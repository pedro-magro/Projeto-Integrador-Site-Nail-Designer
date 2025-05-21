package br.com.nailDesigner.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.nailDesigner.dtos.UsuarioDTO;
import br.com.nailDesigner.models.Usuario;
import br.com.nailDesigner.repositories.UsuarioRepository;
import br.com.nailDesigner.services.AgendamentoService;
import br.com.nailDesigner.services.UsuarioService;

@Controller
@RequestMapping("/funcionario")
@PreAuthorize("hasRole('FUNCIONARIO')")
public class FuncionarioController {
	
	@Autowired
	private AgendamentoService agendamentoService;
	
	@Autowired 
	private UsuarioRepository usuarioRepo;
	
	@Autowired 
	private UsuarioService usuarioService;
	
	@GetMapping("/perfil")
    public String perfil(Model model, Authentication authentication) {
        model.addAttribute("usuario", usuarioService.buscarPorEmail(authentication.getName()));
        return "funcionario/perfil";
    }
	
	@PostMapping("/atualizar")
    public String atualizar(@ModelAttribute UsuarioDTO dto, Authentication authentication) {
        dto.setEmail(authentication.getName());
        usuarioService.atualizar(dto);
        return "redirect:/funcionario/perfil";
    }
	
	@GetMapping("/dashboard")
	public String dashboard(Model model, Authentication authentication) {
		Usuario funcionario = usuarioRepo.findByEmail(authentication.getName()).orElseThrow();
		model.addAttribute("agendamentos", agendamentoService.listarPorFuncionario(funcionario));
		model.addAttribute("clientes", usuarioService.listarClientes());
		return "funcionario/dashboard";
	}
	
	

}
