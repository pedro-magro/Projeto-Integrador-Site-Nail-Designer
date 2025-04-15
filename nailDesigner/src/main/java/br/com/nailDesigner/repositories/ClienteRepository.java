package br.com.nailDesigner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.nailDesigner.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	

}
