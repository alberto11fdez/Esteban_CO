package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.CuentaRepository;
import es.estebanco.estebanco.dto.CuentaEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.dto.RolEntityDto;
import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import es.estebanco.estebanco.entity.RolEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CuentaService {
    @Autowired
    protected CuentaRepository cuentaRepository;

    public List<CuentaEntityDto> cuentasPorPersona(PersonaEntityDto persona){
        ArrayList dtos = new ArrayList<CuentaEntityDto>();
        List<CuentaEntity> lista = cuentaRepository.cuentasPorPersona(persona.getId());
        lista.forEach((final CuentaEntity cuenta)->dtos.add(cuenta.toDTO()));
        return dtos;
    }

    public CuentaEntityDto encontrarPorId(int idCuenta){
        CuentaEntity cuenta = cuentaRepository.findById(idCuenta).orElse(null);
        return (cuenta!=null ? cuenta.toDTO() : null);
    }

    public void save(CuentaEntityDto dto) {
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
}
