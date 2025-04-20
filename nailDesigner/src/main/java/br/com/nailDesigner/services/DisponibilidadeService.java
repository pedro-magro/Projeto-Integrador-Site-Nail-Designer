package br.com.nailDesigner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.nailDesigner.repositories.DisponibilidadeReposiory;
import br.com.nailDesigner.models.*;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class DisponibilidadeService {
	
	@Autowired
	private DisponibilidadeReposiory disponibilidadeRepo;
	
	public Disponibilidade salvar(Disponibilidade disponibilidade) {
		return disponibilidadeRepo.save(disponibilidade);
	}
	
	public List<Disponibilidade> listarPorFuncionarioEDia(Usuario funcionario, DayOfWeek diaSemana){
		return disponibilidadeRepo.findByFuncionarioAndDiaSemana(funcionario, diaSemana);
	}
	
	public void excluir(Long id) {
		disponibilidadeRepo.deleteById(id);
	}
	
	public Disponibilidade buscarPorId(Long id) {
		return disponibilidadeRepo.findById(id).orElse(null);
	}
	
	public List<Disponibilidade> listarTodos(){
		return disponibilidadeRepo.findAll();
	}

}
