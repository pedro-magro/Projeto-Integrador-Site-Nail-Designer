package br.com.nailDesigner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nailDesigner.models.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{

}
