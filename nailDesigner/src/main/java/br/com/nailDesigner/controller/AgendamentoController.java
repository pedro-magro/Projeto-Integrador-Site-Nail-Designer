package br.com.nailDesigner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.nailDesigner.models.Agendamento;
import br.com.nailDesigner.models.Usuario;
import br.com.nailDesigner.repositories.AgendamentoRepository;
import br.com.nailDesigner.repositories.UsuarioRepository;
import br.com.nailDesigner.services.EmailService;
import jakarta.validation.constraints.Email;

@Controller("/agendamento")
public class AgendamentoController {
	
	@Autowired
	EmailService emailService;
	@Autowired
	UsuarioRepository usuarioRepo;
	@Autowired
	AgendamentoRepository agendamentoRepo;
	
	@PostMapping
	private String salvar(@ModelAttribute Agendamento agendamento, Authentication auth) {
		Usuario cliente = usuarioRepo.findByEmail(auth.getName()).orElseThrow();
		agendamento.setCliente(cliente);
		agendamentoRepo.save(agendamento);
		
		//Email para o Cliente
		emailService.enviarEmail(cliente.getEmail(),
		"Confirmação de Agendamento", "Olá "+ cliente.getNome() + ", seu agendamento foi confirmado para o dia "
		+ agendamento.getData() + " às " + agendamento.getHora() + ".");
		
		//Email para cada funcionario 
		for(Usuario funcionario: agendamento.getFuncionarios()) {
			emailService.enviarEmail(funcionario.getEmail(), 
			"Novo Agendamento Recebido",
			"Olá " + funcionario.getNome() + ", você foi agendado para o dia " + 
			agendamento.getData() + " às" + agendamento.getHora() + ".");
		}
		
		return "redirect:cliente/agendamentos";
	}

}
