package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.CuentaRepository;
import es.estebanco.estebanco.dao.OperacionRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dao.TipoMonedaEntityRepository;
import es.estebanco.estebanco.dto.CuentaEntityDto;
import es.estebanco.estebanco.dto.OperacionEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.dto.TipoMonedaEntityDto;
import es.estebanco.estebanco.entity.*;
import es.estebanco.estebanco.service.*;
import es.estebanco.estebanco.ui.FiltroGestor;
import es.estebanco.estebanco.ui.FiltroOperaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import es.estebanco.estebanco.entity.PersonaEntity;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/gestor")
public class GestorController {

    @Autowired
    protected OperacionRepository operacionRepository;

    @Autowired
    protected CuentaRepository cuentaRepository;
    @Autowired
    protected PersonaRepository personaRepository;

    @Autowired
    protected TipoMonedaEntityRepository tipoMonedaRepository;

    @Autowired
    private PersonaService personaService;
    @Autowired
    private GestorService gestorService;
    @Autowired
    private TipoMonedaService tipoMonedaService;
    @Autowired
    private CuentaPersonaService cuentaPersonaService;
    @Autowired
    private OperacionService operacionService;

    @GetMapping("/")
    public String doListar(Model model, HttpSession session){
        return this.procesarFiltrado(null,model,session);
    }

    @PostMapping("/filtrar")
    public String doFiltrar(@ModelAttribute("filtro") FiltroGestor filtro, Model model, HttpSession session){
        return this.procesarFiltrado(filtro,model,session);
    }
    protected String procesarFiltrado(FiltroGestor filtro, Model model, HttpSession session){

        String urlTo = "gestor";


        PersonaEntityDto gestor = (PersonaEntityDto) session.getAttribute("gestor");

        if(gestor == null) {
            urlTo = "redirect:/";
        } else {
            List<String> divisa = new ArrayList<>();
            List <CuentaEntityDto> cuentas = this.gestorService.listarCuentas("", divisa);
           // List <PersonaEntity> personas = this.personaRepository.findAll();

            if (filtro == null || filtro.getTexto().isEmpty() && filtro.getMonedas().isEmpty()){
                filtro = new FiltroGestor();
            } else if (filtro.getMonedas().isEmpty()) {
                //cuentas = this.cuentaRepository.cuentaPorIban(filtro.getTexto());
                cuentas = this.gestorService.listarCuentas(filtro.getTexto(), filtro.getMonedas());
            } else if(filtro.getTexto().isEmpty()){
                //cuentas = this.cuentaRepository.cuentaPorDivisa(filtro.getMonedas());
                cuentas = this.gestorService.listarCuentas("", filtro.getMonedas());
            } else {
                cuentas = this.gestorService.listarCuentas(filtro.getTexto(),filtro.getMonedas());
            }
            model.addAttribute("filtro",filtro);
            model.addAttribute("cuentas", cuentas);
            List <TipoMonedaEntityDto> monedas = this.tipoMonedaService.findAll();
            model.addAttribute("monedas", monedas);
            //model.addAttribute("personas", personas);



        }


        return urlTo;
    }
    @GetMapping("/gestorPersonas")
    public String doListarPersonas(Model model, HttpSession session){
        return this.procesarFiltradoPersonas(null,model,session);
    }

    protected String procesarFiltradoPersonas(FiltroGestor filtro, Model model, HttpSession session){

        String urlTo = "gestorPersonas";
        PersonaEntityDto gestor =(PersonaEntityDto) session.getAttribute("gestor");

        if (gestor == null){
            return "redirect:/";
        } else {
            List <PersonaEntityDto> personas = this.personaService.findAll();
            model.addAttribute("personas", personas);
        }
        return urlTo;
    }
    @GetMapping("/solicitudes")
    public String doListarSolicitudes(Model model, HttpSession session){
        String urlTo ="gestorSolicitudes";
        PersonaEntityDto gestor = (PersonaEntityDto) session.getAttribute("gestor");

        if(gestor == null){
            return "redirect:/";
        } else {
            List <PersonaEntityDto> personaSolicitante = this.personaService.obtenerPersonasPorEstado("esperandoConfirmacion");
            model.addAttribute("personaSolicitante", personaSolicitante);
        }

        return urlTo;
    }
    @GetMapping("/revisar")
    public String doRevisarEstado(@RequestParam("id") Integer id, Model model, HttpSession session){
        String urlTo ="gestorRevisarEstado";
        PersonaEntityDto gestor = (PersonaEntityDto) session.getAttribute("gestor");
        PersonaEntityDto persona;

        if(gestor == null){
            return "redirect:/";
        } else {
            persona = this.personaService.buscarPersonaPorId(id);
        }
        model.addAttribute("personaRevisar", persona);

        return urlTo;
    }
   /* @PostMapping("/revisar")
    public String doRevisarEstado(@ModelAttribute("personaRevisar") PersonaEntity persona,@ModelAttribute("filtro")FiltroGestor filtro, Model model, HttpSession session){
        String urlTo ="gestor";
        String [] checkbox = filtro.getCheckboxes();

        if(checkbox.length == 1 && checkbox[0].equals("true")){
            persona.setEstado("bien");
        } if (checkbox.length == 1 && checkbox[0].equals("false")){
            persona.setEstado("bloqueado");
        } else {
            persona.setEstado("espera_confirmacion");
        }
        this.personaRepository.save(persona);
        return urlTo;
    }*/
    @PostMapping("/revisar")
    public String doRevisar(@ModelAttribute("personaRevisar")PersonaEntityDto persona){
        this.personaService.save(persona);
        return "redirect:/gestor/solicitudes";
    }
    @GetMapping("/revisarCuenta")
    public String doRevisarCuenta(@RequestParam("idCuenta") Integer idCuenta, Model model, HttpSession session){
        String urlTo ="gestorRevisarCuenta";
        PersonaEntityDto gestor = (PersonaEntityDto) session.getAttribute("gestor");
        if(gestor == null){
            return "redirect:/";
        }
        CuentaEntityDto cuenta = this.cuentaPersonaService.encontrarCuentaPorId(idCuenta);
        model.addAttribute("cuentaRevisar", cuenta);
        return urlTo;
    }

    @PostMapping("/revisarCuenta")
    public String doRevisarCuentaPost(@ModelAttribute("cuentaRevisar") CuentaEntityDto cuenta, Model model, HttpSession session){
        String urlTo ="redirect:/gestor/";
        Random iban = new Random();
        String card = "ES";
        for(int i = 0; i < 14; i++){
            int n = iban.nextInt(10) + 0;
            card += Integer.toString(n);
        }
        if(cuenta.getEstado().equalsIgnoreCase("bien")){
            cuenta.setEstado("bien");
            cuenta.setIban(card);
            cuenta.setFechaApertura(new Timestamp(System.currentTimeMillis()));
            this.cuentaPersonaService.saveCuenta(cuenta);
        } else {
            cuenta.setFechaApertura(new Timestamp(System.currentTimeMillis()));
            cuenta.setEstado("bloqueado");
            this.cuentaPersonaService.saveCuenta(cuenta);
        }

        return urlTo;
    }
    @GetMapping("/historico")
    public String doHistoricoOperaciones(@RequestParam("idCuenta") Integer idCuenta, Model model, HttpSession session){
        String urlTo ="gestorVistaOperaciones";

        CuentaEntityDto cuenta = this.cuentaPersonaService.encontrarCuentaPorId(idCuenta);
        List <OperacionEntityDto> operaciones = this.operacionService.operacionesPorCuenta(idCuenta);
        model.addAttribute("operaciones", operaciones);
        FiltroOperaciones filtroOperaciones = new FiltroOperaciones();
        model.addAttribute("filtroOperaciones", filtroOperaciones);

        return urlTo;
    }
    @PostMapping("/filtrarOperaciones")
    public String doFiltrarOperacioines(@ModelAttribute("filtroOperaciones") FiltroOperaciones filtroOperaciones, @ModelAttribute("operaciones") List<OperacionEntityDto> operaciones, Model model, HttpSession session){
        String urlTo ="gestorVistaOperaciones";




        return urlTo;
    }
    @GetMapping("/cuentasSospechosas")
    public String doListarCuentasSospechosas(Model model, HttpSession session){
        String urlTo ="gestorCuentasSospechosas";
        PersonaEntityDto gestor = (PersonaEntityDto) session.getAttribute("gestor");
        if(gestor == null){
            return "redirect:/";
        }
        List <CuentaEntityDto> cuentas = this.cuentaPersonaService.cuentasSospechosas();
        model.addAttribute("cuentasSospechosas", cuentas);
        return urlTo;
    }



}
