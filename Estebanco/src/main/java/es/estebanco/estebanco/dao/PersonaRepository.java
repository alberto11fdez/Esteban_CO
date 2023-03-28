package es.estebanco.estebanco.dao;


import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.OperacionEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Integer> {
   // @Query("select p from PersonaEntity p where p = :persona ")
    //public List<PersonaEntity> personas ();
    @Query("select p from PersonaEntity p where p.usuario = :user and p.contraseña = :contrasena")
    public PersonaEntity autenticar(@Param("user") String user, @Param("contrasena")String contrasena);

    @Query("select c from CuentaEntity c join RolEntity r on c=r.cuentaByCuentaId and :persona=r.personaByPersonaId")
    public List<CuentaEntity> cuentasPorPersona(@Param("persona")PersonaEntity persona);
    @Query("select o from OperacionEntity o join CuentaEntity c on o.cuentaByCuentaId=c join RolEntity r on c=r.cuentaByCuentaId and :persona=r.personaByPersonaId")
    public List<OperacionEntity> operacionesPorPersona(@Param("persona")PersonaEntity persona);
}
