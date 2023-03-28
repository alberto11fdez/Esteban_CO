package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.AsistenteRepository;
import es.estebanco.estebanco.entity.ConversacionEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/asistente")
public class AsistenteController {

    @Autowired
    protected AsistenteRepository asistenteRepository;

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
