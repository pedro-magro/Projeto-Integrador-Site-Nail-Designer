package br.com.nailDesigner.models;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import br.com.nailDesigner.models.enums.Role;
import jakarta.persistence.*;

@Entity
public class Usuario { 
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	
	private String nome;
	private String celular;
	private String senha;
	private Date createdAt;
	private String cpf; // apenas para funcionarios
	
	@Enumerated(EnumType.STRING)
	private Role role; // CLIENTE, FUNCIONARIO OU ADMIN
	
	@ManyToMany
	private List<Servico> servicosQuePodeExecutar;
	
	@ElementCollection
	private List<LocalDate> datasIndisponiveis;
	
	@OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
	private List<Disponibilidade> disponibilidades;
	
	
	//Getter & Setters
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Servico> getServicosQuePodeExecutar() {
		return servicosQuePodeExecutar;
	}

	public void setServicosQuePodeExecutar(List<Servico> servicosQuePodeExecutar) {
		this.servicosQuePodeExecutar = servicosQuePodeExecutar;
	}

	public List<LocalDate> getDatasIndisponiveis() {
		return datasIndisponiveis;
	}

	public void setDatasIndisponiveis(List<LocalDate> datasIndisponiveis) {
		this.datasIndisponiveis = datasIndisponiveis;
	}

	public List<Disponibilidade> getDisponibilidades() {
		return disponibilidades;
	}

	public void setDisponibilidades(List<Disponibilidade> disponibilidades) {
		this.disponibilidades = disponibilidades;
	} 	

}
