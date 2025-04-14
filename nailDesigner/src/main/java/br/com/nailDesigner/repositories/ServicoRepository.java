package br.com.nailDesigner.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.nailDesigner.models.Servico;

public interface ServicoRepository extends JpaRepository<Servico, Long> {

}
