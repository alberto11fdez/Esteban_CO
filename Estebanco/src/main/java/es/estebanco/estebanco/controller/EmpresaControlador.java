package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.*;
import es.estebanco.estebanco.dto.*;
import es.estebanco.estebanco.entity.*;
import es.estebanco.estebanco.service.*;
import es.estebanco.estebanco.ui.FiltroOperacion;
import es.estebanco.estebanco.ui.FiltroOperacionSocio;
import es.estebanco.estebanco.ui.FiltroSocios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@RequestMapping("/cuentaEmpresa")
@Controller
public class EmpresaControlador {


    @Autowired
    protected CuentaPersonaService cuentaPersonaService;
   // protected CuentaRepository cuentaRepository;
    @Autowired
    protected OperacionService operacionService;
    //protected OperacionRepository operacionRepository;

    @Autowired
    protected PersonaService personaService;
    //protected PersonaRepository personaRepository;
    @Autowired
    protected RolService rolService;
    //protected RolRepository rolRepository;

    @Autowired
    protected TipoOperacionService tipoOperacionService;
    //protected TipoOperacionEntityRepository tipoOperacionEntityRepository;

    @Autowired
    protected  TipoMonedaService tipoMonedaService;
    //protected TipoMonedaEntityRepository tipoMonedaEntityRepository;


    @GetMapping("/")
    public String goCuentaEmpresa(@RequestParam("id") Integer idCuentaEmpresa, @RequestParam("idPersona") Integer idPersona, Model model, HttpSession session) {

        //CuentaEntity cuentaEmpresa = cuentaRepository.findById(idCuentaEmpresa).orElse(null);
        CuentaEntityDto cuentaEmpresa = cuentaPersonaService.encontrarCuentaPorId(idCuentaEmpresa);
        //PersonaEntity persona = personaRepository.findById(idPersona).orElse(null);
        PersonaEntityDto persona = personaService.buscarPersonaPorId(idPersona);

        model.addAttribute("cuentaEmpresa", cuentaEmpresa);

        model.addAttribute("persona", persona);

        //List<OperacionEntity> operaciones = cuentaEmpresa.getOperacionsById();
        List<OperacionEntityDto> operaciones = operacionService.operacionesPorCuenta(cuentaEmpresa.getId());
        model.addAttribute("operaciones", operaciones);

        //List<PersonaEntity> socios = personaRepository.obtenerSocioEmpresa(cuentaEmpresa);
        List<PersonaEntityDto> socios = personaService.obtenerSocioEmpresa(cuentaEmpresa,idPersona);

        model.addAttribute("socios", socios);

        List<Integer> sociosActivos = rolService.obtenerSociosActivos(idCuentaEmpresa);
        model.addAttribute("sociosActivos",sociosActivos);
        List<Integer> sociosBloqueados=rolService.obtenerSocioBloqueados(idCuentaEmpresa);
        model.addAttribute("sociosBloqueados",sociosBloqueados);

        model.addAttribute("tipo_operaciones", tipoMonedaService.findAll());

        //OperacionEntity operacion = new OperacionEntity();
        OperacionEntityDto operacion=new OperacionEntityDto();
        operacion.setCuentaByCuentaId(cuentaEmpresa);
        operacion.setPersonaByPersonaId(persona);
        model.addAttribute("operacion", operacion);

        model.addAttribute("filtroOperacionSocio", new FiltroOperacionSocio());

        //List<OperacionEntity> operacionesRecibidas = operacionRepository.buscarOperacionesRecibidas(cuentaEmpresa.getIban());
        List<OperacionEntityDto> operacionesRecibidas = operacionService.buscarOperacionesRecibidas(cuentaEmpresa.getIban());
        model.addAttribute("operacionesRecibidas",operacionesRecibidas);


        FiltroOperacion filtroOperacion = new FiltroOperacion();
        model.addAttribute("filtroOperacion", filtroOperacion);
        model.addAttribute("tipos_filtro",filtroOperacion.getTipos_filtro());

        FiltroSocios filtroSocios = new FiltroSocios();
        model.addAttribute("filtroSocios",filtroSocios);
        model.addAttribute("tipos_filtro_socio",filtroSocios.getTipos_filtro_socio());


        return "cuentaEmpresa";
    }

    @PostMapping("/filtrar")
    public String filtrar(@ModelAttribute("filtroOperacion") FiltroOperacion filtroOperacion,Model model,HttpSession session){

       // List<OperacionEntity> operaciones;
        List<OperacionEntityDto> operaciones;
        //CuentaEntity cuentaEmpresa =(CuentaEntity) session.getAttribute("cuenta");
        CuentaEntityDto cuentaEmpresa=(CuentaEntityDto) session.getAttribute("cuenta");
        if(filtroOperacion==null){
            filtroOperacion = new FiltroOperacion();
            filtroOperacion.setIdpersona(cuentaEmpresa.getId());
        }
        switch(filtroOperacion.getTipo()){
            case "sacar":
                //operaciones = this.operacionRepository.operacionesPorCuentaYTipo(cuentaEmpresa.getId(),filtroOperacion.getTipo());
                operaciones=operacionService.operacionesPorCuentaYTipo(cuentaEmpresa.getId(),filtroOperacion.getTipo());
                break;
            case "meter":
                //operaciones = this.operacionRepository.operacionesPorCuentaYTipo(cuentaEmpresa.getId(),filtroOperacion.getTipo());
                operaciones=operacionService.operacionesPorCuentaYTipo(cuentaEmpresa.getId(),filtroOperacion.getTipo());
                break;
            case "cambio divisa":
               // operaciones = this.operacionRepository.operacionesPorCuentaYTipo(cuentaEmpresa.getId(),filtroOperacion.getTipo());
                operaciones=operacionService.operacionesPorCuentaYTipo(cuentaEmpresa.getId(),filtroOperacion.getTipo());
                break;
            case "euro":
                //operaciones = this.operacionRepository.operacionesPorCuentaYMoneda(cuentaEmpresa.getId(),filtroOperacion.getTipo());
                operaciones=operacionService.operacionesPorCuentaYMoneda(cuentaEmpresa.getId(),filtroOperacion.getTipo());
                break;
            case "libra":
                //operaciones = this.operacionRepository.operacionesPorCuentaYMoneda(cuentaEmpresa.getId(),filtroOperacion.getTipo());
                operaciones=operacionService.operacionesPorCuentaYMoneda(cuentaEmpresa.getId(),filtroOperacion.getTipo());
                break;
            case "ordenar por fecha":
                //operaciones = this.operacionRepository.operacionesPorCuentaOrdenadoPorFecha(cuentaEmpresa.getId());
                operaciones=operacionService.operacionesPorCuentaOrdenadoPorFecha(cuentaEmpresa.getId());
                break;
            case "ordenar por cantidad":
                //operaciones = this.operacionRepository.operacionesPorCuentaOrdenadoPorCantidad(cuentaEmpresa.getId());
                operaciones=operacionService.operacionesPorCuentaOrdenadoPorCantidad(cuentaEmpresa.getId());
                break;
            default:
                //operaciones = this.operacionRepository.operacionesPorCuenta(cuentaEmpresa.getId());
                operaciones=operacionService.operacionesPorCuenta(cuentaEmpresa.getId());
        }

        //PersonaEntity personaAux= (PersonaEntity) session.getAttribute("persona");
        PersonaEntityDto persona=(PersonaEntityDto) session.getAttribute("persona");
        //PersonaEntity persona = personaRepository.findById(personaAux.getId()).orElse(null);
        persona = personaService.encontrarPersona(persona.getId());

        model.addAttribute("cuentaEmpresa", cuentaEmpresa);

        model.addAttribute("persona", persona);

        model.addAttribute("operaciones", operaciones);

        //List<PersonaEntity> socios = personaRepository.obtenerSocioEmpresa(cuentaEmpresa);
        List<PersonaEntityDto> socios=personaService.obtenerSocioEmpresa(cuentaEmpresa, persona.getId());
        model.addAttribute("socios", socios);

        List<Integer> sociosActivos = rolService.obtenerSociosActivos(cuentaEmpresa.getId());
        model.addAttribute("sociosActivos",sociosActivos);
        List<Integer> sociosBloqueados=rolService.obtenerSocioBloqueados(cuentaEmpresa.getId());
        model.addAttribute("sociosBloqueados",sociosBloqueados);


        model.addAttribute("tipo_operaciones", tipoOperacionService.findAll());

        //OperacionEntity operacion = new OperacionEntity();
        OperacionEntityDto operacion= new OperacionEntityDto();
        operacion.setCuentaByCuentaId(cuentaEmpresa);
        operacion.setPersonaByPersonaId(persona);
        model.addAttribute("operacion", operacion);

        model.addAttribute("filtroOperacionSocio", new FiltroOperacionSocio());

        //List<OperacionEntity> operacionesRecibidas = operacionRepository.buscarOperacionesRecibidas(cuentaEmpresa.getIban());
        List<OperacionEntityDto> operacionesRecibidas=operacionService.buscarOperacionesRecibidas(cuentaEmpresa.getIban());
        model.addAttribute("operacionesRecibidas",operacionesRecibidas);

        //filtros para las distintas listas
        model.addAttribute("filtroOperacion", filtroOperacion);
        model.addAttribute("tipos_filtro",filtroOperacion.getTipos_filtro());

        FiltroSocios filtroSocios = new FiltroSocios();
        model.addAttribute("filtroSocios",filtroSocios);
        model.addAttribute("tipos_filtro_socio",filtroSocios.getTipos_filtro_socio());

        return "cuentaEmpresa";
    }

    @GetMapping("/crearSocio")
    public String goCrearSocios(Model model, @RequestParam("idCuenta") Integer idCuenta, HttpSession session) {

        PersonaEntityDto persona =(PersonaEntityDto) session.getAttribute("persona");
       // model.addAttribute("socio", new PersonaEntity());
        model.addAttribute("socio",new PersonaEntityDto());

        //CuentaEntity cuentaEmpresa = cuentaRepository.findById(idCuenta).orElse(null);
        CuentaEntityDto cuentaEmpresa=cuentaPersonaService.encontrarCuentaPorId(idCuenta);
        //List<PersonaEntity> personasNoSocio = personaRepository.personasNoSociosEnCuentaEmpresa(cuentaEmpresa);
        List<PersonaEntityDto> personasNoSocio=personaService.personasNoSociosEnCuentaEmpresa(cuentaEmpresa,persona.getId());
        //List<PersonaEntity> personasSiSocio = personaRepository.obtenerSocioEmpresa(cuentaEmpresa);
        List<PersonaEntityDto> personasSiSocio=personaService.obtenerSocioEmpresa(cuentaEmpresa,persona.getId());

        personasNoSocio.removeAll(personasSiSocio);
        model.addAttribute("personasNoSocio", personasNoSocio);

        //RolEntity rolNuevo = new RolEntity();
        RolEntityDto rolNuevo=new RolEntityDto();
        rolNuevo.setCuentaByCuentaId(cuentaEmpresa);
        model.addAttribute("rolNuevo", rolNuevo);

        return "crearSocio";
    }

    @PostMapping("/socio/guardar")
    public String doGuardarSocioCreado(@ModelAttribute("socio") PersonaEntityDto socio, HttpSession session) {

        //CuentaEntity cuentaEmpresa = (CuentaEntity) session.getAttribute("cuenta");
        CuentaEntityDto cuentaEmpresa=(CuentaEntityDto) session.getAttribute("cuenta");
        int idCuenta = cuentaEmpresa.getId();
        //PersonaEntity persona=(PersonaEntity) session.getAttribute("persona") ;
        PersonaEntityDto persona=(PersonaEntityDto) session.getAttribute("persona");

        //crea al socio
        socio.setEstado("esperandoConfirmacion");
        //this.personaRepository.save(socio);
        int idSocio=cuentaPersonaService.guardarPersona(socio);
        socio=cuentaPersonaService.encontrarPersona(idSocio);
        //Unimos la tabla persona y cuentaEmpresa a traves de la tabla rol
        //RolEntity rol = new RolEntity();
        RolEntityDto rol=new RolEntityDto();
        rol.setRol("socio");
        //CuentaEntity cuenta = cuentaRepository.getById(idCuenta);
        CuentaEntityDto cuenta=cuentaPersonaService.encontrarCuentaPorId(idCuenta);
        rol.setCuentaByCuentaId(cuenta);
        rol.setPersonaByPersonaId(socio);
        rol.setBloqueado_empresa((byte)0);

        //this.rolRepository.save(rol);
        rolService.saveRolsinId(rol);

        if(cuentaEmpresa.getEstado().equals("esperandoConfirmacion")){
            return "redirect:/persona/?id="+persona.getId();
        }else{
            return "redirect:/cuentaEmpresa/?id=" + cuentaEmpresa.getId()+"&idPersona="+persona.getId();
        }
    }


    @PostMapping("/socio/guardarYaExistente")
    public String doGuardarSocioYaExistente(@ModelAttribute("rolNuevo") RolEntityDto rol, HttpSession session) {

        rol.setPersonaByPersonaId(personaService.encontrarPersona(rol.getIdPersona()));

        CuentaEntityDto cuentaEmpresa =(CuentaEntityDto) session.getAttribute("cuenta");
        int idCuenta = cuentaEmpresa.getId();
        CuentaEntityDto cuenta=cuentaPersonaService.encontrarCuentaPorId(idCuenta);
        rol.setCuentaByCuentaId(cuenta);

        rol.setRol("socio");
        rol.setBloqueado_empresa((byte) 0);

        rolService.saveRolsinId(rol);

        PersonaEntityDto persona = (PersonaEntityDto) session.getAttribute("persona");

        if(cuentaEmpresa.getEstado().equals("esperandoConfirmacion")){
            return "redirect:/persona/?id="+persona.getId();
        }else{
            return "redirect:/cuentaEmpresa/?id=" + cuentaEmpresa.getId()+"&idPersona="+persona.getId();
        }
    }

    @GetMapping("/socio/bloquear")
    public String bloquearSocio(@RequestParam("id") Integer idSocio, HttpSession session)
    {

        CuentaEntityDto cuentaEmpresa=(CuentaEntityDto) session.getAttribute("cuenta");

        PersonaEntityDto persona=(PersonaEntityDto) session.getAttribute("persona");

        //RolEntity rol = rolRepository.obtenerRol_Persona_en_Empresa(idSocio, cuentaEmpresa.getId());
        RolEntityDto rol=rolService.obtenerRol_Persona_en_Empresa(idSocio, cuentaEmpresa.getId());
        rol.setBloqueado_empresa((byte) 1);
        rolService.saveRol(rol);

        return "redirect:/cuentaEmpresa/?id=" + cuentaEmpresa.getId()+"&idPersona="+persona.getId();
    }

    @GetMapping("/socio/activar")
    public String activarSocio(@RequestParam("id") Integer idSocio, HttpSession session) {
        CuentaEntityDto cuentaEmpresa=(CuentaEntityDto) session.getAttribute("cuenta");

        PersonaEntityDto persona=(PersonaEntityDto) session.getAttribute("persona");

        //RolEntity rol = rolRepository.obtenerRol_Persona_en_Empresa(idSocio, cuentaEmpresa.getId());
        RolEntityDto rol=rolService.obtenerRol_Persona_en_Empresa(idSocio, cuentaEmpresa.getId());
        rol.setBloqueado_empresa((byte) 0);
        rolService.saveRol(rol);

        return "redirect:/cuentaEmpresa/?id=" + cuentaEmpresa.getId()+"&idPersona="+persona.getId();
    }


    @GetMapping("/mostrarTransferencia")
    public String mostrarTransferencia(@RequestParam("idCuenta") Integer idCuenta, @RequestParam("idPersona") Integer idPersona, Model model) {
        //CuentaEntity cuenta = cuentaRepository.findById(idCuenta).orElse(null);
        CuentaEntityDto cuenta = cuentaPersonaService.encontrarCuentaPorId(idCuenta);
        //PersonaEntity persona = personaRepository.findById(idPersona).orElse(null);
        PersonaEntityDto persona = personaService.buscarPersonaPorId(idPersona);

        model.addAttribute("cuenta", cuenta);
        model.addAttribute("persona", persona);

        return "transferenciaEmpresa";
    }

    @PostMapping("/transfiriendoDinero")
    public String doTransferidoDinero(@RequestParam("valor") Integer valor,
                                      @RequestParam("id") Integer idCuenta,
                                      @RequestParam("destino") String destino,
                                      Model model, HttpSession session) {

       // PersonaEntity persona = (PersonaEntity) session.getAttribute("persona");
        PersonaEntityDto persona=(PersonaEntityDto) session.getAttribute("persona");
        //CuentaEntity cuentaOrigen = this.cuentaRepository.cuentaOrigen(idCuenta);
        CuentaEntityDto cuentaOrigen=cuentaPersonaService.cuentaOrigen(idCuenta);
        //CuentaEntity cuentaDestino = this.cuentaRepository.cuentaDestinoTransferencia(destino);
        CuentaEntityDto cuentaDestino=cuentaPersonaService.cuentaDestinoTransferencia(destino);

        String urlTo = "redirect:/cuentaEmpresa/?id=" + cuentaOrigen.getId() + "&idPersona=" + persona.getId();

        if (cuentaDestino == null) {
            model.addAttribute("error", "Cuenta destino no encontrada");
            return this.mostrarEditadoTransferencia(cuentaOrigen, model, session);
        } else {
            if (valor < 0 || cuentaOrigen.getSaldo() < valor) {
                model.addAttribute("error", "Cantidad incorrecta introduce un nuevo valor");
                return this.mostrarEditadoTransferencia(cuentaOrigen, model, session);
            } else if (cuentaDestino == cuentaOrigen) {
                model.addAttribute("error", "La cuenta origen y la cuenta destino son iguales");
                return this.mostrarEditadoTransferencia(cuentaOrigen, model, session);
            } else if (cuentaOrigen.getEstado().equals("bloqueado")) {
                model.addAttribute("error", "No puede operar con esta cuenta porque esta bloqueada");
                return this.mostrarEditadoTransferencia(cuentaOrigen, model, session);
            } else if (cuentaDestino.getEstado().equals("bloqueado")) {
                model.addAttribute("error", "No puede transferir a esta cuenta porque esta bloqueada");
                return this.mostrarEditadoTransferencia(cuentaOrigen, model, session);
            } else {
                cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - valor);
                cuentaDestino.setSaldo(cuentaDestino.getSaldo() + valor);
                this.cuentaPersonaService.saveCuenta(cuentaOrigen);
                this.cuentaPersonaService.saveCuenta(cuentaDestino);
                this.nuevaOperacionSacarTransferencia(cuentaOrigen, cuentaDestino, valor,session);

            }
        }
        return urlTo;
    }

    protected String mostrarEditadoTransferencia(CuentaEntityDto cuenta, Model model, HttpSession session) {
        //PersonaEntity persona = (PersonaEntity) session.getAttribute("persona");
        PersonaEntityDto persona = (PersonaEntityDto) session.getAttribute("persona");
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("persona", persona);
        return "transferenciaEmpresa";
    }

    protected void nuevaOperacionSacarTransferencia(CuentaEntityDto cuentaOrigen, CuentaEntityDto cuentaDestino, Integer valor,HttpSession session) {

        //PersonaEntity persona = (PersonaEntity) session.getAttribute("persona");
        PersonaEntityDto persona = (PersonaEntityDto) session.getAttribute("persona");
        int idPersona=persona.getId();

        Date now = new Date();
       // OperacionEntity operacion = new OperacionEntity();
        OperacionEntityDto operacion=new OperacionEntityDto();
        //TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(1);
        TipoOperacionEntityDto tipo =tipoOperacionService.buscarTipo(1);
        operacion.setCantidad(valor);
        operacion.setCuentaByCuentaId(cuentaOrigen);
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        operacion.setIbanCuentaDestinoOrigen(cuentaDestino.getIban());
        operacion.setPersonaByPersonaId(personaService.encontrarPersona(idPersona));
        operacionService.save(operacion);
    }

    @GetMapping("/mostrarDivisa")
    public String mostrarDivisa(@RequestParam("idCuenta") Integer idCuenta, @RequestParam("idPersona") Integer idPersona, Model model, HttpSession session) {
       // CuentaEntity cuenta = this.cuentaRepository.findById(idCuenta).orElse(null);
        CuentaEntityDto cuenta = cuentaPersonaService.encontrarCuentaPorId(idCuenta);
        //List<TipoMonedaEntity> monedas = this.tipoMonedaEntityRepository.findAll();
        List<TipoMonedaEntityDto> monedas = tipoMonedaService.findAll();
        //PersonaEntity persona = (PersonaEntity) session.getAttribute("persona");
        PersonaEntityDto persona = (PersonaEntityDto) session.getAttribute("persona");
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("monedas", monedas);
        model.addAttribute("persona", persona);

        return "cambioDivisaEmpresa";
    }

    @PostMapping("/guardarDivisa")
    public String doGuardarDivisa(@ModelAttribute("cuenta") CuentaEntityDto cuentaCambio,
                                  @RequestParam("moneda") String moneda, HttpSession session) {

        //TipoMonedaEntity moneda1 = this.tipoMonedaEntityRepository.buscarMoneda(moneda);
        TipoMonedaEntityDto moneda1=tipoMonedaService.buscarMoneda(moneda);
        //PersonaEntity persona = (PersonaEntity) session.getAttribute("persona");
        PersonaEntityDto persona=(PersonaEntityDto) session.getAttribute("persona");
        //CuentaEntity cuenta = cuentaRepository.findById(cuentaCambio.getId()).orElse(null);
        CuentaEntityDto cuenta = cuentaPersonaService.encontrarCuentaPorId(cuentaCambio.getId());
        cuenta.setMoneda(moneda1.getMoneda());
        //this.cuentaRepository.save(cuenta);
        cuentaPersonaService.saveCuenta(cuenta);
        this.nuevaOperacionCambioDivisa(cuenta, session);
        return "redirect:/cuentaEmpresa/?id=" + cuenta.getId() + "&idPersona=" + persona.getId();
    }

    protected void nuevaOperacionCambioDivisa(CuentaEntityDto cuenta, HttpSession session) {
        //PersonaEntity persona = (PersonaEntity) session.getAttribute("persona");
        PersonaEntityDto persona =(PersonaEntityDto) session.getAttribute("persona");
        Date now = new Date();
        //TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(3);
        TipoOperacionEntityDto tipo=tipoOperacionService.buscarTipo(3);
        //OperacionEntity operacion = new OperacionEntity();
        OperacionEntityDto operacion=new OperacionEntityDto();
        operacion.setCuentaByCuentaId(cuenta);
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        operacion.setPersonaByPersonaId(persona);

        this.operacionService.save(operacion);
    }

    @PostMapping("/filtroOperacionSocio")
    public String filtroOperacionSocio(@ModelAttribute("filtroOperacionSocio") FiltroOperacionSocio filtroOperacionSocio, Model model, HttpSession session) {
        //PersonaEntity persona = (PersonaEntity) session.getAttribute("persona");
        PersonaEntityDto persona =(PersonaEntityDto) session.getAttribute("persona");
        //CuentaEntity cuentaEmpresa = (CuentaEntity) session.getAttribute("cuenta");
        CuentaEntityDto cuentaEmpresa = (CuentaEntityDto) session.getAttribute("cuenta");

        model.addAttribute("cuentaEmpresa", cuentaEmpresa);

        model.addAttribute("persona", persona);

        //PersonaEntity socioFiltro = personaRepository.findById(filtroOperacionSocio.getIdSocio()).orElse(null);
        PersonaEntityDto socioFiltro=personaService.buscarPersonaPorId(filtroOperacionSocio.getIdSocio());

        //List<OperacionEntity> operaciones = operacionRepository.getOperacionesSocio(socioFiltro, cuentaEmpresa);
        List<OperacionEntityDto> operaciones=operacionService.getOperacionesSocio(socioFiltro, cuentaEmpresa);

        model.addAttribute("operaciones", operaciones);

       // List<PersonaEntity> socios = personaRepository.obtenerSocioEmpresa(cuentaEmpresa);
        List<PersonaEntityDto> socios=personaService.obtenerSocioEmpresa(cuentaEmpresa, persona.getId());
        model.addAttribute("socios", socios);

        List<Integer> sociosActivos = rolService.obtenerSociosActivos(cuentaEmpresa.getId());
        model.addAttribute("sociosActivos",sociosActivos);
        List<Integer> sociosBloqueados=rolService.obtenerSocioBloqueados(cuentaEmpresa.getId());
        model.addAttribute("sociosBloqueados",sociosBloqueados);


        model.addAttribute("tipo_operaciones", tipoMonedaService.findAll());



        //OperacionEntity operacion = new OperacionEntity();
        OperacionEntityDto operacion= new OperacionEntityDto();
        operacion.setCuentaByCuentaId(cuentaEmpresa);
        operacion.setPersonaByPersonaId(persona);
        model.addAttribute("operacion", operacion);

        List<OperacionEntityDto> operacionesRecibidas = operacionService.buscarOperacionesRecibidas(cuentaEmpresa.getIban());
        model.addAttribute("operacionesRecibidas",operacionesRecibidas);


        model.addAttribute("filtroOperacionSocio", new FiltroOperacionSocio());

        FiltroOperacion filtro = new FiltroOperacion();
        model.addAttribute("filtroOperacion",filtro);
        model.addAttribute("tipos_filtro",filtro.getTipos_filtro());

        FiltroSocios filtroSocios = new FiltroSocios();
        model.addAttribute("filtroSocios",filtroSocios);
        model.addAttribute("tipos_filtro_socio",filtroSocios.getTipos_filtro_socio());
        return "cuentaEmpresa";
    }

    @PostMapping("/filtrarSocios")
    public String filtroSocios(@ModelAttribute("filtroSocios") FiltroSocios filtroSocios,Model model,HttpSession session){

       // List<PersonaEntity> socios;
        List<PersonaEntityDto> socios;
        //CuentaEntity cuentaEmpresaAux =(CuentaEntity) session.getAttribute("cuenta");
        CuentaEntityDto cuentaEmpresaAux=(CuentaEntityDto) session.getAttribute("cuenta");
        //CuentaEntity cuentaEmpresa=cuentaRepository.findById(cuentaEmpresaAux.getId()).orElse(null);
        CuentaEntityDto cuentaEmpresa=cuentaPersonaService.encontrarCuentaPorId(cuentaEmpresaAux.getId());
        if(filtroSocios==null){
            filtroSocios = new FiltroSocios();
            filtroSocios.setIdpersona(cuentaEmpresa.getId());
        }
        switch(filtroSocios.getTipo()){
            case "Orden Dni Ascendente":

                socios = personaService.ordenarSociosDniAscendente(cuentaEmpresa);
                break;
            case "Orden Dni Descendente":
                socios = personaService.ordenarSociosDniDescendente(cuentaEmpresa);
                break;
            case "Bloqueados":
                socios=personaService.ordenarSociosBloqueados(cuentaEmpresa);
                break;
            case "Activos":
                socios=personaService.ordenarSociosActivados(cuentaEmpresa);
                break;
            case "Orden Apellidos Ascendente":
                socios=personaService.ordenarSociosApellidosAscendente(cuentaEmpresa);
                break;
            case "Orden Apellidos Descendente":
                socios=personaService.ordenarSociosApellidosDescendente(cuentaEmpresa);
                break;
            default:
                socios=personaService.ordenarSociosDniAscendente(cuentaEmpresa);

        }

        //PersonaEntity personaAux= (PersonaEntity) session.getAttribute("persona");
        PersonaEntityDto personaAux=(PersonaEntityDto) session.getAttribute("persona");
        //PersonaEntity persona = personaRepository.findById(personaAux.getId()).orElse(null);
        PersonaEntityDto persona = personaService.encontrarPersona(personaAux.getId());
        model.addAttribute("cuentaEmpresa", cuentaEmpresa);

        model.addAttribute("persona", persona);

        //List<OperacionEntity> operaciones = cuentaEmpresa.getOperacionsById();
        //List<OperacionEntityDto> operaciones = cuentaEmpresa.getOperacionsById(); DESCOMENTAR
        List<OperacionEntityDto> operaciones = this.personaService.operacionesDeUnaEmpresa(cuentaEmpresa.getId());
        model.addAttribute("operaciones", operaciones);


        model.addAttribute("socios", socios);

        List<Integer> sociosActivos = rolService.obtenerSociosActivos(cuentaEmpresa.getId());
        model.addAttribute("sociosActivos",sociosActivos);
        List<Integer> sociosBloqueados=rolService.obtenerSocioBloqueados(cuentaEmpresa.getId());
        model.addAttribute("sociosBloqueados",sociosBloqueados);



        model.addAttribute("tipo_operaciones", tipoOperacionService.findAll());

        //OperacionEntity operacion = new OperacionEntity();
        OperacionEntityDto operacion=new OperacionEntityDto();
        operacion.setCuentaByCuentaId(cuentaEmpresa);
        operacion.setPersonaByPersonaId(persona);
        model.addAttribute("operacion", operacion);

        model.addAttribute("filtroOperacionSocio", new FiltroOperacionSocio());

        //List<OperacionEntity> operacionesRecibidas = operacionRepository.buscarOperacionesRecibidas(cuentaEmpresa.getIban());
        List<OperacionEntityDto> operacionesRecibidas=operacionService.buscarOperacionesRecibidas(cuentaEmpresa.getIban());
        model.addAttribute("operacionesRecibidas",operacionesRecibidas);

        //filtros para las distintas listas
        FiltroOperacion filtroOperacion=new FiltroOperacion();
        model.addAttribute("filtroOperacion", filtroOperacion);
        model.addAttribute("tipos_filtro",filtroOperacion.getTipos_filtro());

        FiltroSocios filtroSocios1 = new FiltroSocios();
        model.addAttribute("filtroSocios",filtroSocios1);
        model.addAttribute("tipos_filtro_socio",filtroSocios1.getTipos_filtro_socio());

        return "cuentaEmpresa";
    }
}

