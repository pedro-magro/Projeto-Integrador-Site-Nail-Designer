package br.com.nailDesigner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.nailDesigner.models.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

}
