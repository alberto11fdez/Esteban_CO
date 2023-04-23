package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/mensaje")
public class MensajeController {


    @Autowired
    MensajeRepository mensajeRepository;

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



}
