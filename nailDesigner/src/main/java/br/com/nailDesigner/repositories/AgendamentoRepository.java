package br.com.nailDesigner.repositories;

import java.util.List;
import br.com.nailDesigner.models.Agendamento;
import br.com.nailDesigner.models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;



public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{
	
	List<Agendamento> findByCliente(Usuario cliente);
	List<Agendamento> findByFuncionariosContaining(Usuario funcionario);

}
