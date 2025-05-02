package br.com.nailDesigner.controller;

import br.com.nailDesigner.services.AgendamentoService;
import br.com.nailDesigner.services.UsuarioService;

import java.util.List;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import br.com.nailDesigner.models.*;
import br.com.nailDesigner.repositories.UsuarioRepository;

@Controller
@RequestMapping("/cliente")
@PreAuthorize("hasRole('CLIENTE')")
public class ClienteController {
	
	@Autowired
    private UsuarioService usuarioService;
	
	@Autowired 
	private AgendamentoService agendamentoService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
    	String email = authentication.getName();
    	Usuario cliente = usuarioService.buscarPorEmail(email);
    	List<Agendamento> agendamentos = agendamentoService.listarPorCliente(cliente);
    	model.addAttribute("agendamentos", agendamentos);
    	return "cliente/dashboard";
    }
    

}
