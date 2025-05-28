package br.com.nailDesigner.dtos;

import java.util.List;

public class ServicoDTO {
	private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private List<String> imagens;
    
 
	public List<String> getImagens() {
		return imagens;
	}
	public void setImagens(List<String> imagens) {
		this.imagens = imagens;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

}
