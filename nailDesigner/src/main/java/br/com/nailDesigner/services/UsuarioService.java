package br.com.nailDesigner.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.nailDesigner.repositories.ServicoRepository;
import br.com.nailDesigner.repositories.UsuarioRepository;
import br.com.nailDesigner.dtos.UsuarioDTO;
import br.com.nailDesigner.models.Servico;
import br.com.nailDesigner.models.Usuario;
import br.com.nailDesigner.models.enums.Role;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {
	
	@Autowired
	private UsuarioRepository usuarioRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
    private ServicoRepository servicoRepo;
	
	
	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new org.springframework.security.core.userdetails.User(
            usuario.getEmail(),
            usuario.getSenha(),
            List.of(new SimpleGrantedAuthority(usuario.getRole().name()))
        );
    }
	
	 public UsuarioDTO toDTO(Usuario usuario) {
	        UsuarioDTO dto = new UsuarioDTO();
	        dto.setId(usuario.getId());
	        dto.setNome(usuario.getNome());
	        dto.setEmail(usuario.getEmail());
	        dto.setTelefone(usuario.getTelefone());
	        dto.setCpf(usuario.getCpf());
	        dto.setRole(usuario.getRole());
	        if (usuario.getServicosQuePodeExecutar() != null) {
	            dto.setServicosIds(usuario.getServicosQuePodeExecutar().stream()
	                .map(Servico::getId)
	                .collect(Collectors.toList()));
	        }
	        return dto;
	    }
	 
	 public Usuario fromDTO(UsuarioDTO dto) {
	        Usuario usuario = new Usuario();
	        usuario.setId(dto.getId());
	        usuario.setNome(dto.getNome());
	        usuario.setEmail(dto.getEmail());
	        usuario.setTelefone(dto.getTelefone());
	        usuario.setCpf(dto.getCpf());
	        usuario.setRole(dto.getRole());
	        if (dto.getServicosIds() != null) {
	            usuario.setServicosQuePodeExecutar(servicoRepo.findAllById(dto.getServicosIds()));
	        }
	        return usuario;
	    } 
	
	 public UsuarioDTO salvar(UsuarioDTO dto) {
		    Optional<Usuario> existente = usuarioRepo.findByEmail(dto.getEmail());
		    if (existente.isPresent()) {
		        throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
		    }

		    Usuario usuario = new Usuario();
		    usuario.setNome(dto.getNome());
		    usuario.setEmail(dto.getEmail());
		    usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
		    usuario.setCpf(dto.getCpf());
		    usuario.setTelefone(dto.getTelefone());
		    usuario.setRole(dto.getRole());

		    return toDTO(usuarioRepo.save(usuario));
		}

	 
	 public UsuarioDTO atualizar(UsuarioDTO dto) {
		    Usuario usuarioExistente = usuarioRepo.findByEmail(dto.getEmail())
		        .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

		    usuarioExistente.setNome(dto.getNome());
		    usuarioExistente.setCpf(dto.getCpf());
		    usuarioExistente.setTelefone(dto.getTelefone());

		    if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
		        usuarioExistente.setSenha(passwordEncoder.encode(dto.getSenha()));
		    }

		    return toDTO(usuarioRepo.save(usuarioExistente));
		}

	
	public UsuarioDTO buscarPorEmail(String email) {
        return toDTO(usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + email)));
	}
	public boolean emailJaCadastrado(String email) {
	    return usuarioRepo.existsByEmail(email);
	}

	
	public void Excluir(Long id) {
		usuarioRepo.deleteById(id);
	}
	
	public UsuarioDTO buscarPorId(Long id) {
		return toDTO(usuarioRepo.findById(id).orElse(null));
	}
	
	public List<UsuarioDTO> listarFuncionarios(){
		return usuarioRepo.findByRole(Role.ROLE_FUNCIONARIO).stream().map(this::toDTO).collect(Collectors.toList());
	}
	public List<UsuarioDTO> listarClientes(){
		return usuarioRepo.findByRole(Role.ROLE_CLIENTE).stream().map(this::toDTO).collect(Collectors.toList());
	}
	
	public List<UsuarioDTO> listarTodos(){
		return usuarioRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
	}
	

}
