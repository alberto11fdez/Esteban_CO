package es.estebanco.estebanco.dao;


import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Integer> {
   // @Query("select p from PersonaEntity p where p = :persona ")
    //public List<PersonaEntity> personas ();
}
