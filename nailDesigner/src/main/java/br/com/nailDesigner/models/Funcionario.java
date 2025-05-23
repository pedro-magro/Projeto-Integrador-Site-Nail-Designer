package br.com.nailDesigner.models;

import jakarta.persistence.*;


@Entity
public class Funcionario { 
	
	@Id @GeneratedValue
    private Long id;
	@Column(unique = true, nullable = false)
	private String email;
    private String nome;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCpf() {
		return email;
	}
	public void setCpf(String cpf) {
		this.email = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
    
    
    

}
