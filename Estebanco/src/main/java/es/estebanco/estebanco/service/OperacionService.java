package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.CuentaRepository;
import es.estebanco.estebanco.dao.OperacionRepository;
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
public class OperacionService {

    @Autowired
    protected OperacionRepository operacionRepository;
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private PersonaRepository personaRepository;

    public List<OperacionEntityDto> buscarOperacionesRecibidas(String iban) {
        List<OperacionEntity> operacionEntityList=operacionRepository.buscarOperacionesRecibidas(iban);
        ArrayList dtos = new ArrayList<OperacionEntityDto>();

        operacionEntityList.forEach((final OperacionEntity operacion) -> dtos.add(operacion.toDTO()));

        return  dtos;
    }

    public List<OperacionEntityDto> operacionesPorCuentaYTipo(int id, String tipo) {
        List<OperacionEntity> operacionEntityList=operacionRepository.operacionesPorCuentaYTipo(id,tipo);
        ArrayList dtos = new ArrayList<OperacionEntityDto>();

        operacionEntityList.forEach((final OperacionEntity operacion) -> dtos.add(operacion.toDTO()));

        return  dtos;
    }

    public List<OperacionEntityDto> operacionesPorCuentaYMoneda(int id, String tipo) {
        List<OperacionEntity> operacionEntityList=operacionRepository.operacionesPorCuentaYMoneda(id,tipo);
        ArrayList dtos = new ArrayList<OperacionEntityDto>();

        operacionEntityList.forEach((final OperacionEntity operacion) -> dtos.add(operacion.toDTO()));

        return  dtos;
    }

    public List<OperacionEntityDto> operacionesPorCuentaOrdenadoPorFecha(int id) {
        List<OperacionEntity> operacionEntityList=operacionRepository.operacionesPorCuentaOrdenadoPorFecha(id);
        ArrayList dtos = new ArrayList<OperacionEntityDto>();

        operacionEntityList.forEach((final OperacionEntity operacion) -> dtos.add(operacion.toDTO()));

        return  dtos;
    }

    public List<OperacionEntityDto> operacionesPorCuentaOrdenadoPorCantidad(int id) {
        List<OperacionEntity> operacionEntityList=operacionRepository.operacionesPorCuentaOrdenadoPorCantidad(id);
        ArrayList dtos = new ArrayList<OperacionEntityDto>();

        operacionEntityList.forEach((final OperacionEntity operacion) -> dtos.add(operacion.toDTO()));

        return  dtos;
    }

    public List<OperacionEntityDto> operacionesPorCuenta(int id) {
        List<OperacionEntity> operacionEntityList=operacionRepository.operacionesPorCuenta(id);
        ArrayList dtos = new ArrayList<OperacionEntityDto>();

        operacionEntityList.forEach((final OperacionEntity operacion) -> dtos.add(operacion.toDTO()));

        return  dtos;
    }

    public void save(OperacionEntityDto operacion) {
        OperacionEntity operacionEntity=new OperacionEntity();
        operacionEntity.setFechaOperacion(operacion.getFechaOperacion());
        operacionEntity.setTipo(operacion.getTipo());
        operacionEntity.setIdOperacion(operacion.getIdOperacion());
        CuentaEntity cuenta = cuentaRepository.findById(operacion.getCuentaByCuentaId().getId()).orElse(null);
        operacionEntity.setCuentaByCuentaId(cuenta);
        PersonaEntity persona = personaRepository.findById(operacion.getPersonaByPersonaId().getId()).orElse(null);
        operacionEntity.setPersonaByPersonaId(persona);
        operacionEntity.setCantidad(operacion.getCantidad());
        operacionEntity.setMoneda(operacion.getMoneda());
        operacionEntity.setIbanCuentaDestinoOrigen(operacion.getIbanCuentaDestinoOrigen());

        operacionRepository.save(operacionEntity);
    }
    public void saveSinId(OperacionEntityDto operacion) {
        OperacionEntity operacionEntity=new OperacionEntity();
        operacionEntity.setFechaOperacion(operacion.getFechaOperacion());
        operacionEntity.setTipo(operacion.getTipo());
        CuentaEntity cuenta = cuentaRepository.findById(operacion.getCuentaByCuentaId().getId()).orElse(null);
        operacionEntity.setCuentaByCuentaId(cuenta);
        PersonaEntity persona = personaRepository.findById(operacion.getPersonaByPersonaId().getId()).orElse(null);
        operacionEntity.setPersonaByPersonaId(persona);
        operacionEntity.setCantidad(operacion.getCantidad());
        operacionEntity.setMoneda(operacion.getMoneda());
        operacionEntity.setIbanCuentaDestinoOrigen(operacion.getIbanCuentaDestinoOrigen());

        operacionRepository.save(operacionEntity);
    }

    public List<OperacionEntityDto> getOperacionesSocio(PersonaEntityDto socioFiltro, CuentaEntityDto cuentaEmpresa) {

        List<OperacionEntity> operacionEntityList=operacionRepository.getOperacionesSocio(socioFiltro.getId(),cuentaEmpresa.getId());
        ArrayList dtos = new ArrayList<OperacionEntityDto>();

        operacionEntityList.forEach((final OperacionEntity operacion) -> dtos.add(operacion.toDTO()));

        return  dtos;
    }
}
