
package br.com.nailDesigner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/") // Mapeia a URL raiz (http://localhost:8080/)
    public String landingPage() {
        return "landing-page"; // Retorna o nome do seu arquivo HTML (sem .html), que o Thymeleaf buscará em src/main/resources/templates/
    }

    @GetMapping("/servicos") // Mapeia a URL /servicos (http://localhost:8080/servicos)
    public String servicosPage() {
        return "servicos"; // Retorna servicos.html
    }

    @GetMapping("/sobre") // Mapeia a URL /sobre (http://localhost:8080/sobre)
    public String sobrePage() {
        return "sobre"; // Retorna sobre.html
    }

    @GetMapping("/perfil") // Mapeia a URL /perfil (http://localhost:8080/perfil)
    public String perfilPage() {
        return "perfil"; // Retorna perfil.html
    }

    @GetMapping("/agendamento") // Mapeia a URL /agendamento (http://localhost:8080/agendamento)
    public String agendamentoPage() {
        return "agendamento"; // Retorna agendamento.html
    }

    // Adicione outros @GetMapping para suas outras páginas conforme necessário
}