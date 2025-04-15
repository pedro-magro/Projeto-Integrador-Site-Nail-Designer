package br.com.nailDesinger.controller;

import br.com.nailDesigner.services.ClienteServices;

import java.util.List;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import br.com.nailDesigner.models.*;
import br.com.nailDesigner.repositories.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
    private ClienteServices clienteService;
	
	
    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listarTodos();
    }
    
    @PostMapping
    public Cliente criar(@RequestBody Cliente cliente) {
    	return clienteService.salvar(cliente);
    }
    

}
