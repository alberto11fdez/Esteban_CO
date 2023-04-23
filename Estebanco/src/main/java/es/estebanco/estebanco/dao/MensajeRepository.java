package es.estebanco.estebanco.dao;

import es.estebanco.estebanco.entity.MensajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeRepository extends JpaRepository<MensajeEntity, Integer> {
}
