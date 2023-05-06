package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.MensajeRepository;
import es.estebanco.estebanco.dto.ConversacionEntityDto;
import es.estebanco.estebanco.dto.MensajeEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.entity.ConversacionEntity;
import es.estebanco.estebanco.entity.MensajeEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
   ALBERTO -> 100%.
 */

@Service
public class MensajeService {

    @Autowired
    MensajeRepository mensajeRepository;


    public List<MensajeEntityDto> getMensajesDeEstaConversacion(Integer idConversacion){
        List<MensajeEntity> lista = this.mensajeRepository.getMensajesDeEstaConversacion(idConversacion);
        ArrayList dtos = new ArrayList<MensajeEntityDto>();

        lista.forEach((final MensajeEntity conver)->dtos.add(conver.toDTO()));
        return dtos;
    }


    public ConversacionEntityDto getConversacion(Integer idConversacion){
        ConversacionEntity conversacion = this.mensajeRepository.getConversacion(idConversacion);
        return conversacion.toDTO();
    }


    public Integer getUltimaIdMensaje(){
        int ultMensaje = this.mensajeRepository.getUltimaIdMensaje();
        return ultMensaje;
    }



    public void save(MensajeEntityDto dto){

        MensajeEntity mensaje = new MensajeEntity();
        mensaje.setIdmensaje(dto.getIdmensaje());
        mensaje.setFechaEnvio(dto.getFechaEnvio());
        mensaje.setTexto(dto.getTexto());
        mensaje.setConversacionEmisorId(dto.getConversacionEmisorId());
        mensaje.setConversacionReceptorId(dto.getConversacionReceptorId());

        ConversacionEntityDto converDto = this.getConversacion(dto.getIdconversacion());
        ConversacionEntity conversacion = new ConversacionEntity();
        conversacion.setIdconversacion(converDto.getIdconversacion());
        conversacion.setEstado(converDto.getEstado());
        conversacion.setFechaInicio(converDto.getFechaInicio());
        conversacion.setFechaFin(converDto.getFechaFin());

        PersonaEntityDto personaDto=converDto.getPersonaByPersonaId();
        PersonaEntity persona = new PersonaEntity();
        persona.setId(personaDto.getId());
        persona.setDni(personaDto.getDni());
        persona.setNombre(personaDto.getNombre());
        persona.setApellido1(personaDto.getApellido1());
        persona.setApellido2(personaDto.getApellido2());
        persona.setCorreo(personaDto.getCorreo());
        persona.setDireccion(personaDto.getDireccion());
        persona.setTelefono(personaDto.getTelefono());
        persona.setUsuario(personaDto.getUsuario());
        persona.setContraseña(personaDto.getContraseña());
        persona.setEstado(personaDto.getEstado());

        conversacion.setPersonaByPersonaId(persona);



        PersonaEntityDto asistenteDto = converDto.getPersonaByAsistenteId();
        PersonaEntity asistente = new PersonaEntity();
        asistente.setId(personaDto.getId());
        asistente.setDni(personaDto.getDni());
        asistente.setNombre(personaDto.getNombre());
        asistente.setApellido1(personaDto.getApellido1());
        asistente.setApellido2(personaDto.getApellido2());
        asistente.setCorreo(personaDto.getCorreo());
        asistente.setDireccion(personaDto.getDireccion());
        asistente.setTelefono(personaDto.getTelefono());
        asistente.setUsuario(personaDto.getUsuario());
        asistente.setContraseña(personaDto.getContraseña());
        asistente.setEstado(personaDto.getEstado());


        conversacion.setPersonaByAsistenteId(asistente);

        conversacion.setMensajesByIdconversacion(this.mensajeRepository.getMensajesDeEstaConversacion(converDto.getIdconversacion()));

        mensaje.setConversacionByConversacionIdconversacion(conversacion);
        this.mensajeRepository.save(mensaje);
    }
}
