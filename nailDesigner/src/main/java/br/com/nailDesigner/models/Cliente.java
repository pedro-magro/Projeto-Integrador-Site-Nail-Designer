package br.com.nailDesigner.models;

import java.sql.Date;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Cliente { 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@NotEmpty
	private String nome;
	
	@NotEmpty
	private String celular;
	
	private Date createdAt;

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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	

}
