package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dto.ConversacionEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.entity.ConversacionEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import es.estebanco.estebanco.service.ConversacionService;
import es.estebanco.estebanco.service.PersonaService;
import es.estebanco.estebanco.ui.FiltroAsistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/asistente")
public class AsistenteController {

    @Autowired
    protected ConversacionService asistenteService;

    @Autowired
    protected PersonaService personaService;

    @GetMapping("/")
    public String doListar (Model model, HttpSession session) {
        return this.procesarFiltrado(null, model, session);
    }

    @PostMapping("/filtrar")
    public String doFiltrar (@ModelAttribute("filtro") FiltroAsistente filtro, Model model, HttpSession session) {
        return this.procesarFiltrado(filtro, model, session);
    }

    protected String procesarFiltrado ( FiltroAsistente filtro, Model model, HttpSession session) {
        List<ConversacionEntityDto> lista;
        String urlto = "asistente";

        PersonaEntityDto asistente = (PersonaEntityDto) session.getAttribute("persona");
        if (asistente == null) {
            urlto = "redirect:/";
        } else {
            if (filtro == null || (filtro.getIdCliente()==(-1) && filtro.getEstado()==(-1))) {
                lista = this.asistenteService.conversacionPorPersona(asistente.getId());
                filtro = new FiltroAsistente();
            }else if(filtro.getIdCliente()!=(-1) && filtro.getEstado()==(-1)){
                lista = this.asistenteService.buscarPorIdCliente(filtro.getIdCliente(),asistente.getId());
            }else if(filtro.getEstado() == 1 && filtro.getIdCliente()==(-1)){
                lista = this.asistenteService.conversacionesActivas(asistente.getId());
            }else if(filtro.getEstado() == 0 && filtro.getIdCliente()==(-1)){
                lista = this.asistenteService.conversacionesBloqueadas(asistente.getId());
            }else if(filtro.getEstado() == 1 && filtro.getIdCliente()!=(-1)){
                lista = this.asistenteService.buscarPorIdClienteCuentaActiva(filtro.getIdCliente(),asistente.getId());
            }else{
                lista = this.asistenteService.buscarPorIdClienteCuentaBloqueada(filtro.getIdCliente(),asistente.getId());
            }


            model.addAttribute("filtro", filtro);
            model.addAttribute("conversaciones", lista);

            List<Byte> estadoConver = this.asistenteService.getEstados();
            model.addAttribute("estadoConver", estadoConver);

            List<Integer> idClients = this.asistenteService.getClients();
            model.addAttribute("idClients",idClients);
        }

        return urlto;
    }




    @GetMapping("/crearConversacion")
    public String doCrearConversacion(@RequestParam("idCliente")Integer idCliente, Model model){
        PersonaEntityDto cliente = this.personaService.buscarPersonaPorId(idCliente);

        ConversacionEntityDto conversacionNueva = new ConversacionEntityDto();
        //meto la idConversacion
        int idUltimaConver = this.asistenteService.getUltimaIdConversacion();
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


        List<PersonaEntityDto> asistentes = this.asistenteService.getAsistente();
        model.addAttribute("asistentes", asistentes);

        model.addAttribute("conversacionNueva", conversacionNueva);
        return "crearConversacion";
    }


    @PostMapping("/guardar")
    public String doGuardar(@ModelAttribute("conversacionNueva") ConversacionEntityDto conversacionNueva){
        asistenteService.save(conversacionNueva);
        return "redirect:/persona/?id=" + conversacionNueva.getPersonaByPersonaId().getId();
    }

    @GetMapping("/cerrar")
    public String doCerrar(@RequestParam("id")Integer idConversacion){
        ConversacionEntityDto conversacion = this.asistenteService.buscarConversacionPorId(idConversacion);
        byte estado=0;
        conversacion.setEstado(estado);

        Timestamp fecha = new Timestamp(System.currentTimeMillis());
        conversacion.setFechaFin(fecha);

        this.asistenteService.save(conversacion);
        return "redirect:/persona/?id="+ conversacion.getPersonaByPersonaId().getId();
    }



}
