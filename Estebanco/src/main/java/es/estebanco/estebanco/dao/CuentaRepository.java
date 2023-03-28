package es.estebanco.estebanco.dao;

import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CuentaRepository extends JpaRepository<CuentaEntity, Integer> {

    @Query("select c from CuentaEntity c join RolEntity r on c=r.cuentaByCuentaId and :persona=r.personaByPersonaId")
    public List<CuentaEntity> cuentasPorPersona(@Param("persona")PersonaEntity persona);

}
