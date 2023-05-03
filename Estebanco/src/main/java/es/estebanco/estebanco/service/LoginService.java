package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dao.RolRepository;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.dto.RolEntityDto;
import es.estebanco.estebanco.entity.PersonaEntity;
import es.estebanco.estebanco.entity.RolEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginService {
    @Autowired
    protected PersonaRepository personaRepository;
    @Autowired
    protected RolRepository rolRepository;

    public PersonaEntityDto autenticar(String user, String contrasena){
        PersonaEntity persona = this.personaRepository.autenticar(user,contrasena);
        return (persona == null ? null : persona.toDTO());
    }
    public List<RolEntityDto> getRolByIdString(int iduser){
        ArrayList dtos = new ArrayList<RolEntityDto>();
        List<RolEntity> lista = rolRepository.getRolByIdString(iduser);
        lista.forEach((final RolEntity rol)->dtos.add(rol.toDTO()));
        return dtos;
    }


}
