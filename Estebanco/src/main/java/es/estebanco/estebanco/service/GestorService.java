package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.CuentaRepository;
import es.estebanco.estebanco.dao.OperacionRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dao.TipoMonedaEntityRepository;
import es.estebanco.estebanco.dto.CuentaEntityDto;
import es.estebanco.estebanco.dto.OperacionEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.OperacionEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
   SERGIO -> 100%.
 */

@Service
public class GestorService {

    @Autowired
    protected OperacionRepository operacionRepository;

    @Autowired
    protected CuentaRepository cuentaRepository;

    @Autowired
    protected PersonaRepository personaRepository;

    @Autowired
    protected TipoMonedaEntityRepository tipoMonedaRepository;

    public List<CuentaEntityDto> listarCuentas(String texto, List<String> divisa, String fecha){
        List<CuentaEntity> cuentas;
        ArrayList dtos = new ArrayList<CuentaEntityDto>();
        Date fechas = Date.valueOf(LocalDate.now().minusDays(30));

        if(texto.isEmpty() && divisa.isEmpty() && fecha == null){ // 000
            cuentas = this.cuentaRepository.findAll();
        } else if (texto.isEmpty() && divisa.isEmpty()){ // 001
            cuentas = this.cuentaRepository.cuentasInactivas(fechas);
        } else if (texto.isEmpty() && fecha == null){ // 010
            cuentas = this.cuentaRepository.cuentaPorDivisa(divisa);
        } else if (texto.isEmpty()){ // 011
            cuentas = this.cuentaRepository.cuentaPorDivisaInactiva(divisa,fechas);
        } else if (divisa.isEmpty() && fecha == null) { // 100
            cuentas = this.cuentaRepository.cuentaPorIban(texto);
        } else if (divisa.isEmpty()) { // 101
            cuentas = this.cuentaRepository.cuentaPorIbanInactiva(texto,fechas);
        } else if (fecha == null) { // 110
            cuentas = this.cuentaRepository.buscarPorIbanYDivisa(texto, divisa);
        } else { // 111
            cuentas = this.cuentaRepository.buscarPorIbanYDivisaInactiva(texto,divisa,fechas);
        }
        cuentas.forEach((final CuentaEntity cuenta) -> dtos.add(cuenta.toDTO()));
        return dtos;
    }

    protected List<CuentaEntityDto> listaEntidadesADTO (List<CuentaEntity> lista){
        ArrayList dtos = new ArrayList<CuentaEntityDto>();
        lista.forEach((final CuentaEntity cuenta) -> dtos.add(cuenta.toDTO()));

        return dtos;
    }

    public List<OperacionEntityDto> sortOperaciones(String texto, Integer idCuenta){
        List<OperacionEntity> operaciones;
        LocalDate fecha = LocalDate.now();
        LocalDate fechaLimite = fecha.minusDays(30);
        ArrayList dtos = new ArrayList<OperacionEntityDto>();
         if(texto.equalsIgnoreCase("activo")){
            operaciones = this.operacionRepository.sortOperacionesFecha(idCuenta,fecha,fechaLimite);
        } else if (texto.equalsIgnoreCase("inactivo")){
            operaciones = this.operacionRepository.sortOperacionesFechaAntiguas(idCuenta,fechaLimite);
        } else {
             operaciones = this.operacionRepository.operacionesPorCuenta(idCuenta);
         }
        operaciones.forEach((final OperacionEntity operacion) -> dtos.add(operacion.toDTO()));
        return dtos;
    }
    public List<CuentaEntityDto> cuentasSospechosas(){
        List<CuentaEntity> cuentas;
        ArrayList dtos = new ArrayList<CuentaEntityDto>();
        cuentas = this.cuentaRepository.findTransferenciaSospechosa();
        cuentas.forEach((final CuentaEntity cuenta) -> dtos.add(cuenta.toDTO()));
        return dtos;
    }

    public List<CuentaEntityDto> listarExternas() {
        List<CuentaEntity> cuentas;
        ArrayList dtos = new ArrayList<CuentaEntityDto>();
        cuentas = this.cuentaRepository.findCuentasExternas();
        cuentas.forEach((final CuentaEntity cuenta) -> dtos.add(cuenta.toDTO()));
        return dtos;
    }
}
