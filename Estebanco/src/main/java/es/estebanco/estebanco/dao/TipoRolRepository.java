package es.estebanco.estebanco.dao;

import es.estebanco.estebanco.entity.RolEntity;
import es.estebanco.estebanco.entity.TipoRolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TipoRolRepository extends JpaRepository<TipoRolEntity, Integer> {

    @Query("select nombre from TipoRolEntity ")
    public List<String> obtenerRoles();
}
