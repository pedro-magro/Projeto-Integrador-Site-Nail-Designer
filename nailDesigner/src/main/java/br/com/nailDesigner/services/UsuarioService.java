package br.com.nailDesigner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.nailDesigner.repositories.UsuarioRepository;
import br.com.nailDesigner.models.Usuario;
import br.com.nailDesigner.models.enums.Role;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public Usuario buscarPorEmail(String email) {
        return usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email));
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepo.findByEmail(email)
				.orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado por email "+ email));
		
		 return new org.springframework.security.core.userdetails.User(
		            usuario.getEmail(),
		            usuario.getSenha(),
		            List.of(new SimpleGrantedAuthority(usuario.getRole().name()))
		        );
	}
	
	public Usuario salvar(Usuario usuario) {
		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return usuarioRepo.save(usuario);
	}
	
	public void Excluir(Long id) {
		usuarioRepo.deleteById(id);
	}
	
	public Usuario buscaPorId(Long id) {
		return usuarioRepo.findById(id).orElse(null);
	}
	
	public List<Usuario> listarFuncionarios(){
		return usuarioRepo.findByRole(Role.ROLE_FUNCIONARIO);
	}
	public List<Usuario> listarClientes(){
		return usuarioRepo.findByRole(Role.ROLE_CLIENTE);
	}
	

}
