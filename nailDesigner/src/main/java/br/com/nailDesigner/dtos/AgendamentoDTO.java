package br.com.nailDesigner.dtos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import br.com.nailDesigner.models.Servico;
import br.com.nailDesigner.models.Usuario;

public class AgendamentoDTO {
	private Long id;
    private LocalDate data;
    private LocalTime hora;
    private Usuario cliente;
    private Long clienteId; // ID do cliente
    private String clienteEmail;
    private List<Usuario> funcionarios;
    private List<Long> funcionariosIds; // IDs dos funcionários
    private List<Servico> servicos;
    private List<Long> servicosIds; // IDs dos serviços
    
    
    
    public String getClienteEmail() {
        return clienteEmail;
    }
    public void setClienteEmail(String clienteEmail) {
        this.clienteEmail = clienteEmail;
    }
	public Usuario getCliente() {
		return cliente;
	}
	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}
	public List<Usuario> getFuncionarios() {
		return funcionarios;
	}
	public void setFuncionarios(List<Usuario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	public List<Servico> getServicos() {
		return servicos;
	}
	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public LocalTime getHora() {
		return hora;
	}
	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	public Long getClienteId() {
		return clienteId;
	}
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	public List<Long> getFuncionariosIds() {
		return funcionariosIds;
	}
	public void setFuncionariosIds(List<Long> funcionariosIds) {
		this.funcionariosIds = funcionariosIds;
	}
	public List<Long> getServicosIds() {
		return servicosIds;
	}
	public void setServicosIds(List<Long> servicosIds) {
		this.servicosIds = servicosIds;
	}
    
    
 

}
