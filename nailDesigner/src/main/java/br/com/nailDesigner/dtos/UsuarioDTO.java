package br.com.nailDesigner.dtos;

import java.util.List;

import br.com.nailDesigner.models.enums.Role;

public class UsuarioDTO {
	 private Long id;
	    private String nome;
	    private String email;
	    private String telefone;
	    private String cpf;
	    private String senha;
	    private Role role;
	    private List<Long> servicosIds;
	    
	    
	    public String getSenha() {
	    	return senha;
	    }
	    public void setSenha(String senha) {
	    	this.senha = senha;
	    }
		public List<Long> getServicosIds() {
			return servicosIds;
		}
		public void setServicosIds(List<Long> servicosIds) {
			this.servicosIds = servicosIds;
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
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getTelefone() {
			return telefone;
		}
		public void setTelefone(String telefone) {
			this.telefone = telefone;
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
	    
	    

}
