package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.AsistenteRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.entity.ConversacionEntity;
import es.estebanco.estebanco.entity.MensajeEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import es.estebanco.estebanco.entity.RolEntity;
import es.estebanco.estebanco.ui.FiltroAsistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/asistente")
public class AsistenteController {

    @Autowired
    protected AsistenteRepository asistenteRepository;

    @Autowired
    protected PersonaRepository personaRepository;

    @GetMapping("/")
    public String doListar (Model model, HttpSession session) {
        return this.procesarFiltrado(null, model, session);
    }

    @PostMapping("/filtrar")
    public String doFiltrar (@ModelAttribute("filtro") FiltroAsistente filtro, Model model, HttpSession session) {
        return this.procesarFiltrado(filtro, model, session);
    }

    protected String procesarFiltrado ( FiltroAsistente filtro, Model model, HttpSession session) {
        List<ConversacionEntity> lista;
        String urlto = "asistente";

        PersonaEntity asistente = (PersonaEntity) session.getAttribute("persona");
        if (asistente == null) {
            urlto = "redirect:/";
        } else {
            if (filtro == null || (filtro.getIdCliente()==(-1) && filtro.getEstado()==(-1))) {
                lista = this.asistenteRepository.conversacionPorPersona(asistente.getId());
                //lista = this.asistenteRepository.findAll();
                filtro = new FiltroAsistente();
            }else if(filtro.getIdCliente()!=(-1) && filtro.getEstado()==(-1)){
                lista = this.asistenteRepository.buscarPorIdCliente(filtro.getIdCliente(),asistente.getId());
            }else if(filtro.getEstado() == 1 && filtro.getIdCliente()==(-1)){
                lista = this.asistenteRepository.conversacionesActivas(asistente.getId());
            }else if(filtro.getEstado() == 0 && filtro.getIdCliente()==(-1)){
                lista = this.asistenteRepository.conversacionesBloqueadas(asistente.getId());
            }else if(filtro.getEstado() == 1 && filtro.getIdCliente()!=(-1)){
                lista = this.asistenteRepository.buscarPorIdClienteCuentaActiva(filtro.getIdCliente(),asistente.getId());
            }else{
                lista = this.asistenteRepository.buscarPorIdClienteCuentaBloqueada(filtro.getIdCliente(),asistente.getId());
            }


            model.addAttribute("filtro", filtro);
            model.addAttribute("conversaciones", lista);

            List<Byte> estadoConver = this.asistenteRepository.getEstados();
            model.addAttribute("estadoConver", estadoConver);

            List<Integer> idClients = this.asistenteRepository.getClients();
            model.addAttribute("idClients",idClients);
        }

        return urlto;
    }




    @GetMapping("/crearConversacion")
    public String doCrearConversacion(@RequestParam("idCliente")Integer idCliente, Model model){
        PersonaEntity cliente = this.personaRepository.findById(idCliente).orElse(null);

        ConversacionEntity conversacionNueva = new ConversacionEntity();
        //meto la idConversacion
        int idUltimaConver = this.asistenteRepository.getUltimaIdConversacion();
        idUltimaConver++;
        conversacionNueva.setIdconversacion(idUltimaConver);
        //meto el estado
        byte estadoInicial = 1;
        conversacionNueva.setEstado(estadoInicial);

        //meto la fecha de inicio
        Timestamp fecha = new Timestamp(System.currentTimeMillis());
        conversacionNueva.setFechaInicio(fecha);
        //dejo a cero la fecha final
        fecha = new Timestamp(0);
        conversacionNueva.setFechaFin(fecha);

        //introduzco el cliente actual
        conversacionNueva.setPersonaByPersonaId(cliente);

        //introduzco a null el asistente y lo seleccionará el cliente
        conversacionNueva.setPersonaByAsistenteId(null);


        List<PersonaEntity> asistentes = this.asistenteRepository.getAsistente();
        model.addAttribute("asistentes", asistentes);

        model.addAttribute("conversacionNueva", conversacionNueva);
        return "crearConversacion";
    }


    @PostMapping("/guardar")
    public String doGuardar(@ModelAttribute("conversacionNueva")ConversacionEntity conversacionNueva){
        asistenteRepository.save(conversacionNueva);
        return "redirect:/persona/?id=" + conversacionNueva.getPersonaByPersonaId().getId();
    }

    @GetMapping("/cerrar")
    public String doCerrar(@RequestParam("id")Integer idConversacion){
        ConversacionEntity conversacion = this.asistenteRepository.findById(idConversacion).orElse(null);
        byte estado=0;
        conversacion.setEstado(estado);

        Timestamp fecha = new Timestamp(System.currentTimeMillis());
        conversacion.setFechaFin(fecha);

        this.asistenteRepository.save(conversacion);
        return "redirect:/persona/?id="+ conversacion.getPersonaByPersonaId().getId();
    }



}
