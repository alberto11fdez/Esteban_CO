package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
   FERNANDO -> 100%.
 */

@Service
public class LoginCajeroService {
    @Autowired
    protected PersonaRepository personaRepository;

    public PersonaEntityDto doAutenticar(String user, String contrasena){
        PersonaEntity persona = this.personaRepository.autenticar(user,contrasena);
        return (persona == null? null : persona.toDTO());
    }
}
