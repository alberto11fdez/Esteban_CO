package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.*;
import es.estebanco.estebanco.entity.*;
import es.estebanco.estebanco.ui.FiltroCajero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cajero")
public class CajeroController {
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

    @GetMapping("/")
    public String doListar (Model model, HttpSession session){
        String urlTo = "cajero";
        PersonaEntity persona = (PersonaEntity) session.getAttribute("persona");
        if(persona==null){
            urlTo = "redirect:/cajeroLogin/";
        } else {
            model.addAttribute("persona", persona);
            List<CuentaEntity> cuentas = this.personaRepository.cuentasPorPersona(persona);
            model.addAttribute("cuentas",cuentas);
        }
        return urlTo;
    }

    @GetMapping("/mostrarLogin")
    public String doMostrarLogin(Model model){
        return "redirect:/cajeroLogin/";
    }

    @GetMapping("/mostrarOperaciones")
    public String doMostrarOperaciones(@RequestParam("id") Integer idCuenta,
                                       Model model){
        CuentaEntity cuenta = this.cuentaRepository.findById(idCuenta).orElse(null);
        model.addAttribute("cuenta", cuenta);

        return "operacionesCajero";
    }

    @GetMapping("/sacarDinero")
    public String doSacarDinero(@RequestParam("id") Integer idCuenta,
                                Model model){
        CuentaEntity cuenta = this.cuentaRepository.findById(idCuenta).orElse(null);
        model.addAttribute("cuenta", cuenta);

        return "sacarDinero";
    }

    protected String mostrarEditadoSacar (CuentaEntity cuenta, Model model){
        model.addAttribute("cuenta", cuenta);
        return "sacarDinero";
    }

    protected void nuevaOperacionSacar(CuentaEntity cuenta, Integer valor){
        Date now = new Date();
        OperacionEntity operacion = new OperacionEntity();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(1);
        operacion.setCantidad(valor);
        operacion.setCuentaByCuentaId(cuenta);
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        this.operacionRepository.save(operacion);
    }

    @PostMapping("/sacandoDinero")
    public String doSacadoDinero(@RequestParam("valor") Integer valor,
                                 @RequestParam("id") Integer idCuenta,
                                 Model model){
        String urlTo = "redirect:/cajero/";
        TipoEstadoEntity estado = this.tipoEstadoEntityRepository.findById(2).orElse(null);
        CuentaEntity cuenta = this.cuentaRepository.cuentaOrigen(idCuenta);
        if(valor==null||valor>cuenta.getSaldo()||valor<0){
            model.addAttribute("error", "Cantidad incorrecta introduce un nuevo valor");
            return this.mostrarEditadoSacar(cuenta,model);
        } else {
            cuenta.setSaldo(cuenta.getSaldo()-valor);
            this.cuentaRepository.save(cuenta);
            this.nuevaOperacionSacar(cuenta,valor);
        }
        return urlTo;
    }


    @GetMapping("/ingresarDinero")
    public String doIngresarDinero(@RequestParam("id") Integer idCuenta,
                                   Model model){
        CuentaEntity cuenta = this.cuentaRepository.findById(idCuenta).orElse(null);
        model.addAttribute("cuenta", cuenta);

        return "ingresarDinero";
    }

    protected String mostrarEditadoIngresar (CuentaEntity cuenta, Model model){
        model.addAttribute("cuenta", cuenta);
        return "ingresarDinero";
    }

    protected void nuevaOperacionMeter(CuentaEntity cuenta, Integer valor){
        Date now = new Date();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(2);
        OperacionEntity operacion = new OperacionEntity();
        operacion.setCantidad(valor);
        operacion.setCuentaByCuentaId(cuenta);
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);

        this.operacionRepository.save(operacion);
    }

    @PostMapping("/ingresandoDinero")
    public String doIngresadoDinero(@RequestParam("valor") Integer valor,
                                    @RequestParam("id") Integer idCuenta,
                                    Model model){
        String urlTo = "redirect:/cajero/";
        CuentaEntity cuenta = this.cuentaRepository.cuentaOrigen(idCuenta);
        if(valor<0){
            model.addAttribute("error", "Cantidad incorrecta introduce un nuevo valor");
            return this.mostrarEditadoIngresar(cuenta,model);
        } else if (cuenta.getEstado().equals("bloqueado")) {
            model.addAttribute("error", "No puede operar con esta cuenta porque esta bloqueada");
            return this.mostrarEditadoIngresar(cuenta,model);
        }else {
            cuenta.setSaldo(cuenta.getSaldo()+valor);
            this.cuentaRepository.save(cuenta);
            this.nuevaOperacionMeter(cuenta,valor);
        }
        return urlTo;
    }

    @GetMapping("/transferirDinero")
    public String doTransferirDinero(@RequestParam ("id") Integer idCuenta, Model model){
        CuentaEntity cuenta = this.cuentaRepository.findById(idCuenta).orElse(null);
        model.addAttribute("cuenta", cuenta);

        return "transferencia";
    }

    protected String mostrarEditadoTransferencia(CuentaEntity cuenta, Model model){
        model.addAttribute("cuenta", cuenta);
        return "transferencia";
    }

    protected void nuevaOperacionMeterTransferencia(CuentaEntity cuentaOrigen, CuentaEntity cuentaDestino, Integer valor){
        Date now = new Date();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(2);
        OperacionEntity operacion = new OperacionEntity();
        operacion.setCantidad(valor);
        operacion.setCuentaByCuentaId(cuentaDestino);
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        operacion.setIbanCuentaDestinoOrigen(cuentaOrigen.getIban());
        this.operacionRepository.save(operacion);
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
        this.operacionRepository.save(operacion);
    }

    @PostMapping("/transfiriendoDinero")
    public String doTransferidoDinero(@RequestParam ("valor") Integer valor,
                                      @RequestParam ("id") Integer idCuenta,
                                      @RequestParam ("destino") String destino,
                                      Model model){
        String urlTo = "redirect:/cajero/";
        CuentaEntity cuentaOrigen = this.cuentaRepository.cuentaOrigen(idCuenta);
        CuentaEntity cuentaDestino = this.cuentaRepository.cuentaDestinoTransferencia(destino);

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
                this.nuevaOperacionMeterTransferencia(cuentaOrigen,cuentaDestino,valor);
            }
        }
        return urlTo;
    }

    @PostMapping("/filtrar")
    public String doFiltrar(@ModelAttribute("filtro") FiltroCajero filtro,
                            Model model, HttpSession session){
        return this.procesarFiltrado(filtro,model,session);
    }

    protected String procesarFiltrado(FiltroCajero filtro, Model model,HttpSession session){
        List<OperacionEntity> lista;
        String urlTo = "listaOperaciones";
        PersonaEntity persona = (PersonaEntity) session.getAttribute("persona");
        if(persona == null){
            urlTo = "loginCajero";
        } else {
            if(filtro==null || (filtro.getTipoOperacion().isEmpty() && filtro.getTexto().isEmpty())){
                lista = this.operacionRepository.operacionesPorPersona(persona);
                filtro = new FiltroCajero();
            } else if (filtro.getTexto().isEmpty()){
                lista = this.operacionRepository.buscarPorTipoOperacion(persona, filtro.getTipoOperacion());
            } else if (filtro.getTipoOperacion().isEmpty()) {
                lista = this.operacionRepository.buscarPorIban(persona, filtro.getTexto());
            } else {
                lista = this.operacionRepository.buscarPorTipoOperacionEIban(persona, filtro.getTexto(),filtro.getTipoOperacion());
            }
            model.addAttribute("operaciones", lista);
            model.addAttribute("filtro", filtro);
            List<TipoOperacionEntity> tipos = this.tipoOperacionEntityRepository.findAll();
            model.addAttribute("tipos", tipos);
        }
        return urlTo;
    }

    @GetMapping("/listadoOperaciones")
    public String doMostrarListadoOperaciones(Model model, HttpSession session){
        return this.procesarFiltrado(null,model,session);
    }


    @GetMapping("/editar")
    public String doEditarPersona(@RequestParam("id") Integer idpersona, Model model){
        PersonaEntity persona = this.personaRepository.findById(idpersona).orElse(null);
        model.addAttribute("persona", persona);
        return "datosCajero";
    }

    @PostMapping("/guardar")
    public String doGuardar (@ModelAttribute("persona") PersonaEntity persona) {
        this.personaRepository.save(persona);
        return "redirect:/cajero/?id="+persona.getId();
    }

    @GetMapping("/cambiarDivisa")
    public String doEditarDivisa(@RequestParam("id") Integer idcuenta, Model model){
        CuentaEntity cuenta = this.cuentaRepository.findById(idcuenta).orElse(null);
        List<TipoMonedaEntity> monedas = this.tipoMonedaEntityRepository.findAll();
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("monedas", monedas);
        return "datosDivisa";
    }

    protected void nuevaOperacionCambioDivisa(CuentaEntity cuenta){
        Date now = new Date();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(3);
        OperacionEntity operacion = new OperacionEntity();
        operacion.setCuentaByCuentaId(cuenta);
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        this.operacionRepository.save(operacion);
    }

    @PostMapping("/guardarDivisa")
    public String doGuardarDivisa(@ModelAttribute("cuenta") CuentaEntity cuenta,
                                  @RequestParam("moneda") String moneda){
        TipoMonedaEntity moneda1 = this.tipoMonedaEntityRepository.buscarMoneda(moneda);
        cuenta.setMoneda(moneda1.getMoneda());
        this.cuentaRepository.save(cuenta);
        this.nuevaOperacionCambioDivisa(cuenta);
        return "redirect:/cajero/";
    }

    @GetMapping("/solicitarDesbloqueo")
    public String doSolicitarDesbloqueoCajero(@RequestParam("id") Integer idcuenta, Model model){
        CuentaEntity cuenta = this.cuentaRepository.findById(idcuenta).orElse(null);
        TipoEstadoEntity estado = this.tipoEstadoEntityRepository.buscarTipo(1);
        cuenta.setEstado(estado.getNombre());
        this.cuentaRepository.save(cuenta);
        return "solicitarDesbloqueo";
    }

}
