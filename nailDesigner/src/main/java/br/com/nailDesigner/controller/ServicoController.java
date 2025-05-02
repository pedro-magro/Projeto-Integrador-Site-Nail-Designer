package br.com.nailDesigner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.nailDesigner.models.Servico;
import br.com.nailDesigner.services.ServicoService;

@Controller
@RequestMapping("/servicos")
public class ServicoController {
	
	@Autowired
	ServicoService servicoService;
	
	@GetMapping
	public String listarServicos(Model model) {
		model.addAttribute("sercos", servicoService.listarTodos());
		return "servicos/lista";
	}
	
	@GetMapping("/{id}")
	public String detalhesServico(@PathVariable long id, Model model) {
		Servico servico = servicoService.buscaPorId(id);
		return "servicos/detalhes";		
	}
}
