package es.estebanco.estebanco.controller;
import es.estebanco.estebanco.dao.*;
import es.estebanco.estebanco.dto.CuentaEntityDto;
import es.estebanco.estebanco.dto.OperacionEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.dto.TipoMonedaEntityDto;
import es.estebanco.estebanco.entity.*;
import es.estebanco.estebanco.service.CuentaPersonaService;
import es.estebanco.estebanco.ui.FiltroOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/cuentaPersona")
public class CuentaPersonaController {
    @Autowired
    protected CuentaRepository cuentaRepository;
    @Autowired
    protected OperacionRepository operacionRepository;

    @Autowired
    private TipoOperacionEntityRepository tipoOperacionEntityRepository;
    @Autowired
    private TipoMonedaEntityRepository tipoMonedaEntityRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private CuentaPersonaService cuentaPersonaService;
    @GetMapping("/")
    public String mostrarDatosCuenta(@RequestParam("idCuenta") Integer idCuenta, Model model, HttpSession session){
        CuentaEntityDto cuenta = cuentaPersonaService.encontrarCuentaPorId(idCuenta);

        return this.procesarFiltro(null,cuenta,model,session);
    }
    @PostMapping("/filtrar")
    public String doFiltrar(@ModelAttribute("filtro") FiltroOperacion filtro, Model model, HttpSession session){
        return this.procesarFiltro(filtro, cuentaPersonaService.encontrarCuentaPorId(filtro.getIdpersona()), model, session);
    }
    protected String procesarFiltro(FiltroOperacion filtro, CuentaEntityDto cuenta, Model model, HttpSession session){
        if(cuenta==null){
            return "redirect:/";
        }else{
            model.addAttribute("cuenta",cuenta);


            List<OperacionEntityDto> operaciones;
            if(filtro==null){
                filtro = new FiltroOperacion();
                filtro.setIdpersona(cuenta.getId());
            }
            operaciones = cuentaPersonaService.listarOperaciones(cuenta,filtro);


            model.addAttribute("operaciones",operaciones);
            model.addAttribute("filtro",filtro);
            model.addAttribute("tipos_filtro",filtro.getTipos_filtro());
            model.addAttribute("persona", cuentaPersonaService.propietarioDeCuenta(cuenta));


        }


        return "cuentaPersona";
    }
    @GetMapping("/mostrarTransferencia")
    public String mostrarTransferencia(@RequestParam("idCuenta") Integer idCuenta, Model model){
        CuentaEntityDto cuenta = cuentaPersonaService.encontrarCuentaPorId(idCuenta);
        model.addAttribute("cuenta",cuenta);
        return "transferenciaPersona";
    }
    protected String mostrarEditadoTransferencia(CuentaEntityDto cuenta, Model model) {
        model.addAttribute("cuenta", cuenta);
        return "transferenciaPersona";
    }
    protected void nuevaOperacionMeterTransferencia(CuentaEntityDto cuentaOrigen, CuentaEntityDto cuentaDestino, Integer valor){
        this.cuentaPersonaService.nuevaOperacionMeterTransferencia(cuentaOrigen,cuentaDestino,valor);

    }

    protected void nuevaOperacionSacarTransferencia(CuentaEntityDto cuentaOrigen, CuentaEntityDto cuentaDestino, Integer valor){
        this.cuentaPersonaService.nuevaOperacionSacarTransferencia(cuentaOrigen,cuentaDestino,valor);

    }
    @PostMapping("/transfiriendoDinero")
    public String doTransferidoDinero(@RequestParam ("valor") Integer valor,
                                      @RequestParam ("id") Integer idCuenta,
                                      @RequestParam ("destino") String destino,
                                      Model model){
        CuentaEntityDto cuentaOrigen = this.cuentaPersonaService.cuentaOrigen(idCuenta);
        CuentaEntityDto cuentaDestino = this.cuentaPersonaService.cuentaDestinoTransferencia(destino);

        String urlTo = "redirect:/cuentaPersona/?idCuenta="+cuentaOrigen.getId();

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
                this.cuentaPersonaService.guardarSacar(cuentaOrigen,valor);
                this.cuentaPersonaService.guardarMeter(cuentaDestino,valor);
                this.nuevaOperacionSacarTransferencia(cuentaOrigen,cuentaDestino,valor);
                this.nuevaOperacionMeterTransferencia(cuentaOrigen,cuentaDestino,valor);
            }
        }
        return urlTo;
    }
    @GetMapping("/mostrarDivisa")
    public String mostrarDivisa(@RequestParam("idCuenta") Integer idCuenta, Model model){
        CuentaEntityDto cuenta = this.cuentaPersonaService.encontrarCuentaPorId(idCuenta);
        List<TipoMonedaEntityDto> monedas = this.cuentaPersonaService.listarTiposMonedas();
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("monedas", monedas);
        return "cambioDivisaPersona";
    }
    protected void nuevaOperacionCambioDivisa(CuentaEntityDto cuenta){
        this.cuentaPersonaService.nuevaOperacionCambioDivisa(cuenta);

    }
    @PostMapping("/guardarDivisa")
    public String doGuardarDivisa(@ModelAttribute("cuenta") CuentaEntityDto cuenta,
                                  @RequestParam("moneda") String moneda){
        TipoMonedaEntityDto moneda1 = this.cuentaPersonaService.buscarMoneda(moneda);
        //CuentaEntity cuenta = cuentaRepository.findById(cuentaCambio.getId()).orElse(null);
        //cuenta.setMoneda(moneda1.getMoneda());
        this.cuentaPersonaService.guardarCuentaCambioDivisa(cuenta,moneda1.getMoneda());
        this.nuevaOperacionCambioDivisa(cuenta);
        return "redirect:/cuentaPersona/?idCuenta="+cuenta.getId();
    }
}
