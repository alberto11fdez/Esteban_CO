package es.estebanco.estebanco.dao;

import es.estebanco.estebanco.dto.ConversacionEntityDto;
import es.estebanco.estebanco.entity.ConversacionEntity;
import es.estebanco.estebanco.entity.MensajeEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import es.estebanco.estebanco.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/*
   ALBERTO FERNÁNDEZ RAMOS -> 100%.
 */

public interface AsistenteRepository extends JpaRepository<ConversacionEntity,Integer> {

    @Query("select p from PersonaEntity p where p.usuario = :user and p.contraseña = :contrasena")
    public PersonaEntity autenticar(@Param("user") String user, @Param("contrasena")String contrasena);
    @Query("select conv from ConversacionEntity conv where conv.personaByAsistenteId.id = :persona")
    public List<ConversacionEntity> conversacionPorPersona(@Param("persona") Integer persona);

    @Query("select c from ConversacionEntity c where c.personaByPersonaId.id =:cliente and c.personaByAsistenteId.id = :persona")
    public List<ConversacionEntity> buscarPorIdCliente (@Param("cliente") int cliente,@Param("persona") Integer persona);


    @Query("select c from ConversacionEntity c where (c.personaByPersonaId.id =:cliente and c.estado=1 and c.personaByAsistenteId.id = :persona)")
    public List<ConversacionEntity> buscarPorIdClienteCuentaActiva (@Param("cliente") int cliente,@Param("persona") Integer persona);

    @Query("select c from ConversacionEntity c where (c.personaByPersonaId.id =:cliente and c.estado=0 and c.personaByAsistenteId.id = :persona)")
    public List<ConversacionEntity> buscarPorIdClienteCuentaBloqueada (@Param("cliente") int cliente,@Param("persona") Integer persona);

    @Query("select c from ConversacionEntity c where c.estado = 1 and c.personaByAsistenteId.id = :persona")
    public List<ConversacionEntity> conversacionesActivas(@Param("persona") Integer persona);

    @Query("select c from ConversacionEntity c where c.estado = 0 and c.personaByAsistenteId.id = :persona")
    public List<ConversacionEntity> conversacionesBloqueadas(@Param("persona") Integer persona);


    @Query("select distinct c.estado from ConversacionEntity c")
    public List<Byte> getEstados();


    @Query("select distinct c.personaByPersonaId.id from ConversacionEntity c")
    public List<Integer> getClients();


    @Query("select c.personaByPersonaId from RolEntity c where c.rol='asistente'")
    public List<PersonaEntity> getAsistente();


    @Query("select c from PersonaEntity c where c.id = :idPersona")
    public PersonaEntity getPersona(@Param("idPersona") Integer idPersona);

    @Query("select c from PersonaEntity c, RolEntity r where c.id = :idAsistente and r.rol = 'asistente'")
    public PersonaEntity getAsistente(@Param("idAsistente") Integer idAsistente);



    @Query("select MAX (c.idconversacion) from ConversacionEntity c")
    public Integer getUltimaIdConversacion();

    @Query("select p from ConversacionEntity p where p.idconversacion= :idConver ")
    public ConversacionEntity buscarConversacionPorId(@Param("idConver")Integer idConver);


    @Query("select c.mensajesByIdconversacion from ConversacionEntity c where c.idconversacion =:idConver")
    public List<MensajeEntity> devolverListaMensajes(@Param("idConver") Integer idConver);

}
