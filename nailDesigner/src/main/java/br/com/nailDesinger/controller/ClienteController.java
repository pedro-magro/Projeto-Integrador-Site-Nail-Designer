package br.com.nailDesinger.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import br.com.nailDesigner.models.*;
import br.com.nailDesigner.repositories.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
    private ClienteRepository clienteRepo;
	
	
    @GetMapping
    public List<Cliente> listar() {
        return clienteRepo.findAll();
    }

}
