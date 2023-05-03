package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.*;
import es.estebanco.estebanco.dto.*;
import es.estebanco.estebanco.entity.*;
import es.estebanco.estebanco.ui.FiltroOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CuentaPersonaService {
    @Autowired
    protected TipoOperacionEntityRepository tipoOperacionEntityRepository;
    @Autowired
    protected TipoMonedaEntityRepository tipoMonedaEntityRepository;
    @Autowired
    protected CuentaRepository cuentaRepository;
    @Autowired
    PersonaRepository personaRepository;
    @Autowired
    protected TipoRolRepository tipoRolRepository;
    @Autowired
    protected RolRepository rolRepository;
    @Autowired
    protected OperacionRepository operacionRepository;

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
        if(persona!=null){
            return persona.toDTO();
        }else{
            return null;
        }
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
        ArrayList dtos = new ArrayList<ConversacionEntityDto>();
        List<ConversacionEntity> lista = personaRepository.conversacionPorPersona(persona.getId());
        lista.forEach((final ConversacionEntity conver)->dtos.add(conver.toDTO()));
        return dtos;
    }

    public List<OperacionEntityDto> listarOperaciones(CuentaEntityDto cuenta, FiltroOperacion filtro) {
        ArrayList dtos = new ArrayList<OperacionEntityDto>();
        List<OperacionEntity> operaciones = null;
        switch(filtro.getTipo()){
            case "sacar":
                operaciones = this.operacionRepository.operacionesPorCuentaYTipo(cuenta.getId(),filtro.getTipo());
                break;
            case "meter":
                operaciones = this.operacionRepository.operacionesPorCuentaYTipo(cuenta.getId(),filtro.getTipo());
                break;
            case "cambio divisa":
                operaciones = this.operacionRepository.operacionesPorCuentaYTipo(cuenta.getId(),filtro.getTipo());
                break;
            case "euro":
                operaciones = this.operacionRepository.operacionesPorCuentaYMoneda(cuenta.getId(),filtro.getTipo());
                break;
            case "libra":
                operaciones = this.operacionRepository.operacionesPorCuentaYMoneda(cuenta.getId(),filtro.getTipo());
                break;
            case "ordenar por fecha":
                operaciones = this.operacionRepository.operacionesPorCuentaOrdenadoPorFecha(cuenta.getId());
                break;
            case "ordenar por cantidad":
                operaciones = this.operacionRepository.operacionesPorCuentaOrdenadoPorCantidad(cuenta.getId());
                break;
            default:
                operaciones = this.operacionRepository.operacionesPorCuenta(cuenta.getId());

        }
        operaciones.forEach((final OperacionEntity conver)->dtos.add(conver.toDTO()));
        return dtos;
    }

    public PersonaEntityDto propietarioDeCuenta(CuentaEntityDto cuenta) {
        return personaRepository.propietarioDeCuenta(cuenta.getId()).toDTO();
    }

    public void nuevaOperacionMeterTransferencia(CuentaEntityDto cuentaOrigen, CuentaEntityDto cuentaDestino, Integer valor) {
        Date now = new Date();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(2);
        OperacionEntity operacion = new OperacionEntity();
        operacion.setCantidad(valor);
        operacion.setCuentaByCuentaId(this.cuentaRepository.findById(cuentaDestino.getId()).orElse(null));
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        operacion.setIbanCuentaDestinoOrigen(cuentaOrigen.getIban());
        operacion.setPersonaByPersonaId(personaRepository.propietarioDeCuenta(cuentaOrigen.getId()));
        this.operacionRepository.save(operacion);
    }

    public void nuevaOperacionSacarTransferencia(CuentaEntityDto cuentaOrigen, CuentaEntityDto cuentaDestino, Integer valor) {
        Date now = new Date();
        OperacionEntity operacion = new OperacionEntity();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(1);
        operacion.setCantidad(valor);
        operacion.setCuentaByCuentaId(this.cuentaRepository.findById(cuentaOrigen.getId()).orElse(null));
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        operacion.setIbanCuentaDestinoOrigen(cuentaDestino.getIban());
        operacion.setPersonaByPersonaId(personaRepository.propietarioDeCuenta(cuentaOrigen.getId()));
        this.operacionRepository.save(operacion);
    }

    public CuentaEntityDto cuentaOrigen(Integer idCuenta) {
        return cuentaRepository.cuentaOrigen(idCuenta).toDTO();
    }

    public CuentaEntityDto cuentaDestinoTransferencia(String destino) {
        return cuentaRepository.cuentaDestinoTransferencia(destino).toDTO();
    }

    public void guardarSacar(CuentaEntityDto dto, Integer valor) {
        CuentaEntity cuenta = new CuentaEntity();

        cuenta.setId(dto.getId());
        cuenta.setMoneda(dto.getMoneda());
        cuenta.setSaldo(dto.getSaldo()-valor);
        cuenta.setOperacionsById(dto.getOperacionsById());
        cuenta.setEstado(dto.getEstado());
        cuenta.setRolsById(dto.getRolsById());
        cuenta.setFechaApertura(dto.getFechaApertura());
        cuenta.setIban(dto.getIban());

        this.cuentaRepository.save(cuenta);
    }

    public void guardarMeter(CuentaEntityDto dto, Integer valor) {
        CuentaEntity cuenta = new CuentaEntity();

        cuenta.setId(dto.getId());
        cuenta.setMoneda(dto.getMoneda());
        cuenta.setSaldo(dto.getSaldo()+valor);
        cuenta.setOperacionsById(dto.getOperacionsById());
        cuenta.setEstado(dto.getEstado());
        cuenta.setRolsById(dto.getRolsById());
        cuenta.setFechaApertura(dto.getFechaApertura());
        cuenta.setIban(dto.getIban());

        this.cuentaRepository.save(cuenta);
    }

    public List<TipoMonedaEntityDto> listarTiposMonedas() {
        ArrayList dtos = new ArrayList<TipoMonedaEntityDto>();
        List<TipoMonedaEntity> lista =  tipoMonedaEntityRepository.findAll();
        lista.forEach((final TipoMonedaEntity moneda)->dtos.add(moneda.toDTO()));
        return dtos;
    }

    public void nuevaOperacionCambioDivisa(CuentaEntityDto cuenta) {
        Date now = new Date();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(3);
        OperacionEntity operacion = new OperacionEntity();
        operacion.setCuentaByCuentaId(this.cuentaRepository.findById(cuenta.getId()).orElse(null));
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        operacion.setPersonaByPersonaId(personaRepository.propietarioDeCuenta(cuenta.getId()));
        this.operacionRepository.save(operacion);
    }

    public TipoMonedaEntityDto buscarMoneda(String moneda) {
        return this.tipoMonedaEntityRepository.buscarMoneda(moneda).toDTO();
    }

    public void guardarCuentaCambioDivisa(CuentaEntityDto dto, String moneda) {
        CuentaEntity cuenta = new CuentaEntity();
        cuenta.setId(dto.getId());
        cuenta.setMoneda(moneda);
        cuenta.setSaldo(dto.getSaldo());
        cuenta.setOperacionsById(dto.getOperacionsById());
        cuenta.setEstado(dto.getEstado());
        cuenta.setRolsById(dto.getRolsById());
        cuenta.setFechaApertura(dto.getFechaApertura());
        cuenta.setIban(dto.getIban());

        this.cuentaRepository.save(cuenta);
    }

    public PersonaEntityDto nuevaPersona() {
        PersonaEntity persona = new PersonaEntity();
        PersonaEntityDto dto = persona.toDTO();
        return dto;
    }

    public Integer guardarPersona(PersonaEntityDto dto){
        PersonaEntity persona;
        persona = new PersonaEntity();

        persona.setDni(dto.getDni());
        persona.setUsuario(dto.getUsuario());
        persona.setEstado(dto.getEstado());
        persona.setNombre(dto.getNombre());
        persona.setApellido1(dto.getApellido1());
        persona.setApellido2(dto.getApellido2());
        persona.setContraseña(dto.getContraseña());
        persona.setCorreo(dto.getCorreo());
        persona.setDireccion(dto.getDireccion());
        persona.setTelefono(dto.getTelefono());


        this.personaRepository.save(persona);

        return persona.getId();
    }
    public Integer saveCuentaNueva(CuentaEntityDto dto) {
        CuentaEntity cuenta = new CuentaEntity();
        cuenta.setEstado("esperandoConfirmacion");

        cuentaRepository.save(cuenta);
        return cuenta.getId();
    }
}
