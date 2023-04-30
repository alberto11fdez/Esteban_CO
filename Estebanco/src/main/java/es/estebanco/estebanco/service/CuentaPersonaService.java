package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.CuentaRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dao.RolRepository;
import es.estebanco.estebanco.dao.TipoRolRepository;
import es.estebanco.estebanco.dto.ConversacionEntityDto;
import es.estebanco.estebanco.dto.CuentaEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.dto.RolEntityDto;
import es.estebanco.estebanco.entity.ConversacionEntity;
import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import es.estebanco.estebanco.entity.RolEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CuentaPersonaService {

    @Autowired
    protected CuentaRepository cuentaRepository;
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    protected TipoRolRepository tipoRolRepository;
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
    public CuentaEntityDto encontrarCuentaPorId(int idCuenta){
        CuentaEntity cuenta = cuentaRepository.findById(idCuenta).orElse(null);
        return (cuenta!=null ? cuenta.toDTO() : null);
    }

    public void saveCuenta(CuentaEntityDto dto) {
        CuentaEntity cuenta = new CuentaEntity();
        cuenta.setId(dto.getId());
        cuenta.setIban(dto.getIban());
        cuenta.setSaldo(dto.getSaldo());
        cuenta.setMoneda(dto.getMoneda());
        cuenta.setEstado(dto.getEstado());
        cuenta.setFechaApertura(dto.getFechaApertura());
        cuenta.setOperacionsById(dto.getOperacionsById());
        cuenta.setRolsById(dto.getRolsById());
    }

    public List<String> obtenerRoles(){
        return tipoRolRepository.obtenerRoles();
    }

    public List<CuentaEntityDto> cuentasPorPersona(PersonaEntityDto persona){
        ArrayList dtos = new ArrayList<CuentaEntityDto>();
        List<CuentaEntity> lista = cuentaRepository.cuentasPorPersona(persona.getId());
        lista.forEach((final CuentaEntity cuenta)->dtos.add(cuenta.toDTO()));
        return dtos;
    }
    public PersonaEntityDto encontrarPersona(int idpersona){
        PersonaEntity persona = personaRepository.findById(idpersona).orElse(null);
        return (persona !=null ? persona.toDTO() : null);

    }
    public PersonaEntityDto buscarSiExisteUsuario(String usuario) {
        PersonaEntity persona= personaRepository.buscarSiExisteUsuario(usuario);
        return persona.toDTO();
    }
    public void savePersona(PersonaEntityDto dto) {
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


    public List<ConversacionEntityDto> conversacionPorPersona(PersonaEntityDto persona){
        ArrayList dtos = new ArrayList<CuentaEntityDto>();
        List<ConversacionEntity> lista = personaRepository.conversacionPorPersona(persona.getId());
        lista.forEach((final ConversacionEntity conver)->dtos.add(conver.toDTO()));
        return dtos;
    }
}
