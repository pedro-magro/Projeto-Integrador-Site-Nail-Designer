package br.com.nailDesigner.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.nailDesigner.dtos.AgendamentoDTO;
import br.com.nailDesigner.models.Agendamento;
import br.com.nailDesigner.models.Servico;
import br.com.nailDesigner.models.Usuario;
import br.com.nailDesigner.repositories.AgendamentoRepository;
import br.com.nailDesigner.repositories.ServicoRepository;
import br.com.nailDesigner.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AgendamentoService {
	
	@Autowired
	private AgendamentoRepository agendamentoRepo;
	
	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private ServicoRepository servicoRepo;

	@Autowired
	private JavaMailSender mailSender;
	
	
	public AgendamentoDTO toDTO(Agendamento agendamento) {
        AgendamentoDTO dto = new AgendamentoDTO();
        dto.setId(agendamento.getId());
        dto.setData(agendamento.getData());
        dto.setHora(agendamento.getHora());
        dto.setClienteId(agendamento.getCliente().getId());
        dto.setFuncionariosIds(agendamento.getFuncionarios().stream().map(Usuario::getId).collect(Collectors.toList()));
        dto.setServicosIds(agendamento.getServicos().stream().map(Servico::getId).collect(Collectors.toList()));
        return dto;
	}
	
	 public Agendamento fromDTO(AgendamentoDTO dto) {
	        Agendamento agendamento = new Agendamento();
	        agendamento.setId(dto.getId());
	        agendamento.setData(dto.getData());
	        agendamento.setHora(dto.getHora());
	        
	        Usuario cliente = usuarioRepo.findByEmail(dto.getClienteEmail())
	                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
	        agendamento.setCliente(cliente);
	            
	        List<Usuario> funcionarios = usuarioRepo.findAllById(dto.getFuncionariosIds());
	        agendamento.setFuncionarios(funcionarios);
	        
	        List<Servico> servicos = servicoRepo.findAllById(dto.getServicosIds());
	        agendamento.setServicos(servicos);
	        
	        return agendamento;
		}
	
	private boolean existeConflito(Usuario funcionario, LocalDate data, LocalTime hora) {
        return agendamentoRepo.findByDataAndHora(data, hora).stream()
            .anyMatch(a -> a.getFuncionarios().contains(funcionario));
    }
	
	 public AgendamentoDTO salvar(AgendamentoDTO dto) {
	        Agendamento agendamento = new Agendamento();
	        agendamento.setData(dto.getData());
	        agendamento.setHora(dto.getHora());
	        agendamento.setCliente(usuarioRepo.findById(dto.getClienteId()).orElseThrow());
	        agendamento.setFuncionarios(usuarioRepo.findAllById(dto.getFuncionariosIds()));
	        agendamento.setServicos(servicoRepo.findAllById(dto.getServicosIds()));

	        for (Usuario funcionario : agendamento.getFuncionarios()) {
	            if (!funcionario.getServicosQuePodeExecutar().containsAll(agendamento.getServicos())) {
	                throw new IllegalArgumentException("Funcionário não está apto a realizar todos os serviços");
	            }
	            if (existeConflito(funcionario, agendamento.getData(), agendamento.getHora())) {
	                throw new IllegalArgumentException("Funcionário já possui agendamento neste horário");
	            }
	        }

	        Agendamento salvo = agendamentoRepo.save(agendamento);
	        enviarEmails(salvo);
	        return toDTO(salvo);
	    }
	 
	 private void enviarEmails(Agendamento agendamento) {
		    // Enviar e-mail para o cliente com os detalhes do agendamento
		    SimpleMailMessage mensagemCliente = new SimpleMailMessage();
		    mensagemCliente.setTo(agendamento.getCliente().getEmail());
		    mensagemCliente.setSubject("Confirmação de Agendamento");

		    StringBuilder corpoCliente = new StringBuilder();
		    corpoCliente.append("Olá, ").append(agendamento.getCliente().getNome()).append("!\n\n");
		    corpoCliente.append("Seu agendamento foi confirmado para o dia ")
		                 .append(agendamento.getData()).append(" às ")
		                 .append(agendamento.getHora()).append(".\n");
		    corpoCliente.append("Serviços: \n");
		    for (Servico servico : agendamento.getServicos()) {
		        corpoCliente.append("- ").append(servico.getNome()).append("\n");
		    }
		    mensagemCliente.setText(corpoCliente.toString());
		    mailSender.send(mensagemCliente);

		    // Enviar e-mail para cada funcionário com os serviços que ele irá realizar
		    for (Usuario funcionario : agendamento.getFuncionarios()) {
		        SimpleMailMessage mensagemFuncionario = new SimpleMailMessage();
		        mensagemFuncionario.setTo(funcionario.getEmail());
		        mensagemFuncionario.setSubject("Novo Agendamento Atribuído");

		        StringBuilder corpoFuncionario = new StringBuilder();
		        corpoFuncionario.append("Olá, ").append(funcionario.getNome()).append("!\n\n");
		        corpoFuncionario.append("Você tem um novo agendamento no dia ")
		                         .append(agendamento.getData()).append(" às ")
		                         .append(agendamento.getHora()).append(".\n");
		        corpoFuncionario.append("Serviços que você irá executar: \n");

		        for (Servico servico : agendamento.getServicos()) {
		            if (funcionario.getServicosQuePodeExecutar().contains(servico)) {
		                corpoFuncionario.append("- ").append(servico.getNome()).append("\n");
		            }
		        }

		        mensagemFuncionario.setText(corpoFuncionario.toString());
		        mailSender.send(mensagemFuncionario);
		    }
		}

	
	public void excluir(Long id) {
		agendamentoRepo.deleteById(id);
	}
	
	public void cancelar(Long agendamentoId, String emailUsuario) {
	    Agendamento agendamento = agendamentoRepo.findById(agendamentoId)
	        .orElseThrow(() -> new EntityNotFoundException("Agendamento não encontrado"));

	    // Verifica se o usuário autenticado é o cliente do agendamento
	    if (!agendamento.getCliente().getEmail().equals(emailUsuario)) {
	    	throw new AccessDeniedException("Você não tem permissão para acessar este agendamento.");
	    }

	    // Verifica se o cancelamento está sendo feito com pelo menos 2 horas de antecedência
	    LocalDateTime agora = LocalDateTime.now();
	    LocalDateTime DataHoraAgendamento = LocalDateTime.of(agendamento.getData(), agendamento.getHora());
	    if (DataHoraAgendamento.isBefore(agora.plusHours(2))) {
	        throw new IllegalStateException("O cancelamento deve ser feito com no mínimo 2 horas de antecedência.");
	    }

	    agendamentoRepo.delete(agendamento);
	}


	
	public AgendamentoDTO buscarPorId(Long id) {
		return toDTO(agendamentoRepo.findById(id).orElse(null));
	}
	
	public List<AgendamentoDTO> listarPorCliente(Usuario cliente){
		return agendamentoRepo.findByCliente(cliente).stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	public List<AgendamentoDTO> listarPorFuncionario(Usuario funcionario){
		return agendamentoRepo.findByFuncionariosContaining(funcionario).stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	public List<AgendamentoDTO> listarTodos(){
		return agendamentoRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	public List<AgendamentoDTO> listarPorClienteEmail(String email) {
	    Usuario cliente = usuarioRepo.findByEmail(email)
	        .orElseThrow(() -> new UsernameNotFoundException("Cliente não encontrado"));
	    return agendamentoRepo.findByCliente(cliente)
	        .stream()
	        .map(this::toDTO)
	        .collect(Collectors.toList());
	}
	
	public List<AgendamentoDTO> listarPorFuncionarioEmail(String email) {
	    Usuario funcionario = usuarioRepo.findByEmail(email)
	        .orElseThrow(() -> new UsernameNotFoundException("Funcionario não encontrado"));
	    return agendamentoRepo.findByCliente(funcionario)
	        .stream()
	        .map(this::toDTO)
	        .collect(Collectors.toList());
	}
}
