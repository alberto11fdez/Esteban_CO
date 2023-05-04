package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.CuentaRepository;
import es.estebanco.estebanco.dao.OperacionRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dao.TipoMonedaEntityRepository;
import es.estebanco.estebanco.entity.*;
import es.estebanco.estebanco.ui.FiltroGestor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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


        PersonaEntity gestor = (PersonaEntity) session.getAttribute("gestor");
        if(gestor == null) {
            urlTo = "redirect:/";
        } else {
            List <CuentaEntity> cuentas = this.cuentaRepository.findAll();
           // List <PersonaEntity> personas = this.personaRepository.findAll();

            if (filtro == null || filtro.getTexto().isEmpty() && filtro.getMonedas().isEmpty()){
                filtro = new FiltroGestor();
            } else if (filtro.getMonedas().isEmpty()) {
                cuentas = this.cuentaRepository.cuentaPorIban(filtro.getTexto());
            } else if(filtro.getTexto().isEmpty()){
                cuentas = this.cuentaRepository.cuentaPorDivisa(filtro.getMonedas());
            } else {
                cuentas = this.cuentaRepository.buscarPorIbanYDivisa(filtro.getTexto(),filtro.getMonedas());
            }
            model.addAttribute("filtro",filtro);
            model.addAttribute("cuentas", cuentas);
            List <TipoMonedaEntity> monedas = this.tipoMonedaRepository.findAll();
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
        PersonaEntity gestor =(PersonaEntity) session.getAttribute("gestor");

        if (gestor == null){
            return "redirect:/";
        } else {
            List <PersonaEntity> personas = this.personaRepository.findAll();
            model.addAttribute("personas", personas);
        }
        return urlTo;
    }
    @GetMapping("/solicitudes")
    public String doListarSolicitudes(Model model, HttpSession session){
        String urlTo ="gestorSolicitudes";
        PersonaEntity gestor = (PersonaEntity) session.getAttribute("gestor");

        if(gestor == null){
            return "redirect:/";
        } else {
            List <PersonaEntity> personaSolicitante = this.personaRepository.obtenerPersonasPorEstado("esperandoConfirmacion");
            model.addAttribute("personaSolicitante", personaSolicitante);
        }

        return urlTo;
    }
    @GetMapping("/revisar")
    public String doRevisarEstado(@RequestParam("id") Integer id, Model model, HttpSession session){
        String urlTo ="gestorRevisarEstado";
        PersonaEntity gestor = (PersonaEntity) session.getAttribute("gestor");
        PersonaEntity persona;

        if(gestor == null){
            return "redirect:/";
        } else {
            persona = this.personaRepository.findById(id).get();
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
    public String doRevisar(@ModelAttribute("personaRevisar")PersonaEntity persona){
        this.personaRepository.save(persona);
        return "redirect:/gestor/solicitudes";
    }
    @GetMapping("/revisarCuenta")
    public String doRevisarCuenta(@RequestParam("idCuenta") Integer idCuenta, Model model, HttpSession session){
        String urlTo ="gestorRevisarCuenta";
        PersonaEntity gestor = (PersonaEntity) session.getAttribute("gestor");
        if(gestor == null){
            return "redirect:/";
        }
        CuentaEntity cuenta = this.cuentaRepository.getById(idCuenta);
        model.addAttribute("cuentaRevisar", cuenta);
        return urlTo;
    }

    @PostMapping("/revisarCuenta")
    public String doRevisarCuentaPost(@ModelAttribute("cuentaRevisar") CuentaEntity cuenta, Model model, HttpSession session){
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
            this.cuentaRepository.save(cuenta);
        } else {
            cuenta.setFechaApertura(new Timestamp(System.currentTimeMillis()));
            cuenta.setEstado("bloqueado");
            this.cuentaRepository.save(cuenta);
        }

        return urlTo;
    }
    @GetMapping("/historico")
    public String doHistoricoOperaciones(@RequestParam("idCuenta") Integer idCuenta, Model model, HttpSession session){
        String urlTo ="gestorVistaOperaciones";

        CuentaEntity cuenta = this.cuentaRepository.getById(idCuenta);
        List <OperacionEntity> operaciones = this.operacionRepository.obtenerListaOperaciones(cuenta);
        model.addAttribute("operaciones", operaciones);

        return urlTo;
    }



}
