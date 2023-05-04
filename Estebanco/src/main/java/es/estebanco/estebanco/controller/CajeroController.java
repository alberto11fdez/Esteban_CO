package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.*;
import es.estebanco.estebanco.dto.*;
import es.estebanco.estebanco.entity.*;
import es.estebanco.estebanco.service.CajeroService;
import es.estebanco.estebanco.ui.FiltroCajero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cajero")
public class CajeroController {

    @Autowired
    protected CajeroService cajeroService;

    @GetMapping("/")
    public String doListar (Model model, HttpSession session){
        String urlTo = "cajero";
        PersonaEntityDto persona = (PersonaEntityDto) session.getAttribute("persona");
        if(persona==null){
            urlTo = "redirect:/cajeroLogin/";
        } else {
            model.addAttribute("persona", persona);
            List<CuentaEntityDto> cuentas = this.cajeroService.listarCuentas(persona.getId());
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
        CuentaEntityDto cuenta = this.cajeroService.cuentaPorId(idCuenta);
        model.addAttribute("cuenta", cuenta);

        return "operacionesCajero";
    }

    @GetMapping("/sacarDinero")
    public String doSacarDinero(@RequestParam("id") Integer idCuenta,
                                Model model){
        CuentaEntityDto cuenta = this.cajeroService.cuentaPorId(idCuenta);
        model.addAttribute("cuenta", cuenta);

        return "sacarDinero";
    }

    protected String mostrarEditadoSacar (CuentaEntityDto cuenta, Model model){
        model.addAttribute("cuenta", cuenta);
        return "sacarDinero";
    }

    protected void nuevaOperacionSacar(CuentaEntityDto cuenta, Integer valor, HttpSession session){
        PersonaEntityDto persona = (PersonaEntityDto) session.getAttribute("persona");
        this.cajeroService.nuevaOperacionSacar(cuenta,valor,persona);
    }

    @PostMapping("/sacandoDinero")
    public String doSacadoDinero(@RequestParam("valor") Integer valor,
                                 @RequestParam("id") Integer idCuenta,
                                 HttpSession session,
                                 Model model){
        String urlTo = "redirect:/cajero/";
        CuentaEntityDto cuenta = this.cajeroService.cuentaOrigenPorId(idCuenta);
        if(valor==null||valor>cuenta.getSaldo()||valor<0){
            model.addAttribute("error", "Cantidad incorrecta introduce un nuevo valor");
            return this.mostrarEditadoSacar(cuenta,model);
        } else {
            this.cajeroService.guardarSacar(cuenta,valor);
            this.nuevaOperacionSacar(cuenta,valor,session);
        }
        return urlTo;
    }


    @GetMapping("/ingresarDinero")
    public String doIngresarDinero(@RequestParam("id") Integer idCuenta,
                                   Model model){
        CuentaEntityDto cuenta = this.cajeroService.cuentaPorId(idCuenta);
        model.addAttribute("cuenta", cuenta);

        return "ingresarDinero";
    }

    protected String mostrarEditadoIngresar (CuentaEntityDto cuenta, Model model){
        model.addAttribute("cuenta", cuenta);
        return "ingresarDinero";
    }

    protected void nuevaOperacionMeter(CuentaEntityDto cuenta, Integer valor, HttpSession session){
        PersonaEntityDto persona = (PersonaEntityDto) session.getAttribute("persona");
        this.cajeroService.nuevaOperacionMeter(cuenta,valor,persona);
    }

    @PostMapping("/ingresandoDinero")
    public String doIngresadoDinero(@RequestParam("valor") Integer valor,
                                    @RequestParam("id") Integer idCuenta,
                                    HttpSession session,
                                    Model model){
        String urlTo = "redirect:/cajero/";
        CuentaEntityDto cuenta = this.cajeroService.cuentaOrigen(idCuenta);
        if(valor<0){
            model.addAttribute("error", "Cantidad incorrecta introduce un nuevo valor");
            return this.mostrarEditadoIngresar(cuenta,model);
        } else if (cuenta.getEstado().equals("bloqueado")) {
            model.addAttribute("error", "No puede operar con esta cuenta porque esta bloqueada");
            return this.mostrarEditadoIngresar(cuenta,model);
        }else {
            this.cajeroService.guardarMeter(cuenta,valor);
            this.nuevaOperacionMeter(cuenta,valor,session);
        }
        return urlTo;
    }

    @GetMapping("/transferirDinero")
    public String doTransferirDinero(@RequestParam ("id") Integer idCuenta, Model model){
        CuentaEntityDto cuenta = this.cajeroService.cuentaPorId(idCuenta);
        model.addAttribute("cuenta", cuenta);

        return "transferencia";
    }

    protected String mostrarEditadoTransferencia(CuentaEntityDto cuenta, Model model){
        model.addAttribute("cuenta", cuenta);
        return "transferencia";
    }

    protected void nuevaOperacionMeterTransferencia(CuentaEntityDto cuentaOrigen, CuentaEntityDto cuentaDestino, Integer valor, HttpSession session){
        PersonaEntityDto persona = (PersonaEntityDto) session.getAttribute("persona");
        this.cajeroService.nuevaOperacionTransferenciaMeter(cuentaDestino,cuentaOrigen,valor,persona);
    }

    protected void nuevaOperacionSacarTransferencia(CuentaEntityDto cuentaOrigen, CuentaEntityDto cuentaDestino, Integer valor, HttpSession session){
        PersonaEntityDto persona = (PersonaEntityDto) session.getAttribute("persona");
        this.cajeroService.nuevaOperacionTransferenciaSacar(cuentaDestino,cuentaOrigen,valor,persona);
    }

    @PostMapping("/transfiriendoDinero")
    public String doTransferidoDinero(@RequestParam ("valor") Integer valor,
                                      @RequestParam ("id") Integer idCuenta,
                                      @RequestParam ("destino") String destino,
                                      HttpSession session,
                                      Model model){
        String urlTo = "redirect:/cajero/";
        CuentaEntityDto cuentaOrigen = this.cajeroService.cuentaOrigen(idCuenta);
        CuentaEntityDto cuentaDestino = this.cajeroService.cuentaDestino(destino);

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
                this.cajeroService.guardarSacar(cuentaOrigen,valor);
                this.cajeroService.guardarMeter(cuentaDestino,valor);
                this.nuevaOperacionSacarTransferencia(cuentaOrigen,cuentaDestino,valor,session);
                this.nuevaOperacionMeterTransferencia(cuentaOrigen,cuentaDestino,valor,session);
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
        List<OperacionEntityDto> lista;
        String urlTo = "listaOperaciones";
        PersonaEntityDto persona = (PersonaEntityDto) session.getAttribute("persona");
        if(persona == null){
            urlTo = "loginCajero";
        } else {
            if(filtro==null || (filtro.getTipoOperacion().isEmpty() && filtro.getTexto().isEmpty())){
                lista = this.cajeroService.listarOperaciones(persona);
                filtro = new FiltroCajero();
            } else if (filtro.getTexto().isEmpty()){
                lista = this.cajeroService.listarOperacionesPorTipo(persona,filtro.getTipoOperacion());

            } else if (filtro.getTipoOperacion().isEmpty()) {
                lista = this.cajeroService.listarOperacionesPorTexto(persona, filtro.getTexto());
            } else {
                lista = this.cajeroService.listarOperacionesPorTextoYTipo(persona,filtro.getTipoOperacion(),filtro.getTexto());
            }
            model.addAttribute("operaciones", lista);
            model.addAttribute("filtro", filtro);
            List<TipoOperacionEntityDto> tipos = this.cajeroService.listarTiposOperaciones();
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
        PersonaEntityDto persona = this.cajeroService.personaPorId(idpersona);
        model.addAttribute("persona", persona);
        return "datosCajero";
    }

    @PostMapping("/guardar")
    public String doGuardar (@ModelAttribute("persona") PersonaEntityDto persona) {
        this.cajeroService.guardarPersona(persona);
        return "redirect:/cajero";
    }

    @GetMapping("/cambiarDivisa")
    public String doEditarDivisa(@RequestParam("id") Integer idcuenta, Model model){
        CuentaEntityDto cuenta = this.cajeroService.cuentaPorId(idcuenta);
        List<TipoMonedaEntityDto> monedas = this.cajeroService.listarTiposMonedas();
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("monedas", monedas);
        return "datosDivisa";
    }

    protected void nuevaOperacionCambioDivisa(CuentaEntityDto cuenta, HttpSession session){
        PersonaEntityDto persona = (PersonaEntityDto) session.getAttribute("persona");
        this.cajeroService.nuevaOperacionCambiarDivisa(cuenta,persona);
    }

    @PostMapping("/guardarDivisa")
    public String doGuardarDivisa(@ModelAttribute("cuenta") CuentaEntityDto cuenta,
                                  @RequestParam("moneda") String moneda,
                                  HttpSession session){
        TipoMonedaEntityDto moneda1 = this.cajeroService.tipoDeMoneda(moneda);
        this.cajeroService.guardarCuentaCambioDivisa(cuenta, moneda1.getMoneda());
        this.nuevaOperacionCambioDivisa(cuenta,session);
        return "redirect:/cajero/";
    }

    @GetMapping("/solicitarDesbloqueo")
    public String doSolicitarDesbloqueoCajero(@RequestParam("id") Integer idcuenta, Model model){
        CuentaEntityDto cuenta = this.cajeroService.cuentaPorId(idcuenta);
        TipoEstadoEntityDto estado = this.cajeroService.buscarTipoEstadoPorId();
        this.cajeroService.guardarSolicitarDesbloqueo(cuenta, estado);
        return "solicitarDesbloqueo";
    }

}
