package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.CuentaRepository;
import es.estebanco.estebanco.dao.OperacionRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dao.TipoMonedaEntityRepository;
import es.estebanco.estebanco.dto.CuentaEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<CuentaEntityDto> listarCuentas(String texto, List<String> divisa){
        List<CuentaEntity> cuentas;
        ArrayList dtos = new ArrayList<CuentaEntityDto>();
        if(texto.isEmpty() && divisa.isEmpty()){
            cuentas = this.cuentaRepository.findAll();
        } else if (texto.isEmpty()){
            cuentas = this.cuentaRepository.cuentaPorDivisa(divisa);
        } else if (divisa.isEmpty()){
            cuentas = this.cuentaRepository.cuentaPorIban(texto);
        } else {
            cuentas = this.cuentaRepository.buscarPorIbanYDivisa(texto,divisa);
        }
        cuentas.forEach((final CuentaEntity cuenta) -> dtos.add(cuenta.toDTO()));
        return dtos;
    }

    protected List<CuentaEntityDto> listaEntidadesADTO (List<CuentaEntity> lista){
        ArrayList dtos = new ArrayList<CuentaEntityDto>();
        lista.forEach((final CuentaEntity cuenta) -> dtos.add(cuenta.toDTO()));

        return dtos;
    }
}
