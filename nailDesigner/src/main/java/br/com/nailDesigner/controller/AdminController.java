package br.com.nailDesigner.controller;

import java.util.List;

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

import br.com.nailDesigner.dtos.DisponibilidadeDTO;
import br.com.nailDesigner.dtos.ServicoDTO;
import br.com.nailDesigner.dtos.UsuarioDTO;
import br.com.nailDesigner.models.Usuario;
import br.com.nailDesigner.repositories.UsuarioRepository;
import br.com.nailDesigner.services.AgendamentoService;
import br.com.nailDesigner.services.DisponibilidadeService;
import br.com.nailDesigner.services.ServicoService;
import br.com.nailDesigner.services.UsuarioService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
	
	@Autowired 
	private ServicoService servicoService;
	
	@Autowired 
	private UsuarioService usuarioService;
	
	@Autowired
	private AgendamentoService agendamentoService;
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private DisponibilidadeService disponibilidadeService;
	
	@GetMapping("/dashboard")
	public  String dashboard(Model model, Authentication authentication) {
		usuarioRepo.findByEmail(authentication.getName()).orElseThrow();
		model.addAttribute("clientes", usuarioService.listarClientes());
		model.addAttribute("funcionarios", usuarioService.listarFuncionarios());
		model.addAttribute("servico", servicoService.listarTodos());
		model.addAttribute("agendamentos", agendamentoService.listarTodos());
		return "/admin/dashboard";
	}

	@GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<UsuarioDTO> usuarios = usuarioService.listarTodos();
        model.addAttribute("usuarios", usuarios);
        return "admin/usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.buscarPorId(id));
        return "admin/editarUsuario";
    }

    @PostMapping("/usuarios/atualizar")
    public String atualizarUsuario(@ModelAttribute UsuarioDTO dto) {
        usuarioService.atualizar(dto);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/novo")
    public String novoUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        return "admin/novoUsuario";
    }

    @PostMapping("/usuarios/salvar")
    public String salvarUsuario(@ModelAttribute UsuarioDTO dto) {
        usuarioService.salvar(dto);
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/deletar/{id}")
    public String deletarUsuario(@PathVariable Long id) {
        usuarioService.Excluir(id);
        return "redirect:/admin/usuarios";
    }
	
    @GetMapping("/servicos")
    public String listarServicos(Model model) {
    	List<ServicoDTO> servicos =  servicoService.listarTodos();
    	model.addAttribute("servicos ", servicos);
    	return "redirect:/admin/dashboard";
    }
    
    @GetMapping("/servicos/editar/{id}")
    public String editarServico(@PathVariable Long id, Model model) {
        model.addAttribute("servico", servicoService.buscarPorId(id));
        return "admin/editarUsuario";
    }

    @PostMapping("/servicos/atualizar")
    public String atualizarsServico(@ModelAttribute ServicoDTO dto) {
        servicoService.atualizar(dto);
        return "redirect:/admin/usuarios";
    }
    
    @GetMapping("/servicos/novo")
    public String novoServico(Model model) {
        model.addAttribute("Servico", new ServicoDTO());
        return "admin/novoUsuario";
    }

    @PostMapping("/servicos/salvar")
    public String salvarServico(@ModelAttribute ServicoDTO dto) {
        servicoService.salvar(dto);
        return "redirect:/admin/services";
    }

    @GetMapping("/servicos/deletar/{id}")
    public String deletarServico(@PathVariable Long id) {
        servicoService.excluir(id);
        return "redirect:/admin/servicos";
    }
    
    @GetMapping("/disponibilidades")
    public String listarDisponibilidades(Model model) {
        model.addAttribute("disponibilidades", disponibilidadeService.listarTodos());
        return "admin/disponibilidades";
    }

    @GetMapping("/disponibilidades/novo")
    public String novaDisponibilidade(Model model) {
        model.addAttribute("disponibilidade", new DisponibilidadeDTO());
        return "admin/novaDisponibilidade";
    }

    @PostMapping("/disponibilidades/salvar")
    public String salvarDisponibilidade(@ModelAttribute DisponibilidadeDTO dto) {
        disponibilidadeService.salvar(dto);
        return "redirect:/admin/disponibilidades";
    }

    @GetMapping("/disponibilidades/editar/{id}")
    public String editarDisponibilidade(@PathVariable Long id, Model model) {
        model.addAttribute("disponibilidade", disponibilidadeService.buscarPorId(id));
        return "admin/editarDisponibilidade";
    }

    @PostMapping("/disponibilidades/atualizar")
    public String atualizarDisponibilidade(@ModelAttribute DisponibilidadeDTO dto) {
        disponibilidadeService.atualizar(dto);
        return "redirect:/admin/disponibilidades";
    }

    @GetMapping("/disponibilidades/deletar/{id}")
    public String deletarDisponibilidade(@PathVariable Long id) {
        disponibilidadeService.excluir(id);
        return "redirect:/admin/disponibilidades";
    }
    
    @GetMapping("/agendamentos")
    public String listarAgendamentos(Model model) {
        model.addAttribute("agendamentos", agendamentoService.listarTodos());
        return "admin/agendamentos";
    }
}
