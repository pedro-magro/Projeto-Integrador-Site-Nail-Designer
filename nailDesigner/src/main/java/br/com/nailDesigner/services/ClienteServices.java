package br.com.nailDesigner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.nailDesigner.repositories.ClienteRepository;
import br.com.nailDesigner.models.Cliente;

import java.util.List;

@Service
public class ClienteServices {
	
	@Autowired
	private ClienteRepository clienteRepo;
	
	public Cliente salvar(Cliente cliente) {
		return clienteRepo.save(cliente);
	}
	
	public void Excluir(Long id) {
		clienteRepo.deleteById(id);
	}
	
	public Cliente buscaPorId(Long id) {
		return clienteRepo.findById(id).orElse(null);
	}
	
	public List<Cliente> listarTodos(){
		return clienteRepo.findAll();
	}
	

}
