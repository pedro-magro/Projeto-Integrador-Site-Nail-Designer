package br.com.nailDesigner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.nailDesigner.repositories.FuncionarioRepository;
import br.com.nailDesigner.models.*;

import java.util.List;

@Service
public class FuncionarioServices {
	
	@Autowired
	private FuncionarioRepository funcionarioRepo;
	
	public Funcionario salvar(Funcionario funcionario) {
		return funcionarioRepo.save(funcionario);
	}
	
	public void excluir(Long id) {
		funcionarioRepo.deleteById(id);
	}
	
	public Funcionario buscarPorId(Long id) {
		return funcionarioRepo.findById(id).orElse(null);
	}
	
	public List<Funcionario> listarTodos(){
		return funcionarioRepo.findAll();
	}

}
