package es.estebanco.estebanco.dao;

import es.estebanco.estebanco.entity.ConversacionEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AsistenteRepository extends JpaRepository<ConversacionEntity,Integer> {

    @Query("select p from PersonaEntity p where p.usuario = :user and p.contraseña = :contrasena")
    public PersonaEntity autenticar(@Param("user") String user, @Param("contrasena")String contrasena);
    @Query("select conv from ConversacionEntity conv where conv.personaByAsistenteId = :persona")
    public List<ConversacionEntity> conversacionPorPersona(@Param("persona") PersonaEntity persona);


}
