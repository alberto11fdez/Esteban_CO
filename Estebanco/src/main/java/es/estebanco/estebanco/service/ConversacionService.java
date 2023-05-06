package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.AsistenteRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dto.ConversacionEntityDto;
import es.estebanco.estebanco.dto.CuentaEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.entity.ConversacionEntity;
import es.estebanco.estebanco.entity.CuentaEntity;
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
public class ConversacionService {
    @Autowired
    PersonaRepository personaRepository;

    @Autowired
    AsistenteRepository asistenteRepository;

    public List<ConversacionEntityDto> conversacionPorPersona(PersonaEntityDto persona){
        ArrayList dtos = new ArrayList<CuentaEntityDto>();
        List<ConversacionEntity> lista = this.personaRepository.conversacionPorPersona(persona.getId());
        lista.forEach((final ConversacionEntity conver)->dtos.add(conver.toDTO()));
        return dtos;
    }

    public List<ConversacionEntityDto> conversacionPorPersona (Integer idPersona){
        List<ConversacionEntity> lista = this.asistenteRepository.conversacionPorPersona(idPersona);
        ArrayList dtos = new ArrayList<ConversacionEntityDto>();

        lista.forEach((final ConversacionEntity conver)->dtos.add(conver.toDTO()));
        return dtos;
    }


    public List<ConversacionEntityDto> buscarPorIdCliente(Integer idCliente, Integer idAsistente){
        List<ConversacionEntity> lista = this.asistenteRepository.buscarPorIdCliente(idCliente, idAsistente);
        ArrayList dtos = new ArrayList<ConversacionEntityDto>();

        lista.forEach((final ConversacionEntity conver)->dtos.add(conver.toDTO()));
        return dtos;
    }


    public List<ConversacionEntityDto> conversacionesActivas(Integer idAsistente){
        List<ConversacionEntity> lista = this.asistenteRepository.conversacionesActivas(idAsistente);
        ArrayList dtos = new ArrayList<ConversacionEntityDto>();

        lista.forEach((final ConversacionEntity conver)->dtos.add(conver.toDTO()));
        return dtos;
    }

    public List<ConversacionEntityDto> conversacionesBloqueadas(Integer idAsistente){
        List<ConversacionEntity> lista = this.asistenteRepository.conversacionesBloqueadas(idAsistente);
        ArrayList dtos = new ArrayList<ConversacionEntityDto>();

        lista.forEach((final ConversacionEntity conver)->dtos.add(conver.toDTO()));
        return dtos;
    }



    public List<ConversacionEntityDto> buscarPorIdClienteCuentaActiva(Integer idCliente, Integer idAsistente){
        List<ConversacionEntity> lista = this.asistenteRepository.buscarPorIdClienteCuentaActiva(idCliente, idAsistente);
        ArrayList dtos = new ArrayList<ConversacionEntityDto>();

        lista.forEach((final ConversacionEntity conver)->dtos.add(conver.toDTO()));
        return dtos;
    }


    public List<ConversacionEntityDto> buscarPorIdClienteCuentaBloqueada(Integer idCliente, Integer idAsistente){
        List<ConversacionEntity> lista = this.asistenteRepository.buscarPorIdClienteCuentaBloqueada(idCliente, idAsistente);
        ArrayList dtos = new ArrayList<ConversacionEntityDto>();

        lista.forEach((final ConversacionEntity conver)->dtos.add(conver.toDTO()));
        return dtos;
    }


    public List<Byte> getEstados(){
        List<Byte> listaDto = this.asistenteRepository.getEstados();
        return listaDto;
    }


    public List<Integer> getClients(){
        List<Integer> listaDto = this.asistenteRepository.getClients();
        return listaDto;
    }


    public List<PersonaEntityDto> getAsistente(){
        List<PersonaEntity> lista = this.asistenteRepository.getAsistente();
        ArrayList dtos = new ArrayList<PersonaEntityDto>();

        lista.forEach((final PersonaEntity conver)->dtos.add(conver.toDTO()));
        return dtos;
    }


    public Integer getUltimaIdConversacion(){
        int idConv = this.asistenteRepository.getUltimaIdConversacion();
        return idConv;
    }


    public ConversacionEntityDto buscarConversacionPorId(Integer idConversacion){
        ConversacionEntity conversacion = this.asistenteRepository.buscarConversacionPorId(idConversacion);
        if(conversacion!=null){
            return conversacion.toDTO();
        }else{
            return null;
        }
    }

    public PersonaEntityDto devuelvePersonaEntityDto(Integer idPersona){
        return this.asistenteRepository.getPersona(idPersona).toDTO();
    }

    public PersonaEntityDto devuelveAsistenteEntityDto(Integer idAsistente){
        return this.asistenteRepository.getPersona(idAsistente).toDTO();
    }


    public void save(ConversacionEntityDto dto) {
        ConversacionEntity conversacion = new ConversacionEntity();
        conversacion.setIdconversacion(dto.getIdconversacion());
        conversacion.setEstado(dto.getEstado());
        conversacion.setFechaInicio(dto.getFechaInicio());
        conversacion.setFechaFin(dto.getFechaFin());

        PersonaEntityDto personaDto = dto.getPersonaByPersonaId();
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

        PersonaEntityDto asistenteDto =dto.getPersonaByAsistenteId();
        PersonaEntity asistente = new PersonaEntity();
        asistente.setId(asistenteDto.getId());
        asistente.setDni(asistenteDto.getDni());
        asistente.setNombre(asistenteDto.getNombre());
        asistente.setApellido1(asistenteDto.getApellido1());
        asistente.setApellido2(asistenteDto.getApellido2());
        asistente.setCorreo(asistenteDto.getCorreo());
        asistente.setDireccion(asistenteDto.getDireccion());
        asistente.setTelefono(asistenteDto.getTelefono());
        asistente.setUsuario(asistenteDto.getUsuario());
        asistente.setContraseña(asistenteDto.getContraseña());
        asistente.setEstado(asistenteDto.getEstado());

        conversacion.setPersonaByAsistenteId(asistente);

        conversacion.setMensajesByIdconversacion(this.asistenteRepository.devolverListaMensajes(conversacion.getIdconversacion()));

        this.asistenteRepository.save(conversacion);
    }



}
