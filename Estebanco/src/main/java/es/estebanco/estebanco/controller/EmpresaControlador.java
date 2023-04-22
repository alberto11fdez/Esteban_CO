package es.estebanco.estebanco.controller;
import es.estebanco.estebanco.dao.*;
import es.estebanco.estebanco.entity.*;
import es.estebanco.estebanco.ui.FiltroOperacionEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.List;

@RequestMapping("/cuentaEmpresa")
@Controller
public class EmpresaControlador {

    private CuentaEntity cuentaEmpresa;
    private Integer idCuenta;
    private PersonaEntity persona;

    @Autowired
    protected CuentaRepository cuentaRepository;
    @Autowired
    protected OperacionRepository operacionRepository;
    @Autowired
    protected PersonaRepository personaRepository;
    @Autowired
    protected RolRepository rolRepository;
    @Autowired
    protected TipoOperacionEntityRepository tipoOperacionEntityRepository;

    @Autowired
    protected TipoMonedaEntityRepository tipoMonedaEntityRepository;

    /**2
 *Este metodo te leva al perfil de la cuenta de la Empresa.
 * Para ello se le ha pasado el id de la cuenta, se ha buscado la entidad en la base de datos
 * y se ha enviado al cuentaEmpresa.jsp
 */
    @GetMapping("/")
    public String goCuentaEmpresa(@RequestParam("id") Integer idCuentaEmpresa,@RequestParam("idPersona") Integer idPersona, Model model){

        cuentaEmpresa = cuentaRepository.findById(idCuentaEmpresa).orElse(null);
        model.addAttribute("cuentaEmpresa",cuentaEmpresa);

        PersonaEntity persona = personaRepository.findById(idPersona).orElse(null);
        this.persona=persona;
        model.addAttribute("persona",persona);

        List<OperacionEntity> operaciones = cuentaEmpresa.getOperacionsById();
        //List<OperacionEntity> operaciones = operacionRepository.obtenerListaOperaciones(cuentaEmpresa);
        model.addAttribute("operaciones",operaciones);

        List<PersonaEntity> socios = personaRepository.obtenerSocioEmpresa(cuentaEmpresa);
        model.addAttribute("socios",socios);

        model.addAttribute("rolrepository",rolRepository);

        model.addAttribute("tipo_operaciones",tipoOperacionEntityRepository.findAll());

        FiltroOperacionEmpresa filtro=new FiltroOperacionEmpresa();
        model.addAttribute("filtro",filtro);

        OperacionEntity operacion=new OperacionEntity();
        operacion.setCuentaByCuentaId(cuentaEmpresa);
        operacion.setPersonaByPersonaId(persona);
        model.addAttribute("operacion",operacion);

        return "cuentaEmpresa";
    }


    @GetMapping("/crearSocio")
    public String goCrearSocios(Model model,@RequestParam("idCuenta") Integer idCuenta){
        this.idCuenta=idCuenta;

        model.addAttribute("socio",new PersonaEntity());

        CuentaEntity cuentaEmpresa = cuentaRepository.findById(idCuenta).orElse(null);
        List<PersonaEntity> personasNoSocio = personaRepository.personasNoSociosEnCuentaEmpresa(cuentaEmpresa);
        List<PersonaEntity> personasSiSocio=personaRepository.obtenerSocioEmpresa(cuentaEmpresa);
        personasNoSocio.removeAll(personasSiSocio);
        model.addAttribute("personasNoSocio",personasNoSocio);

        RolEntity rolNuevo=new RolEntity();
        rolNuevo.setCuentaByCuentaId(cuentaEmpresa);
        model.addAttribute("rolNuevo",rolNuevo);

        return "crearSocio";
    }

    @PostMapping("/socio/guardar")
    public String doGuardar (@ModelAttribute("socio") PersonaEntity socio) {
        //crea al socio
        socio.setEstado("pendiente");
        this.personaRepository.save(socio);
        //Unimos la tabla persona y cuentaEmpresa a traves de la tabla rol
        RolEntity rol = new RolEntity();
        rol.setRol("socio");
        CuentaEntity cuenta = cuentaRepository.getById(this.idCuenta);
        rol.setCuentaByCuentaId(cuenta);
        rol.setPersonaByPersonaId(socio);
        this.rolRepository.save(rol);

        return "redirect:/cuentaEmpresa?id="+this.idCuenta;
    }

    @PostMapping("/socio/guardarYaExistente")
    public String doGuardar2(@ModelAttribute("rolNuevo") RolEntity rol){
        rol.setRol("socio");
        rol.setBloqueado_empresa((byte)0);
        rolRepository.save(rol);
        return "redirect:/cuentaEmpresa?id="+rol.getCuentaByCuentaId().getId();
    }

    @GetMapping("/socio/bloquear")
    public String bloquearSocio(@RequestParam ("id") Integer idSocio){
        RolEntity rol = rolRepository.obtenerRol_Persona_en_Empresa(idSocio,cuentaEmpresa.getId());
        rol.setBloqueado_empresa((byte) 1);
        rolRepository.save(rol);
        return "redirect:/cuentaEmpresa?id="+cuentaEmpresa.getId();
    }
    @GetMapping("/socio/activar")
    public String activarSocio(@RequestParam ("id") Integer idSocio){
        RolEntity rol = rolRepository.obtenerRol_Persona_en_Empresa(idSocio,cuentaEmpresa.getId());
        rol.setBloqueado_empresa((byte) 0);
        rolRepository.save(rol);
        return "redirect:/cuentaEmpresa?id="+cuentaEmpresa.getId();
    }

    @GetMapping("/mostrarTransferencia")
    public String mostrarTransferencia(@RequestParam("idCuenta") Integer idCuenta,@RequestParam("idPersona") Integer idPersona, Model model){
        CuentaEntity cuenta = cuentaRepository.findById(idCuenta).orElse(null);
        PersonaEntity persona=personaRepository.findById(idPersona).orElse(null);

        model.addAttribute("cuenta",cuenta);
        model.addAttribute("persona",persona);

        return "transferenciaEmpresa";
    }

    @PostMapping("/transfiriendoDinero")
    public String doTransferidoDinero(@RequestParam ("valor") Integer valor,
                                      @RequestParam ("id") Integer idCuenta,
                                      @RequestParam ("destino") String destino,
                                      Model model){
        CuentaEntity cuentaOrigen = this.cuentaRepository.cuentaOrigen(idCuenta);

        CuentaEntity cuentaDestino = this.cuentaRepository.cuentaDestinoTransferencia(destino);

        String urlTo = "redirect:/cuentaEmpresa/?id="+cuentaOrigen.getId()+"&idPersona="+persona.getId();

        if(cuentaDestino==null){
            model.addAttribute("error", "Cuenta destino no encontrada");
            return this.mostrarEditadoTransferencia(cuentaOrigen,model);
        } else {
            if(valor<0||cuentaOrigen.getSaldo()<valor){
                model.addAttribute("error", "Cantidad incorrecta introduce un nuevo valor");
                return this.mostrarEditadoTransferencia(cuentaOrigen,model);
            } else if (cuentaDestino==cuentaOrigen) {
                model.addAttribute("error", "La cuenta origen y la cuenta destino son iguales");
                return this.mostrarEditadoTransferencia(cuentaOrigen,model);
            } else if (cuentaOrigen.getEstado().equals("bloqueado")) {
                model.addAttribute("error", "No puede operar con esta cuenta porque esta bloqueada");
                return this.mostrarEditadoTransferencia(cuentaOrigen,model);
            } else if (cuentaDestino.getEstado().equals("bloqueado")) {
                model.addAttribute("error", "No puede transferir a esta cuenta porque esta bloqueada");
                return this.mostrarEditadoTransferencia(cuentaOrigen,model);
            } else {
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo()-valor);
                cuentaDestino.setSaldo(cuentaDestino.getSaldo()+valor);
                this.cuentaRepository.save(cuentaOrigen);
                this.cuentaRepository.save(cuentaDestino);
                this.nuevaOperacionSacarTransferencia(cuentaOrigen,cuentaDestino,valor);

            }
        }
        return urlTo;
    }

    protected String mostrarEditadoTransferencia(CuentaEntity cuenta, Model model) {
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("persona",this.persona);
        return "transferenciaEmpresa";
    }

    protected void nuevaOperacionSacarTransferencia(CuentaEntity cuentaOrigen, CuentaEntity cuentaDestino, Integer valor){
        Date now = new Date();
        OperacionEntity operacion = new OperacionEntity();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(1);
        operacion.setCantidad(valor);
        operacion.setCuentaByCuentaId(cuentaOrigen);
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        operacion.setIbanCuentaDestinoOrigen(cuentaDestino.getIban());
        operacion.setPersonaByPersonaId(personaRepository.propietarioDeCuenta(cuentaOrigen));
        this.operacionRepository.save(operacion);
    }

    @GetMapping("/mostrarDivisa")
    public String mostrarDivisa(@RequestParam("idCuenta") Integer idCuenta,@RequestParam("idPersona") Integer idPersona, Model model){
        CuentaEntity cuenta = this.cuentaRepository.findById(idCuenta).orElse(null);
        List<TipoMonedaEntity> monedas = this.tipoMonedaEntityRepository.findAll();
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("monedas", monedas);
        model.addAttribute("persona",this.persona);
        return "cambioDivisaPersona";
    }
    @PostMapping("/guardarDivisa")
    public String doGuardarDivisa(@ModelAttribute("cuenta") CuentaEntity cuentaCambio,
                                  @RequestParam("moneda") String moneda){
        TipoMonedaEntity moneda1 = this.tipoMonedaEntityRepository.buscarMoneda(moneda);
        CuentaEntity cuenta = cuentaRepository.findById(cuentaCambio.getId()).orElse(null);
        cuenta.setMoneda(moneda1.getMoneda());
        this.cuentaRepository.save(cuenta);
        this.nuevaOperacionCambioDivisa(cuenta);
        return "redirect:/cuentaEmpresa/?id="+cuenta.getId()+"&idPersona="+this.persona.getId();
    }
    protected void nuevaOperacionCambioDivisa(CuentaEntity cuenta){
        Date now = new Date();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(3);
        OperacionEntity operacion = new OperacionEntity();
        operacion.setCuentaByCuentaId(cuenta);
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        operacion.setPersonaByPersonaId(persona);
        this.operacionRepository.save(operacion);
    }
}
