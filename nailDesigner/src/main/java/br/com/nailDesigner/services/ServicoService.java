package br.com.nailDesigner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.nailDesigner.repositories.ServicoRepository;
import br.com.nailDesigner.models.*;

import java.util.List;

@Service
public class ServicoService {
	
	@Autowired
	private ServicoRepository servicoRepo;
	
	public Servico salvar(Servico servico) {
		return servicoRepo.save(servico);
	}
	
	public void excluir(Long id) {
		servicoRepo.deleteById(id);
	}
	
	public Servico buscaPorId(Long id) {
		return servicoRepo.findById(id).orElse(null);
	}
	
	public List<Servico> listarTodos(){
		return servicoRepo.findAll();
	}
	
}
