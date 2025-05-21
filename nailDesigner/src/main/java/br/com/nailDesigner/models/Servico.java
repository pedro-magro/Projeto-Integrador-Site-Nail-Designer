package br.com.nailDesigner.models;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Servico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotEmpty
	private String nome;
	
	@NotEmpty
	private String descricao;
	
	@NotEmpty
	private Double preco;
	
	@ElementCollection
	private List<String> imagens; //Url das imagens 
	
	
	//Getters & Setters

	
	public long getId() {
		return id;
	}

	public void setId(long codigo) {
		this.id = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<String> getImagens() {
		return imagens;
	}

	public void setImagens(List<String> imagens) {
		this.imagens = imagens;
	}
		

}
