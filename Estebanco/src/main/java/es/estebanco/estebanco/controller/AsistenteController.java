package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.AsistenteRepository;
import es.estebanco.estebanco.entity.ConversacionEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import es.estebanco.estebanco.ui.Filtro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/asistente")
public class AsistenteController {

    @Autowired
    protected AsistenteRepository asistenteRepository;

    @GetMapping("/")
    public String doListar (Model model, HttpSession session) {
        return this.procesarFiltrado(null, model, session);
    }

    @PostMapping("/filtrar")
    public String doFiltrar (@ModelAttribute("filtro") Filtro filtro, Model model, HttpSession session) {
        return this.procesarFiltrado(filtro, model, session);
    }

    /*
    @GetMapping("/")
    public String doMostrarListaClientes(Model model, HttpSession session){
        String urlTo = "asistente";

       PersonaEntity asistente = (PersonaEntity) session.getAttribute("persona");
        if(asistente == null){
            urlTo="redirect:/";
        }else{
            List<ConversacionEntity> conversaciones = this.asistenteRepository.conversacionPorPersona(asistente);
            model.addAttribute("conversaciones", conversaciones);
        }
        return urlTo;
    }

     */

    protected String procesarFiltrado (Filtro filtro, Model model, HttpSession session) {
        List<ConversacionEntity> lista;
        String urlto = "asistente";

        PersonaEntity asistente = (PersonaEntity) session.getAttribute("persona");
        if (asistente == null) {
            urlto = "redirect:/";
        } else {
            if (filtro == null || (filtro.getIdConver()==0 && filtro.getMensajesByIdconversacion().isEmpty())) {
                lista = this.asistenteRepository.findAll();
                filtro = new Filtro();
            } else if (filtro.getIdConver() == 0) {
                lista = this.asistenteRepository.buscarPorMensajes(filtro.getMensajesByIdconversacion());
            }

           // model.addAttribute("clientes", lista);
            model.addAttribute("filtro", filtro);
            List<ConversacionEntity> conversaciones = this.asistenteRepository.conversacionPorPersona(asistente);
            model.addAttribute("conversaciones", conversaciones);
        }

        return urlto;
    }


    @GetMapping("/entrar")
    public String doEntrar(Model model){
        return "mensajes";
    }

    @PostMapping("/mensajes")
    public String enviarMensaje(@RequestParam("mensaje") String mensaje, Model model) {
        // Aquí puedes agregar la lógica para actualizar la conversación
        // Por ejemplo, puedes guardar los mensajes en una lista y pasarla a la vista
        List<String> mensajesEnviados = new ArrayList<>();
        mensajesEnviados.add(mensaje);
        model.addAttribute("mensajes", mensajesEnviados);
        return "mensajes";
    }
/*
    @PostMapping("/conversacion")
    public String enviarMensaje(@RequestParam("mensaje") String mensaje, Model model) {
        List<String> mensajes = (List<String>) model.getAttribute("mensajes");
        if (mensajes == null) {
            mensajes = new ArrayList<>();
        }
        mensajes.add(0, mensaje); // Agregar el nuevo mensaje en la primera posición
        model.addAttribute("mensajes", mensajes);
        return "conversacion";
    }

 */



}
