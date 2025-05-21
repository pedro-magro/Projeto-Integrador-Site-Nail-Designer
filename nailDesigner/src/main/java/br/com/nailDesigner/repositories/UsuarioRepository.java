package br.com.nailDesigner.repositories;

import java.util.Optional;
import java.util.List;
import br.com.nailDesigner.models.Usuario;
import br.com.nailDesigner.models.enums.Role;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmail(String email);
	List<Usuario> findByRole(Role role);
	boolean existsByEmail(String email);

}
