package br.com.nailDesigner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.nailDesigner.dtos.AgendamentoDTO;
import br.com.nailDesigner.services.AgendamentoService;
import br.com.nailDesigner.services.ServicoService;
import br.com.nailDesigner.services.UsuarioService;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ServicoService servicoService;

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("agendamento", new AgendamentoDTO());
        model.addAttribute("servicos", servicoService.listarTodos());
        model.addAttribute("funcionarios", usuarioService.listarFuncionarios());
        return "agendamento/form";
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute AgendamentoDTO dto, Authentication auth) {
        dto.setClienteEmail(auth.getName());
        agendamentoService.salvar(dto);
        return "redirect:/agendamentos/minhas-reservas";
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/minhas-reservas")
    public String listarCliente(Model model, Authentication auth) {
        model.addAttribute("agendamentos", agendamentoService.listarPorClienteEmail(auth.getName()));
        return "agendamento/listar-cliente";
    }

    @PreAuthorize("hasRole('FUNCIONARIO')")
    @GetMapping("/meus-servicos")
    public String listarFuncionario(Model model, Authentication auth) {
        model.addAttribute("agendamentos", agendamentoService.listarPorFuncionarioEmail(auth.getName()));
        return "agendamento/listar-funcionario";
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Long id, Authentication auth) {
        agendamentoService.cancelar(id, auth.getName());
        return "redirect:/agendamentos/minhas-reservas";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String listarTodos(Model model) {
        model.addAttribute("agendamentos", agendamentoService.listarTodos());
        return "agendamento/listar-admin";
    }
}
