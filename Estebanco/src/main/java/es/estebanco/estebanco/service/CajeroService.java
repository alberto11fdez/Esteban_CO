package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.*;
import es.estebanco.estebanco.dto.*;
import es.estebanco.estebanco.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
   FERNANDO -> 100%.
 */

@Service
public class CajeroService {

    @Autowired
    protected PersonaRepository personaRepository;

    @Autowired
    protected CuentaRepository cuentaRepository;

    @Autowired
    protected OperacionRepository operacionRepository;

    @Autowired
    protected TipoOperacionEntityRepository tipoOperacionEntityRepository;

    @Autowired
    protected TipoEstadoEntityRepository tipoEstadoEntityRepository;

    @Autowired
    protected TipoMonedaEntityRepository tipoMonedaEntityRepository;


    public PersonaEntityDto personaPorId(Integer idpersona){
        PersonaEntity persona = this.personaRepository.findById(idpersona).orElse(null);
        if(persona != null){
            return persona.toDTO();
        } else {
            return null;
        }
    }

    public List<CuentaEntityDto> listarCuentas(Integer idPersona){
        List<CuentaEntity> lista = this.personaRepository.cuentasPorPersona(idPersona);
        return this.listaEntidadesADTO(lista);
    }

    public List<TipoOperacionEntityDto> listarTiposOperaciones(){
        List<TipoOperacionEntity> tipos = this.tipoOperacionEntityRepository.findAll();
        return this.listaTipoOperacionesADTO(tipos);
    }

    public List<OperacionEntityDto> listarOperaciones(PersonaEntityDto persona){
        List<OperacionEntity> lista = this.operacionRepository.buscarOperacionesPorPersona(persona.getId());
        return this.listaOperacionesADTO(lista);
    }

    public List<OperacionEntityDto> listarOperacionesPorTipo(PersonaEntityDto persona, List<String> tipo){
        List<OperacionEntity> lista = this.operacionRepository.buscarPorTipoOperacion(persona.getId(), tipo);
        return this.listaOperacionesADTO(lista);
    }

    public List<OperacionEntityDto> listarOperacionesPorTexto(PersonaEntityDto persona, String texto){
        List<OperacionEntity> lista = this.operacionRepository.buscarPorIban(persona.getId(), texto);
        return this.listaOperacionesADTO(lista);
    }

    public List<OperacionEntityDto> listarOperacionesPorTextoYTipo(PersonaEntityDto persona, List<String> tipo, String texto){
        List<OperacionEntity> lista = this.operacionRepository.buscarPorTipoOperacionEIban(persona.getId(), texto, tipo);
        return this.listaOperacionesADTO(lista);
    }

    public CuentaEntityDto cuentaPorId(Integer idCuenta){
        CuentaEntity cuenta = this.cuentaRepository.findById(idCuenta).orElse(null);
        if(cuenta != null){
            return cuenta.toDTO();
        } else {
            return null;
        }
    }

    public CuentaEntityDto cuentaOrigenPorId(Integer idCuenta){
        CuentaEntity cuenta = this.cuentaRepository.cuentaOrigen(idCuenta);
        if(cuenta != null){
            return cuenta.toDTO();
        } else {
            return null;
        }
    }

    public void guardarSacar (CuentaEntityDto dto, Integer valor){
        CuentaEntity cuenta = new CuentaEntity();

        cuenta.setId(dto.getId());
        cuenta.setMoneda(dto.getMoneda());
        cuenta.setSaldo(dto.getSaldo()-valor);
        //cuenta.setOperacionsById(null);
        cuenta.setEstado(dto.getEstado());
        //cuenta.setRolsById(null);
        cuenta.setFechaApertura(dto.getFechaApertura());
        cuenta.setIban(dto.getIban());

        this.cuentaRepository.save(cuenta);
    }

    public void nuevaOperacionSacar(CuentaEntityDto dto, Integer valor, PersonaEntityDto persona){
        Date now = new Date();
        OperacionEntity operacion = new OperacionEntity();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(1);
        operacion.setCantidad(valor);
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        operacion.setMoneda(dto.getMoneda());

        CuentaEntity cuenta = this.cuentaRepository.findById(dto.getId()).orElse(null);
        operacion.setCuentaByCuentaId(cuenta);

        PersonaEntity p = this.personaRepository.findById(persona.getId()).orElse(null);
        operacion.setPersonaByPersonaId(p);

        this.operacionRepository.save(operacion);
    }

    public void guardarMeter (CuentaEntityDto dto, Integer valor){
        CuentaEntity cuenta = new CuentaEntity();

        cuenta.setId(dto.getId());
        cuenta.setMoneda(dto.getMoneda());
        cuenta.setSaldo(dto.getSaldo()+valor);
        //cuenta.setOperacionsById(null);
        cuenta.setEstado(dto.getEstado());
        //cuenta.setRolsById(null);
        cuenta.setFechaApertura(dto.getFechaApertura());
        cuenta.setIban(dto.getIban());

        this.cuentaRepository.save(cuenta);
    }

    public void nuevaOperacionMeter(CuentaEntityDto dto, Integer valor, PersonaEntityDto persona){
        Date now = new Date();
        OperacionEntity operacion = new OperacionEntity();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(2);
        operacion.setCantidad(valor);
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        operacion.setMoneda(dto.getMoneda());

        CuentaEntity cuenta = this.cuentaRepository.findById(dto.getId()).orElse(null);
        operacion.setCuentaByCuentaId(cuenta);

        PersonaEntity p = this.personaRepository.findById(persona.getId()).orElse(null);
        operacion.setPersonaByPersonaId(p);

        this.operacionRepository.save(operacion);
    }

    public void nuevaOperacionTransferenciaMeter(CuentaEntityDto dtoDestino, CuentaEntityDto dtoOrigen, Integer valor, PersonaEntityDto persona){
        Date now = new Date();
        OperacionEntity operacion = new OperacionEntity();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(2);
        operacion.setCantidad(valor);
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        operacion.setMoneda(dtoDestino.getMoneda());
        operacion.setIbanCuentaDestinoOrigen(dtoOrigen.getIban());

        CuentaEntity cuenta = this.cuentaRepository.findById(dtoDestino.getId()).orElse(null);
        operacion.setCuentaByCuentaId(cuenta);

        PersonaEntity p = this.personaRepository.findById(persona.getId()).orElse(null);
        operacion.setPersonaByPersonaId(p);

        this.operacionRepository.save(operacion);
    }

    public void nuevaOperacionTransferenciaSacar(CuentaEntityDto dtoDestino, CuentaEntityDto dtoOrigen, Integer valor, PersonaEntityDto persona){
        Date now = new Date();
        OperacionEntity operacion = new OperacionEntity();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(1);
        operacion.setCantidad(valor);
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        operacion.setMoneda(dtoDestino.getMoneda());
        operacion.setIbanCuentaDestinoOrigen(dtoDestino.getIban());

        CuentaEntity cuenta = this.cuentaRepository.findById(dtoOrigen.getId()).orElse(null);
        operacion.setCuentaByCuentaId(cuenta);

        PersonaEntity p = this.personaRepository.findById(persona.getId()).orElse(null);
        operacion.setPersonaByPersonaId(p);

        this.operacionRepository.save(operacion);
    }

    public CuentaEntityDto cuentaOrigen(Integer idCuenta){
        CuentaEntity cuentaOrigen = this.cuentaRepository.cuentaOrigen(idCuenta);
        if(cuentaOrigen != null){
            return cuentaOrigen.toDTO();
        } else {
            return null;
        }
    }

    public CuentaEntityDto cuentaDestino(String destino){
        CuentaEntity cuentaDestino = this.cuentaRepository.cuentaDestinoTransferencia(destino);
        if(cuentaDestino != null){
            return cuentaDestino.toDTO();
        } else {
            return null;
        }
    }


    protected List<CuentaEntityDto> listaEntidadesADTO (List<CuentaEntity> lista) {
        ArrayList dtos = new ArrayList<CuentaEntityDto>();

        lista.forEach((final CuentaEntity cuenta) -> dtos.add(cuenta.toDTO()));

        return dtos;
    }

    protected List<OperacionEntityDto> listaOperacionesADTO (List<OperacionEntity> lista) {
        ArrayList dtos = new ArrayList<OperacionEntityDto>();

        lista.forEach((final OperacionEntity operacion) -> dtos.add(operacion.toDTO()));

        return dtos;
    }

    protected List<TipoOperacionEntityDto> listaTipoOperacionesADTO (List<TipoOperacionEntity> lista) {
        ArrayList dtos = new ArrayList<OperacionEntityDto>();

        lista.forEach((final TipoOperacionEntity tipoOperacion) -> dtos.add(tipoOperacion.toDTO()));

        return dtos;
    }

    protected List<TipoMonedaEntityDto> listaTipoMonedasADTO (List<TipoMonedaEntity> lista) {
        ArrayList dtos = new ArrayList<OperacionEntityDto>();

        lista.forEach((final TipoMonedaEntity tipoMoneda) -> dtos.add(tipoMoneda.toDTO()));

        return dtos;
    }

    public void guardarPersona(PersonaEntityDto dto){

        PersonaEntity persona = new PersonaEntity();
        persona.setId(dto.getId());
        persona.setDni(dto.getDni());
        persona.setNombre(dto.getNombre());
        persona.setApellido1(dto.getApellido1());
        persona.setApellido2(dto.getApellido2());
        persona.setEstado(dto.getEstado());
        persona.setUsuario(dto.getUsuario());
        persona.setCorreo(dto.getCorreo());
        persona.setDireccion(dto.getDireccion());
        persona.setTelefono(dto.getTelefono());
        persona.setContraseña(dto.getContraseña());

        /*persona.setConversacionsById(this.personaRepository.listadoConversaciones(dto.getId()));
        persona.setRolsById(this.personaRepository.listadoRoles(dto.getId()));
        persona.setOperacionesById(this.personaRepository.listadoOperaciones(dto.getId()));
        persona.setConversacionsById_0(this.personaRepository.listadoConversaciones2(dto.getId()));

         */

        this.personaRepository.save(persona);
    }

    public List<TipoMonedaEntityDto> listarTiposMonedas(){
        List<TipoMonedaEntity> monedas = this.tipoMonedaEntityRepository.findAll();
        return this.listaTipoMonedasADTO(monedas);
    }

    public void nuevaOperacionCambiarDivisa(CuentaEntityDto dto, PersonaEntityDto persona){
        Date now = new Date();
        OperacionEntity operacion = new OperacionEntity();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(3);
        operacion.setCantidad(dto.getSaldo());
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        operacion.setMoneda(dto.getMoneda());
        operacion.setIbanCuentaDestinoOrigen(dto.getIban());

        CuentaEntity cuenta = this.cuentaRepository.findById(dto.getId()).orElse(null);
        operacion.setCuentaByCuentaId(cuenta);

        PersonaEntity p = this.personaRepository.findById(persona.getId()).orElse(null);
        operacion.setPersonaByPersonaId(p);

        this.operacionRepository.save(operacion);
    }

    public TipoMonedaEntityDto tipoDeMoneda(String moneda){
        TipoMonedaEntity moneda1 = this.tipoMonedaEntityRepository.buscarMoneda(moneda);
        if(moneda1 != null){
            return moneda1.toDTO();
        } else {
            return null;
        }
    }

    public void guardarCuentaCambioDivisa(CuentaEntityDto dto, String moneda){
        CuentaEntity cuenta = new CuentaEntity();
        cuenta.setId(dto.getId());
        cuenta.setMoneda(moneda);
        cuenta.setSaldo(dto.getSaldo());
        //cuenta.setOperacionsById(null);
        cuenta.setEstado(dto.getEstado());
        //cuenta.setRolsById(null);
        cuenta.setFechaApertura(dto.getFechaApertura());
        cuenta.setIban(dto.getIban());

        this.cuentaRepository.save(cuenta);
    }

    public void guardarSolicitarDesbloqueo(CuentaEntityDto dto, TipoEstadoEntityDto estado){
        CuentaEntity cuenta = new CuentaEntity();
        cuenta.setId(dto.getId());
        cuenta.setMoneda(dto.getMoneda());
        cuenta.setSaldo(dto.getSaldo());
        //List<OperacionEntityDto> operaciones = dto.getOperacionsById();
        //cuenta.setOperacionsById(dto.getOperacionsById());
        //cuenta.setOperacionsById(null);
        cuenta.setEstado(estado.getNombre());
        //cuenta.setRolsById(null);
        cuenta.setFechaApertura(dto.getFechaApertura());
        cuenta.setIban(dto.getIban());

        this.cuentaRepository.save(cuenta);
    }

    public TipoEstadoEntityDto buscarTipoEstadoPorId(){
        TipoEstadoEntity estado = this.tipoEstadoEntityRepository.buscarTipo(1);
        if(estado != null){
            return estado.toDTO();
        } else {
            return null;
        }
    }
}
