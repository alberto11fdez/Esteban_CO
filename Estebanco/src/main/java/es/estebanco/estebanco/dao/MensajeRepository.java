package es.estebanco.estebanco.dao;

import es.estebanco.estebanco.entity.ConversacionEntity;
import es.estebanco.estebanco.entity.MensajeEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
/*
    ALBERTO FERNÁNDEZ RAMOS -> 100%.
 */

public interface MensajeRepository extends JpaRepository<MensajeEntity, Integer> {

    @Query("select MAX (c.idmensaje) from MensajeEntity c")
    public Integer getUltimaIdMensaje();


    @Query("select c from MensajeEntity c where c.conversacionByConversacionIdconversacion.idconversacion =:idConver")
    public List<MensajeEntity> getMensajesDeEstaConversacion(@Param("idConver")Integer idConver);

    @Query("select c.personaByPersonaId.id from RolEntity c where c.rol='asistente'")
    public List<Integer> getAsistentes();

    @Query("select c.personaByPersonaId.id from RolEntity c where c.rol='normal'")
    public List<Integer> getClientes();



    @Query("select c from ConversacionEntity c where c.idconversacion=:idConver")
    public ConversacionEntity getConversacion(@Param("idConver")Integer idConver);

}
