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

    @Query("select  r from RolEntity r  where r.personaByPersonaId.id=:idPersona and r.cuentaByCuentaId.id=:idCuenta"  )
    public RolEntity obtenerRol_Persona_en_Empresa(@Param("idPersona") Integer idPersona,@Param("idCuenta") Integer idCuenta);


    @Query("select c.rol from RolEntity c where c.personaByPersonaId.id =:idPerson")
    public String getRolByIdString(@Param("idPerson")Integer idPerson);

    @Query("select c.personaByPersonaId.id from RolEntity c where c.rol='asistente'")
    public List<Integer> getAsistentes();

}
