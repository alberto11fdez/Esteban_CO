package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.CuentaRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dao.RolRepository;
import es.estebanco.estebanco.dto.CuentaEntityDto;
import es.estebanco.estebanco.dto.RolEntityDto;
import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.RolEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/*
   JOSE -> 100%.
 */

@Service
public class RolService {
    @Autowired
    RolRepository rolRepository;
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    CuentaRepository cuentaRepository;

    public void saveRol(RolEntityDto dto) {
        RolEntity rol = new RolEntity();
        rol.setRol(dto.getRol());
        rol.setPersonaByPersonaId(personaRepository.findById(dto.getPersonaByPersonaId().getId()).orElse(null));
        rol.setCuentaByCuentaId(cuentaRepository.findById(dto.getCuentaByCuentaId().getId()).orElse(null));
        rol.setId(dto.getId());
        rol.setBloqueado_empresa(dto.getBloqueado_empresa());
        this.rolRepository.save(rol);
    }
    public void saveRolsinId(RolEntityDto dto) {
        RolEntity rol = new RolEntity();
        rol.setRol(dto.getRol());
        rol.setPersonaByPersonaId(personaRepository.findById(dto.getPersonaByPersonaId().getId()).orElse(null));
        rol.setCuentaByCuentaId(cuentaRepository.findById(dto.getCuentaByCuentaId().getId()).orElse(null));
        rol.setBloqueado_empresa(dto.getBloqueado_empresa());
        this.rolRepository.save(rol);
    }

    public RolEntityDto obtenerRol_Persona_en_Empresa(Integer idSocio, int id) {
        RolEntity rol = rolRepository.obtenerRol_Persona_en_Empresa(idSocio,id);
        return rol.toDTO();
    }

    public List<Integer> obtenerCuentasNormlesOEmpresa(Integer idpersona) {
        List<Integer> lista = rolRepository.obtenerCuentasNormlesOEmpresa(idpersona);
        return lista;
    }

    public List<Integer> obtenerCuentasSocioActivado(Integer idpersona) {
        List<Integer> lista = rolRepository.obtenerCuentasSocioActivado(idpersona);
        return  lista;
    }

    public List<Integer> obtenerSociosActivos(Integer idCuentaEmpresa) {
        return rolRepository.obtenerSociosActivos(idCuentaEmpresa);
    }

    public List<Integer> obtenerSocioBloqueados(Integer idCuentaEmpresa) {
        return rolRepository.obtenerSocioBloqueados(idCuentaEmpresa);

    }
}
