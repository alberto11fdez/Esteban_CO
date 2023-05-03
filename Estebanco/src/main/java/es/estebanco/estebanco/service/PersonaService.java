package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {
    @Autowired
    PersonaRepository personaRepository;

    public PersonaEntityDto encontrarPersona(int idpersona){
        PersonaEntity persona = personaRepository.findById(idpersona).orElse(null);
        return (persona !=null ? persona.toDTO() : null);

    }

    public void save(PersonaEntityDto dto) {
        PersonaEntity persona;
        persona = new PersonaEntity();
        persona.setId(dto.getId());
        persona.setDni(dto.getDni());
        persona.setNombre(dto.getNombre());
        persona.setApellido1(dto.getApellido1());
        persona.setApellido2(dto.getApellido2());
        persona.setCorreo(dto.getCorreo());
        persona.setDireccion(dto.getDireccion());
        persona.setTelefono(dto.getTelefono());
        persona.setUsuario(dto.getUsuario());
        persona.setContraseña(dto.getContraseña());
        persona.setEstado(dto.getEstado());
        persona.setConversacionsById(null);
        persona.setConversacionsById_0(null);
        persona.setOperacionesById(null);
        persona.setRolsById(null);

    }

    public PersonaEntityDto buscarSiExisteUsuario(String usuario) {
        PersonaEntity persona= personaRepository.buscarSiExisteUsuario(usuario);
        return persona.toDTO();
    }


    public PersonaEntityDto buscarPersonaPorId(Integer idPersona){
        PersonaEntity persona = this.personaRepository.buscarPersonaPorId(idPersona);
        if(persona!=null){
            return persona.toDTO();
        }else{
            return null;
        }
    }
}
