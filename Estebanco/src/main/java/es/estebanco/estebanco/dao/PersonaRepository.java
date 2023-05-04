package es.estebanco.estebanco.dao;


import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaRepository extends JpaRepository<PersonaEntity, Integer> {
   // @Query("select p from PersonaEntity p where p = :persona ")
    //public List<PersonaEntity> personas ();
    @Query("select p from PersonaEntity p where p.usuario = :user and p.contraseña = :contrasena")
    public PersonaEntity autenticar(@Param("user") String user, @Param("contrasena")String contrasena);

    @Query("select  p from PersonaEntity p join RolEntity  r on  p=r.personaByPersonaId and r.cuentaByCuentaId.id=:empresa")
    public List<PersonaEntity> obtenerSocioEmpresa(@Param("empresa") Integer empresa) ;

    @Query("select conv from ConversacionEntity conv where (conv.estado =1 and conv.personaByPersonaId.id = :persona)")
    public List<ConversacionEntity> conversacionPorPersona(@Param("persona") int persona);

 @Query("select c from CuentaEntity c join RolEntity r on c=r.cuentaByCuentaId and :idPersona=r.personaByPersonaId.id")
 public List<CuentaEntity> cuentasPorPersona(@Param("idPersona") Integer idPersona);

   @Query("select p.operacionesById from PersonaEntity p where p.id = :id")
   public List<OperacionEntity> listadoOperaciones(@Param("id") Integer id);

 @Query("select p.conversacionsById from PersonaEntity p where p.id = :id")
 public List<ConversacionEntity> listadoConversaciones(@Param("id") Integer id);
 @Query("select p.conversacionsById_0 from PersonaEntity p where p.id = :id")
 public List<ConversacionEntity> listadoConversaciones2(@Param("id") Integer id);
 @Query("select p.rolsById from PersonaEntity p where p.id = :id")
 public List<RolEntity> listadoRoles(@Param("id") Integer id);

   @Query("select p from PersonaEntity p where p.usuario= :usuario ")
   public PersonaEntity buscarSiExisteUsuario(@Param("usuario")String usuario);
   @Query("select p from PersonaEntity p join RolEntity r on p=r.personaByPersonaId join CuentaEntity c on c=r.cuentaByCuentaId and c.id=:cuenta")
    public PersonaEntity propietarioDeCuenta(@Param("cuenta") int cuenta);

   @Query("select distinct p from PersonaEntity p , RolEntity r where p=r.personaByPersonaId and r.cuentaByCuentaId.id != :cuenta ")
    public List<PersonaEntity> personasNoSociosEnCuentaEmpresa(@Param("cuenta") Integer cuenta);
    @Query("select distinct p from PersonaEntity p where p.estado = :estado")
    public List<PersonaEntity> obtenerPersonasPorEstado(@Param("estado")String estado);


    @Query("select p from PersonaEntity p where p.id= :idPersona ")
    public PersonaEntity buscarPersonaPorId(@Param("idPersona")Integer idPersona);


    @Query("select distinct p from PersonaEntity p ,RolEntity r where p=r.personaByPersonaId and r.cuentaByCuentaId.id=:cuenta order by p.dni ASC ")
    public List<PersonaEntity> ordenarSociosDniAscendente(@Param("cuenta") Integer cuenta);
    @Query("select distinct p from PersonaEntity p ,RolEntity r where p=r.personaByPersonaId and r.cuentaByCuentaId.id=:cuenta order by p.dni DESC ")
    public List<PersonaEntity> ordenarSociosDniDescendente(@Param("cuenta") Integer cuenta);
    @Query("select distinct p from PersonaEntity p ,RolEntity r where p=r.personaByPersonaId and r.cuentaByCuentaId.id=:cuenta and r.bloqueado_empresa=1 ")
    public List<PersonaEntity> ordenarSociosBloqueados(@Param("cuenta") Integer cuenta);
    @Query("select distinct p from PersonaEntity p ,RolEntity r where p=r.personaByPersonaId and r.cuentaByCuentaId.id=:cuenta and r.bloqueado_empresa=0 ")
    public List<PersonaEntity> ordenarSociosActivados(@Param("cuenta") Integer cuenta);
    @Query("select distinct p from PersonaEntity p ,RolEntity r where p=r.personaByPersonaId and r.cuentaByCuentaId.id=:cuenta order by p.apellido1 asc, p.apellido2 asc ")
    public List<PersonaEntity> ordenarSociosApellidosAscendente(@Param("cuenta") Integer cuenta);
    @Query("select distinct p from PersonaEntity p ,RolEntity r where p=r.personaByPersonaId and r.cuentaByCuentaId.id=:cuenta order by p.apellido1 desc , p.apellido2 desc ")
    public List<PersonaEntity> ordenarSociosApellidosDescendente(@Param("cuenta") Integer cuenta);

    @Query("select c.operacionsById from CuentaEntity c where c.id= :id")
    public List<OperacionEntity> buscarOperacionesPorID(@Param("id") Integer id);

}
