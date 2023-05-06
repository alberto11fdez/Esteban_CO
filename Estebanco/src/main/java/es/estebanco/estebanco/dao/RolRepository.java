package es.estebanco.estebanco.dao;

/*
   JOSÉ MANUEL SÁNCHEZ NAVARRO -> 60%
   ALBERTO FERNÁNDEZ RAMOS -> 20%
   SERGIO BERINO GARCÍA -> 20%.
 */

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
    @Query("select rol from RolEntity rol where rol.personaByPersonaId = :idPerson")
    List <String> getRolByPersonaId(@Param("idPerson") Integer idPerson);

    @Query("select c from RolEntity c where c.personaByPersonaId.id =:idPerson")
    public List<RolEntity> getRolByIdString(@Param("idPerson")Integer idPerson);

    @Query("select  r.cuentaByCuentaId.id from RolEntity r where r.personaByPersonaId.id=:idpersona and (r.rol='empresa' or r.rol='normal') ")
    public List<Integer> obtenerCuentasNormlesOEmpresa(@Param("idpersona")Integer idpersona);

    @Query("select  r.cuentaByCuentaId.id from RolEntity  r where r.personaByPersonaId.id=:idpersona and r.rol='socio' and r.bloqueado_empresa=0")
    public List<Integer> obtenerCuentasSocioActivado(@Param("idpersona")Integer idpersona);

    @Query("select r.personaByPersonaId.id from RolEntity r where r.cuentaByCuentaId.id=:idCuentaEmpresa and r.rol='socio' and r.bloqueado_empresa=0")
    public List<Integer> obtenerSociosActivos(@Param("idCuentaEmpresa") Integer idCuentaEmpresa);

    @Query("select r.personaByPersonaId.id from RolEntity r where r.cuentaByCuentaId.id=:idCuentaEmpresa and r.rol='socio' and r.bloqueado_empresa=1")
    public List<Integer> obtenerSocioBloqueados(@Param("idCuentaEmpresa")Integer idCuentaEmpresa);
}
