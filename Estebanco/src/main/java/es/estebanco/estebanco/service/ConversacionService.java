package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dto.ConversacionEntityDto;
import es.estebanco.estebanco.dto.CuentaEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.entity.ConversacionEntity;
import es.estebanco.estebanco.entity.CuentaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConversacionService {
    @Autowired
    PersonaRepository personaRepository;

    public List<ConversacionEntityDto> conversacionPorPersona(PersonaEntityDto persona){
        ArrayList dtos = new ArrayList<CuentaEntityDto>();
        List<ConversacionEntity> lista = personaRepository.conversacionPorPersona(persona.getId());
        lista.forEach((final ConversacionEntity conver)->dtos.add(conver.toDTO()));
        return dtos;
    }
}
