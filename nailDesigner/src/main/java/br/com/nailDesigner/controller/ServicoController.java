package br.com.nailDesigner.controller;

import br.com.nailDesigner.dtos.ServicoDTO;
import br.com.nailDesigner.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService servicoService;

    @GetMapping
    public String listar(Model model) {
        List<ServicoDTO> servicos = servicoService.listarTodos();
        model.addAttribute("servicos", servicos);
        return "servicos";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("servico", new ServicoDTO());
        return "servico/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute ServicoDTO dto) {
        servicoService.salvar(dto);
        return "redirect:/servicos";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("servico", servicoService.buscarPorId(id));
        return "servico/form";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/atualizar")
    public String atualizar(@ModelAttribute ServicoDTO dto) {
        servicoService.atualizar(dto);
        return "redirect:/servicos";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id) {
        servicoService.excluir(id);
        return "redirect:/servicos";
    }
}
