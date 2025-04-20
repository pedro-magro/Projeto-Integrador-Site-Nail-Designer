package br.com.nailDesigner.controller;

import br.com.nailDesigner.services.UsuarioService;

import java.util.List;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import br.com.nailDesigner.models.*;
import br.com.nailDesigner.repositories.UsuarioRepository;

@Controller
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public Usuario criar(@RequestBody Usuario cliente) {
    	return usuarioService.salvar(cliente);
    }
    

}
