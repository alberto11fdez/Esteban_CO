package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dto.ConversacionEntityDto;
import es.estebanco.estebanco.dto.MensajeEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.service.MensajeService;
import es.estebanco.estebanco.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;


@Controller
@RequestMapping("/mensaje")
public class MensajeController {


    @Autowired
    protected MensajeService mensajeService;

    @Autowired
    protected PersonaService personaService;

    @GetMapping("/entrar")
    public String doEntrarCliente(@RequestParam("idCliente") Integer idCliente,@RequestParam("idAsistente") Integer idAsistente,
                                  @RequestParam("idConversacion")Integer idConversacion,@RequestParam("soyCliente")Integer soyCliente,
                                  Model model){
        PersonaEntityDto cliente = this.personaService.buscarPersonaPorId(idCliente);
        PersonaEntityDto asistente = this.personaService.buscarPersonaPorId(idAsistente);

        List<MensajeEntityDto> listaMensajes = this.mensajeService.getMensajesDeEstaConversacion(idConversacion);
        model.addAttribute("listaMensajes", listaMensajes);

        model.addAttribute("cliente", cliente);
        model.addAttribute("asistente", asistente);
        model.addAttribute("idConversacion", idConversacion);
        model.addAttribute("soyCliente", soyCliente);
        return "listaMensajes";
    }


    @GetMapping("/crearMensaje")
    public String enviarMensajeClientes(@RequestParam("idCliente") Integer idCliente,@RequestParam("idAsistente") Integer idAsistente,
                                @RequestParam("idConversacion")Integer idConversacion, @RequestParam("soyCliente")Integer soyCliente,
                                        Model model) {

        PersonaEntityDto cliente = this.personaService.buscarPersonaPorId(idCliente);
        PersonaEntityDto asistente = this.personaService.buscarPersonaPorId(idAsistente);
        ConversacionEntityDto conversacion = this.mensajeService.getConversacion(idConversacion);

        MensajeEntityDto mensajeNuevo = new MensajeEntityDto();

        int idUltimaMensaje = this.mensajeService.getUltimaIdMensaje();
        idUltimaMensaje++;
        mensajeNuevo.setIdmensaje(idUltimaMensaje);


        Timestamp fecha = new Timestamp(System.currentTimeMillis());
        mensajeNuevo.setFechaEnvio(fecha);

        if (soyCliente == 1) {
            mensajeNuevo.setConversacionEmisorId(cliente.getId());
            mensajeNuevo.setConversacionReceptorId(asistente.getId());
        }else{
            mensajeNuevo.setConversacionEmisorId(asistente.getId());
            mensajeNuevo.setConversacionReceptorId(cliente.getId());
        }

        mensajeNuevo.setIdconversacion(conversacion.getIdconversacion());

        model.addAttribute("mensajeNuevo", mensajeNuevo);
        model.addAttribute("soyCliente", soyCliente);

        return "mensajes";
    }


    @PostMapping("/guardar")
    public String doGuardar(@ModelAttribute("mensajeNuevo") MensajeEntityDto mensaje, @RequestParam("soyCliente")Integer soyCliente){

        mensajeService.save(mensaje);

        if(soyCliente==1){
            return "redirect:/mensaje/entrar?idCliente="+ mensaje.getConversacionEmisorId() + "&idAsistente="+ mensaje.getConversacionReceptorId() + "&idConversacion=" + mensaje.getIdconversacion() + "&soyCliente=" + soyCliente;
        }else{
            return "redirect:/mensaje/entrar?idCliente="+ mensaje.getConversacionReceptorId() + "&idAsistente="+ mensaje.getConversacionEmisorId() + "&idConversacion=" + mensaje.getIdconversacion()+ "&soyCliente=" + soyCliente;

        }
    }


}
