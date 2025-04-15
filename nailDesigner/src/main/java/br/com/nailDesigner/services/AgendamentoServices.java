package br.com.nailDesigner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.nailDesigner.repositories.AgendamentoRepository;
import br.com.nailDesigner.models.*;

import java.util.List;

@Service
public class AgendamentoServices {
	
	@Autowired
	private AgendamentoRepository AgendamentoRepo;
	
	public Agendamento salvar(Agendamento agendamento) {
		return AgendamentoRepo.save(agendamento);
	}
	
	public void excluir(Long id) {
		AgendamentoRepo.deleteById(id);
	}
	
	public Agendamento buscarPorId(Long id) {
		return AgendamentoRepo.findById(id).orElse(null);
	}
	
	public List<Agendamento> listarTodos(){
		return AgendamentoRepo.findAll();
	}
}
