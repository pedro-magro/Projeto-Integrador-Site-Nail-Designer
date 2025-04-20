package br.com.nailDesigner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.nailDesigner.repositories.AgendamentoRepository;
import br.com.nailDesigner.models.*;

import java.util.List;

@Service
public class AgendamentoService {
	
	@Autowired
	private AgendamentoRepository agendamentoRepo;
	
	public Agendamento salvar(Agendamento agendamento) {
		return agendamentoRepo.save(agendamento);
	}
	
	public void excluir(Long id) {
		agendamentoRepo.deleteById(id);
	}
	
	public Agendamento buscarPorId(Long id) {
		return agendamentoRepo.findById(id).orElse(null);
	}
	
	public List<Agendamento> listarPorCliente(Usuario cliente){
		return agendamentoRepo.findByCliente(cliente);
	}
	
	public List<Agendamento> listarPorFuncionario(Usuario funcionario){
		return agendamentoRepo.findByFuncionariosContaining(funcionario);
	}
	
	public List<Agendamento> listarTodos(){
		return agendamentoRepo.findAll();
	}
}
