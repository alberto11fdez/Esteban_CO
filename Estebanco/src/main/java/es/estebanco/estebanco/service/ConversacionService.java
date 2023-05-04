package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.AsistenteRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dto.ConversacionEntityDto;
import es.estebanco.estebanco.dto.CuentaEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.entity.ConversacionEntity;
import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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


    public void save(ConversacionEntityDto dto) {
        ConversacionEntity conversacion;
        conversacion = new ConversacionEntity();
        conversacion.setIdconversacion(dto.getIdconversacion());
        conversacion.setEstado(dto.getEstado());
        conversacion.setFechaInicio(dto.getFechaInicio());
        conversacion.setFechaFin(dto.getFechaFin());

        PersonaEntityDto personaDto=dto.getPersonaByPersonaId();
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
        /*persona.setConversacionsById(personaDto.getConversacionsById());
        persona.setOperacionesById(personaDto.getOperacionesById());
        persona.setConversacionsById_0(personaDto.getConversacionsById_0());
        persona.setRolsById(personaDto.getRolsById());

         */

        conversacion.setPersonaByPersonaId(persona);

        PersonaEntityDto asistenteDto =dto.getPersonaByAsistenteId();
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
        /*asistente.setConversacionsById(personaDto.getConversacionsById());
        asistente.setOperacionesById(personaDto.getOperacionesById());
        asistente.setConversacionsById_0(personaDto.getConversacionsById_0());
        asistente.setRolsById(personaDto.getRolsById());

         */



        conversacion.setMensajesByIdconversacion(dto.getMensajesByIdconversacion());

    }



}
