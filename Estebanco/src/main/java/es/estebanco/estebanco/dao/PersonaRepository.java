package es.estebanco.estebanco.dao;


import es.estebanco.estebanco.entity.ConversacionEntity;
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

    @Query("select  p from PersonaEntity p join RolEntity  r on  p=r.personaByPersonaId and r.cuentaByCuentaId=:empresa")
    public List<PersonaEntity> obtenerSocioEmpresa(@Param("empresa") CuentaEntity empresa) ;

    @Query("select conv from ConversacionEntity conv where (conv.estado =1 and conv.personaByPersonaId = :persona)")
    public List<ConversacionEntity> conversacionPorPersona(@Param("persona") PersonaEntity persona);

 @Query("select c from CuentaEntity c join RolEntity r on c=r.cuentaByCuentaId and :persona=r.personaByPersonaId")
 public List<CuentaEntity> cuentasPorPersona(@Param("persona")PersonaEntity persona);

   @Query("select p from PersonaEntity p where p.usuario= :usuario ")
   public PersonaEntity buscarSiExisteUsuario(@Param("usuario")String usuario);
   @Query("select p from PersonaEntity p join RolEntity r on p=r.personaByPersonaId join CuentaEntity c on c=r.cuentaByCuentaId and c=:cuenta")
    public PersonaEntity propietarioDeCuenta(@Param("cuenta") CuentaEntity cuenta);

   @Query("select p from PersonaEntity p , RolEntity r where p=r.personaByPersonaId and r.cuentaByCuentaId != :cuenta ")
    public List<PersonaEntity> personasNoSociosEnCuentaEmpresa(@Param("cuenta") CuentaEntity cuenta);

}
