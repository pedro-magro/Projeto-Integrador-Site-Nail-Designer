package br.com.nailDesigner.repositories;

import br.com.nailDesigner.models.Disponibilidade;
import br.com.nailDesigner.models.Usuario;
import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DisponibilidadeReposiory extends JpaRepository<Disponibilidade, Long> {
	List<Disponibilidade> findByFuncionarioAndDiaSemana(Usuario funcionario, DayOfWeek diaSemana);

}
