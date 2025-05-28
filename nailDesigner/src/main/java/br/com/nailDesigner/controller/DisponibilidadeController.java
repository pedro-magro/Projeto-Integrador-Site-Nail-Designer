package br.com.nailDesigner.controller;

import br.com.nailDesigner.dtos.DisponibilidadeDTO;
import br.com.nailDesigner.services.DisponibilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequestMapping("/disponibilidades")
@PreAuthorize("hasRole('FUNCIONARIO')")
public class DisponibilidadeController {

    @Autowired
    private DisponibilidadeService disponibilidadeService;

    @GetMapping("/form")
    public String exibirFormulario(Model model) {
        model.addAttribute("disponibilidade", new DisponibilidadeDTO());
        return "disponibilidades/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute DisponibilidadeDTO dto, Authentication authentication) {
        dto.setFuncionarioEmail(authentication.getName());
        disponibilidadeService.salvar(dto);
        return "redirect:/disponibilidades/listar";
    }

    @GetMapping("/listar")
    public String listar(Model model, Authentication authentication) {
        String email = authentication.getName();
        List<DisponibilidadeDTO> disponibilidades = disponibilidadeService.listarPorFuncionario(email);
        model.addAttribute("disponibilidades", disponibilidades);
        return "disponibilidades/listar";
    }
}
