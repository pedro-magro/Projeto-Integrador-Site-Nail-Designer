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

import br.com.nailDesigner.dtos.AgendamentoDTO;
import br.com.nailDesigner.dtos.UsuarioDTO;
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
	
	@GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication) {
        model.addAttribute("usuario", usuarioService.buscarPorEmail(authentication.getName()));
        return "cliente/perfil";
    }
	
	@PostMapping("/atualizar")
    public String atualizar(@ModelAttribute UsuarioDTO dto, Authentication authentication) {
        dto.setEmail(authentication.getName());
        usuarioService.atualizar(dto);
        return "redirect:/cliente/perfil";
    }
    
    @GetMapping("/minhas-reservas")
    public String listarCliente(Model model, Authentication auth) {
        String email = auth.getName(); // e-mail do cliente autenticado
        List<AgendamentoDTO> agendamentos = agendamentoService.listarPorClienteEmail(email); // Corrigido
        model.addAttribute("agendamentos", agendamentos);
        return "agendamento/listar-cliente";
    }

}
