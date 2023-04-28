package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.RolRepository;
import es.estebanco.estebanco.dto.RolEntityDto;
import es.estebanco.estebanco.entity.RolEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolService {
    @Autowired
    protected RolRepository rolRepository;

    public List<RolEntityDto> getRolByIdString(int iduser){
        ArrayList dtos = new ArrayList<RolEntityDto>();
        List<RolEntity> lista = rolRepository.getRolByIdString(iduser);
        lista.forEach((final RolEntity rol)->dtos.add(rol.toDTO()));
        return dtos;
    }
    public RolEntityDto obtenerRol_Persona_en_Empresa(int idPersona,int idCuenta){
        RolEntity rol =  rolRepository.obtenerRol_Persona_en_Empresa(idPersona,idCuenta);
        return rol.toDTO();
    }
}
