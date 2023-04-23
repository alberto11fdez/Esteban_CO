package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.AsistenteRepository;
import es.estebanco.estebanco.dao.MensajeRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.entity.ConversacionEntity;
import es.estebanco.estebanco.entity.MensajeEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/mensaje")
public class MensajeController {


    @Autowired
    MensajeRepository mensajeRepository;

    @Autowired
    PersonaRepository personaRepository;



    @GetMapping("/entrarCliente")
    public String doEntrarCliente(@RequestParam("idCliente") Integer idCliente,@RequestParam("idAsistente") Integer idAsistente,
                                  @RequestParam("idConversacion")Integer idConversacion, Model model){
        PersonaEntity cliente = this.personaRepository.findById(idCliente).orElse(null);
        PersonaEntity asistente = this.personaRepository.findById(idAsistente).orElse(null);

        List<MensajeEntity> listaMensajes = this.mensajeRepository.getMensajesDeEstaConversacion(idConversacion);
        model.addAttribute("listaMensajes", listaMensajes);

        model.addAttribute("cliente", cliente);
        model.addAttribute("asistente", asistente);
        model.addAttribute("idConversacion", idConversacion);
        return "listaMensajesClientes";
    }

    @GetMapping("/entrarAsistente")
    public String doEntrarAsistente(@RequestParam("idCliente") Integer idCliente,@RequestParam("idAsistente") Integer idAsistente,
                                    @RequestParam("idConversacion")Integer idConversacion, Model model){
        PersonaEntity cliente = this.personaRepository.findById(idCliente).orElse(null);
        PersonaEntity asistente = this.personaRepository.findById(idAsistente).orElse(null);

        List<MensajeEntity> listaMensajes = this.mensajeRepository.getMensajesDeEstaConversacion(idConversacion);
        model.addAttribute("listaMensajes", listaMensajes);

        model.addAttribute("cliente", cliente);
        model.addAttribute("asistente", asistente);
        model.addAttribute("idConversacion", idConversacion);
        return "listaMensajesAsistentes";
    }



    @GetMapping("/crearMensajeClientes")
    public String enviarMensajeClientes(@RequestParam("idCliente") Integer idCliente,@RequestParam("idAsistente") Integer idAsistente,
                                @RequestParam("idConversacion")Integer idConversacion, Model model) {

        PersonaEntity cliente = this.personaRepository.findById(idCliente).orElse(null);
        PersonaEntity asistente = this.personaRepository.findById(idAsistente).orElse(null);
        ConversacionEntity conversacion = this.mensajeRepository.getConversacion(idConversacion);

        MensajeEntity mensajeNuevo = new MensajeEntity();

        //meto la id del mensaje
        int idUltimaMensaje = this.mensajeRepository.getUltimaIdMensaje();
        idUltimaMensaje++;
        mensajeNuevo.setIdmensaje(idUltimaMensaje);

        //meto la fecha de envio
        Timestamp fecha = new Timestamp(System.currentTimeMillis());
        mensajeNuevo.setFechaEnvio(fecha);


        mensajeNuevo.setConversacionEmisorId(cliente.getId());
        mensajeNuevo.setConversacionReceptorId(asistente.getId());
        mensajeNuevo.setConversacionByConversacionIdconversacion(conversacion);

        model.addAttribute("mensajeNuevo", mensajeNuevo);

        return "mensajes";
    }

    @GetMapping("/crearMensajeAsistentes")
    public String enviarMensajeAsistentes(@RequestParam("idCliente") Integer idCliente,@RequestParam("idAsistente") Integer idAsistente,
                                @RequestParam("idConversacion")Integer idConversacion, Model model) {

        PersonaEntity cliente = this.personaRepository.findById(idCliente).orElse(null);
        PersonaEntity asistente = this.personaRepository.findById(idAsistente).orElse(null);
        ConversacionEntity conversacion = this.mensajeRepository.getConversacion(idConversacion);

        MensajeEntity mensajeNuevo = new MensajeEntity();

        //meto la id del mensaje
        int idUltimaMensaje = this.mensajeRepository.getUltimaIdMensaje();
        idUltimaMensaje++;
        mensajeNuevo.setIdmensaje(idUltimaMensaje);

        //meto la fecha de envio
        Timestamp fecha = new Timestamp(System.currentTimeMillis());
        mensajeNuevo.setFechaEnvio(fecha);


        mensajeNuevo.setConversacionEmisorId(asistente.getId());
        mensajeNuevo.setConversacionReceptorId(cliente.getId());
        mensajeNuevo.setConversacionByConversacionIdconversacion(conversacion);

        model.addAttribute("mensajeNuevo", mensajeNuevo);

        return "mensajes";
    }


    @PostMapping("/guardar")
    public String doGuardar(@ModelAttribute("mensajeNuevo") MensajeEntity mensaje){

        List<Integer> clientes = this.mensajeRepository.getClientes();
        List<Integer> asistentes = this.mensajeRepository.getAsistentes();
        mensajeRepository.save(mensaje);

        boolean clienteEsEmisor=false;
        int i = 0;

        while (i < clientes.size() && clienteEsEmisor==false){
            if(clientes.get(i)==mensaje.getConversacionEmisorId()) {
                clienteEsEmisor=true;
            }
            i++;
        }

        if(clienteEsEmisor==true){
            return "redirect:/mensaje/entrarCliente?idCliente="+ mensaje.getConversacionEmisorId() + "&idAsistente="+ mensaje.getConversacionReceptorId() + "&idConversacion=" + mensaje.getConversacionByConversacionIdconversacion().getIdconversacion();
        }else{
            return "redirect:/mensaje/entrarAsistente?idCliente="+ mensaje.getConversacionReceptorId() + "&idAsistente="+ mensaje.getConversacionEmisorId() + "&idConversacion=" + mensaje.getConversacionByConversacionIdconversacion().getIdconversacion();
        }
    }


}
