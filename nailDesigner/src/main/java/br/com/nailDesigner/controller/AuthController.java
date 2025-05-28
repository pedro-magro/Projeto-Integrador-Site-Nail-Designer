package br.com.nailDesigner.controller;

import br.com.nailDesigner.dtos.UsuarioDTO;
import br.com.nailDesigner.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // Página de login
    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            // Usuário já está autenticado, redireciona com base na role
            String role = authentication.getAuthorities().iterator().next().getAuthority();
            switch (role) {
                case "ROLE_ADMIN": return "redirect:/admin/usuarios";
                case "ROLE_FUNCIONARIO": return "redirect:/funcionario/perfil";
                case "ROLE_CLIENTE": return "redirect:/cliente/perfil";
                default: return "redirect:/";
            }
        }
        return "auth/login";
    }

    // Página de cadastro
    @GetMapping("/cadastro")
    public String cadastroForm(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/"; // já logado
        }
        model.addAttribute("usuario", new UsuarioDTO());
        return "auth/cadastro";
    }

    // Processa cadastro
    @PostMapping("/cadastro")
    public String cadastrar(@ModelAttribute("usuario") UsuarioDTO usuarioDTO, Model model) {
        if (usuarioService.emailJaCadastrado(usuarioDTO.getEmail())) {
            model.addAttribute("erro", "E-mail já está em uso.");
            return "auth/cadastro";
        }

        usuarioService.salvar(usuarioDTO);
        return "redirect:/login?cadastroSucesso";
    }

    // Página de acesso negado
    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "auth/acessoNegado";
    }
}
