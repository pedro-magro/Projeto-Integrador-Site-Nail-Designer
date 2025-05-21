package br.com.nailDesigner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.nailDesigner.repositories.DisponibilidadeReposiory;
import br.com.nailDesigner.repositories.UsuarioRepository;
import br.com.nailDesigner.dtos.DisponibilidadeDTO;
import br.com.nailDesigner.models.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisponibilidadeService {
	
	@Autowired
	private DisponibilidadeReposiory disponibilidadeRepo;
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	private DisponibilidadeDTO toDTO(Disponibilidade disponibilidade) {
	       DisponibilidadeDTO dto = new DisponibilidadeDTO();
	       dto.setId(disponibilidade.getId());
	       dto.setDiaSemana(disponibilidade.getDiaSemana());
	       dto.setHoraInicio(disponibilidade.getHoraInicio());
	       dto.setHoraFim(disponibilidade.getHoraFim());
	       dto.setFuncionarioId(disponibilidade.getFuncionario().getId());
	       return dto;
	}
	
	public DisponibilidadeDTO salvar(DisponibilidadeDTO dto) {
	       Disponibilidade disponibilidade = new Disponibilidade();
	       disponibilidade.setDiaSemana(dto.getDiaSemana());
	       disponibilidade.setHoraInicio(dto.getHoraInicio());
	       disponibilidade.setHoraFim(dto.getHoraFim());
	       Usuario funcionario = usuarioRepo.findById(dto.getFuncionarioId()).orElseThrow();
	       disponibilidade.setFuncionario(funcionario);

	       return toDTO(disponibilidadeRepo.save(disponibilidade));
	}
	
	public List<DisponibilidadeDTO> listarPorFuncionario(String email) {
        Usuario funcionario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Funcionário não encontrado"));

        return disponibilidadeRepo.findByFuncionario(funcionario).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
	
	public List<DisponibilidadeDTO> listarPorFuncionarioEDia(Usuario funcionario, DayOfWeek diaSemana){
		return disponibilidadeRepo.findByFuncionarioAndDiaSemana(funcionario, diaSemana).stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	public void excluir(Long id) {
		disponibilidadeRepo.deleteById(id);
	}
	
	public DisponibilidadeDTO buscarPorId(Long id) {
		return toDTO(disponibilidadeRepo.findById(id).orElse(null));
	}
	
	public List<DisponibilidadeDTO> listarTodos(){
		return disponibilidadeRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}

}
