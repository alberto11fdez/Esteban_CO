package es.estebanco.estebanco.controller;
import es.estebanco.estebanco.dao.*;
import es.estebanco.estebanco.entity.*;
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
    @GetMapping("/")
    public String mostrarDatosCuenta(@RequestParam("idCuenta") Integer idCuenta, Model model, HttpSession session){
        CuentaEntity cuenta = cuentaRepository.findById(idCuenta).orElse(null);

        return this.procesarFiltro(null,cuenta,model,session);
    }
    @PostMapping("/filtrar")
    public String doFiltrar(@ModelAttribute("filtro") FiltroOperacion filtro, Model model, HttpSession session){
        return this.procesarFiltro(filtro, cuentaRepository.findById(filtro.getIdpersona()).orElse(null), model, session);
    }
    protected String procesarFiltro(FiltroOperacion filtro, CuentaEntity cuenta, Model model, HttpSession session){
        if(cuenta==null){
            return "redirect:/";
        }else{
            model.addAttribute("cuenta",cuenta);


            List<OperacionEntity> operaciones;
            if(filtro==null){
                filtro = new FiltroOperacion();
                filtro.setIdpersona(cuenta.getId());
            }
            switch(filtro.getTipo()){
                case "sacar":
                    operaciones = this.operacionRepository.operacionesPorCuentaYTipo(cuenta,filtro.getTipo());
                    break;
                case "meter":
                    operaciones = this.operacionRepository.operacionesPorCuentaYTipo(cuenta,filtro.getTipo());
                    break;
                case "cambio divisa":
                    operaciones = this.operacionRepository.operacionesPorCuentaYTipo(cuenta,filtro.getTipo());
                    break;
                case "euro":
                    operaciones = this.operacionRepository.operacionesPorCuentaYMoneda(cuenta,filtro.getTipo());
                    break;
                case "libra":
                    operaciones = this.operacionRepository.operacionesPorCuentaYMoneda(cuenta,filtro.getTipo());
                    break;
                case "ordenar por fecha":
                    operaciones = this.operacionRepository.operacionesPorCuentaOrdenadoPorFecha(cuenta);
                    break;
                case "ordenar por cantidad":
                    operaciones = this.operacionRepository.operacionesPorCuentaOrdenadoPorCantidad(cuenta);
                    break;
                default:
                    operaciones = this.operacionRepository.operacionesPorCuenta(cuenta);

            }

            model.addAttribute("operaciones",operaciones);
            model.addAttribute("filtro",filtro);
            model.addAttribute("tipos_filtro",filtro.getTipos_filtro());
            model.addAttribute("persona", personaRepository.propietarioDeCuenta(cuenta));


        }


        return "cuentaPersona";
    }
    @GetMapping("/mostrarTransferencia")
    public String mostrarTransferencia(@RequestParam("idCuenta") Integer idCuenta, Model model){
        CuentaEntity cuenta = cuentaRepository.findById(idCuenta).orElse(null);
        model.addAttribute("cuenta",cuenta);
        return "transferenciaPersona";
    }
    protected String mostrarEditadoTransferencia(CuentaEntity cuenta, Model model) {
        model.addAttribute("cuenta", cuenta);
        return "transferenciaPersona";
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
        operacion.setPersonaByPersonaId(personaRepository.propietarioDeCuenta(cuentaOrigen));
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
        operacion.setPersonaByPersonaId(personaRepository.propietarioDeCuenta(cuentaOrigen));
        this.operacionRepository.save(operacion);
    }
    @PostMapping("/transfiriendoDinero")
    public String doTransferidoDinero(@RequestParam ("valor") Integer valor,
                                      @RequestParam ("id") Integer idCuenta,
                                      @RequestParam ("destino") String destino,
                                      Model model){
        CuentaEntity cuentaOrigen = this.cuentaRepository.cuentaOrigen(idCuenta);
        CuentaEntity cuentaDestino = this.cuentaRepository.cuentaDestinoTransferencia(destino);

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
                this.cuentaRepository.save(cuentaOrigen);
                this.cuentaRepository.save(cuentaDestino);
                this.nuevaOperacionSacarTransferencia(cuentaOrigen,cuentaDestino,valor);
                this.nuevaOperacionMeterTransferencia(cuentaOrigen,cuentaDestino,valor);
            }
        }
        return urlTo;
    }
    @GetMapping("/mostrarDivisa")
    public String mostrarDivisa(@RequestParam("idCuenta") Integer idCuenta, Model model){
        CuentaEntity cuenta = this.cuentaRepository.findById(idCuenta).orElse(null);
        List<TipoMonedaEntity> monedas = this.tipoMonedaEntityRepository.findAll();
        model.addAttribute("cuenta", cuenta);
        model.addAttribute("monedas", monedas);
        return "cambioDivisaPersona";
    }
    protected void nuevaOperacionCambioDivisa(CuentaEntity cuenta){
        Date now = new Date();
        TipoOperacionEntity tipo = this.tipoOperacionEntityRepository.buscarTipo(3);
        OperacionEntity operacion = new OperacionEntity();
        operacion.setCuentaByCuentaId(cuenta);
        operacion.setTipo(tipo.getNombre());
        operacion.setFechaOperacion(now);
        operacion.setPersonaByPersonaId(personaRepository.propietarioDeCuenta(cuenta));
        this.operacionRepository.save(operacion);
    }
    @PostMapping("/guardarDivisa")
    public String doGuardarDivisa(@ModelAttribute("cuenta") CuentaEntity cuentaCambio,
                                  @RequestParam("moneda") String moneda){
        TipoMonedaEntity moneda1 = this.tipoMonedaEntityRepository.buscarMoneda(moneda);
        CuentaEntity cuenta = cuentaRepository.findById(cuentaCambio.getId()).orElse(null);
        cuenta.setMoneda(moneda1.getMoneda());
        this.cuentaRepository.save(cuenta);
        this.nuevaOperacionCambioDivisa(cuenta);
        return "redirect:/cuentaPersona/?idCuenta="+cuenta.getId();
    }
}
