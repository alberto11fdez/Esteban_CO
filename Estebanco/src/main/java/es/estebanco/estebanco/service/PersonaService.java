package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dto.CuentaEntityDto;
import es.estebanco.estebanco.dto.OperacionEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.OperacionEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonaService {
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    CuentaPersonaService cuentaPersonaService;

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
        /*persona.setConversacionsById(null);
        persona.setConversacionsById_0(null);
        persona.setOperacionesById(null);
        persona.setRolsById(null);

         */

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

    public List<PersonaEntityDto> obtenerSocioEmpresa(CuentaEntityDto cuentaEmpresa,Integer idPersona) {

        List<PersonaEntity> personaEntities = personaRepository.obtenerSocioEmpresa(cuentaEmpresa.getId(),idPersona);
        ArrayList dtos = new ArrayList<PersonaEntityDto>();

        personaEntities.forEach((final PersonaEntity persona) -> dtos.add(persona.toDTO()));

        return  dtos;
    }

    public List<PersonaEntityDto> personasNoSociosEnCuentaEmpresa(CuentaEntityDto cuentaEmpresa,Integer idPersona) {
        List<PersonaEntity> personaEntities = personaRepository.personasNoSociosEnCuentaEmpresa(cuentaEmpresa.getId(),idPersona);
        ArrayList dtos = new ArrayList<PersonaEntityDto>();

        personaEntities.forEach((final PersonaEntity persona) -> dtos.add(persona.toDTO()));

        return  dtos;
    }

    public List<PersonaEntityDto> ordenarSociosDniAscendente(CuentaEntityDto cuentaEmpresa) {
        List<PersonaEntity> personaEntities = personaRepository.ordenarSociosDniAscendente(cuentaEmpresa.getId());
        ArrayList dtos = new ArrayList<PersonaEntityDto>();

        personaEntities.forEach((final PersonaEntity persona) -> dtos.add(persona.toDTO()));

        return  dtos;
    }

    public List<PersonaEntityDto> ordenarSociosDniDescendente(CuentaEntityDto cuentaEmpresa) {
        List<PersonaEntity> personaEntities = personaRepository.ordenarSociosDniDescendente(cuentaEmpresa.getId());
        ArrayList dtos = new ArrayList<PersonaEntityDto>();

        personaEntities.forEach((final PersonaEntity persona) -> dtos.add(persona.toDTO()));

        return  dtos;
    }

    public List<PersonaEntityDto> ordenarSociosBloqueados(CuentaEntityDto cuentaEmpresa) {
        List<PersonaEntity> personaEntities = personaRepository.ordenarSociosBloqueados(cuentaEmpresa.getId());
        ArrayList dtos = new ArrayList<PersonaEntityDto>();

        personaEntities.forEach((final PersonaEntity persona) -> dtos.add(persona.toDTO()));

        return  dtos;
    }

    public List<PersonaEntityDto> ordenarSociosActivados(CuentaEntityDto cuentaEmpresa) {
        List<PersonaEntity> personaEntities = personaRepository.ordenarSociosActivados(cuentaEmpresa.getId());
        ArrayList dtos = new ArrayList<PersonaEntityDto>();

        personaEntities.forEach((final PersonaEntity persona) -> dtos.add(persona.toDTO()));

        return  dtos;
    }

    public List<PersonaEntityDto> ordenarSociosApellidosAscendente(CuentaEntityDto cuentaEmpresa) {
        List<PersonaEntity> personaEntities = personaRepository.ordenarSociosApellidosAscendente(cuentaEmpresa.getId());
        ArrayList dtos = new ArrayList<PersonaEntityDto>();

        personaEntities.forEach((final PersonaEntity persona) -> dtos.add(persona.toDTO()));

        return  dtos;
    }

    public List<PersonaEntityDto> ordenarSociosApellidosDescendente(CuentaEntityDto cuentaEmpresa) {
        List<PersonaEntity> personaEntities = personaRepository.ordenarSociosApellidosDescendente(cuentaEmpresa.getId());
        ArrayList dtos = new ArrayList<PersonaEntityDto>();

        personaEntities.forEach((final PersonaEntity persona) -> dtos.add(persona.toDTO()));

        return  dtos;
    }

    public List<OperacionEntityDto> operacionesDeUnaEmpresa(Integer id){
        List<OperacionEntity> operaciones = this.personaRepository.buscarOperacionesPorID(id);
        ArrayList dtos = new ArrayList<OperacionEntityDto>();
        operaciones.forEach((final OperacionEntity operacion) -> dtos.add(operacion.toDTO()));

        return dtos;
    }

    public List<PersonaEntityDto> findAll(){
        List<PersonaEntity> personaEntities = personaRepository.findAll();
        ArrayList dtos = new ArrayList<PersonaEntityDto>();

        personaEntities.forEach((final PersonaEntity persona) -> dtos.add(persona.toDTO()));

        return  dtos;
    }
    public List<PersonaEntityDto> obtenerPersonasPorEstado(String estado){
        List<PersonaEntity> personaEntities = personaRepository.obtenerPersonasPorEstado(estado);
        ArrayList dtos = new ArrayList<PersonaEntityDto>();

        personaEntities.forEach((final PersonaEntity persona) -> dtos.add(persona.toDTO()));

        return  dtos;
    }
}
