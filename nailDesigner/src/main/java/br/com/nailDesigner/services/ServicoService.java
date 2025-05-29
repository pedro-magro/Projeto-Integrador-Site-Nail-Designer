package br.com.nailDesigner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.nailDesigner.repositories.ServicoRepository;
import jakarta.persistence.EntityNotFoundException;
import br.com.nailDesigner.dtos.ServicoDTO;
import br.com.nailDesigner.models.*;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicoService {
	
	@Autowired
	private ServicoRepository servicoRepo;
	
	 public ServicoDTO toDTO(Servico servico) {
	        ServicoDTO dto = new ServicoDTO();
	        dto.setId(servico.getId());
	        dto.setNome(servico.getNome());
	        dto.setDescricao(servico.getDescricao());
	        dto.setPreco(servico.getPreco());
			dto.setDuracao(servico.getDuracao());
	        dto.setImagens(servico.getImagens());
	        return dto;
	}
	
	public ServicoDTO salvar(ServicoDTO dto) {
		Servico servico = new Servico();
        servico.setNome(dto.getNome());
        servico.setDescricao(dto.getDescricao());
        servico.setPreco(dto.getPreco());
		servico.setDuracao(dto.getDuracao());
        servico.setImagens(dto.getImagens());
        return toDTO(servicoRepo.save(servico));
	}
	
	public void excluir(Long id) {
	    Servico servico = servicoRepo.findById(id)
	        .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado com ID: " + id));
	    
	    servicoRepo.delete(servico);
	}
	
	public ServicoDTO buscarPorId(Long id) {
		return toDTO(servicoRepo.findById(id).orElse(null));
	}
	
	public ServicoDTO atualizar(ServicoDTO dto) {
	    Servico servico = servicoRepo.findById(dto.getId())
	        .orElseThrow(() -> new EntityNotFoundException("Serviço não encontrado com ID: " + dto.getId()));

	    servico.setNome(dto.getNome());
	    servico.setDescricao(dto.getDescricao());
	    servico.setPreco(dto.getPreco());
		servico.setDuracao(dto.getDuracao());
	    if (dto.getImagens() != null && !dto.getImagens().isEmpty()) {
	        servico.setImagens(dto.getImagens());
	    }

	    return toDTO(servicoRepo.save(servico));
	}
	
	public List<ServicoDTO> listarTodos(){
		return servicoRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	
}
