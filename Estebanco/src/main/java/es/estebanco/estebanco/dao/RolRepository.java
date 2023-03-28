package es.estebanco.estebanco.dao;


import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import es.estebanco.estebanco.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface RolRepository extends JpaRepository<RolEntity, Integer>{

    @Query("select id from RolEntity where id =: idPerson")
    public int getRolById(@Param("idPerson")Integer idPerson);
}
