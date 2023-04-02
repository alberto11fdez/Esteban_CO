package es.estebanco.estebanco.controller;
import es.estebanco.estebanco.dao.CuentaRepository;
import es.estebanco.estebanco.dao.OperacionRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dao.TipoRolRepository;
import es.estebanco.estebanco.entity.*;
import es.estebanco.estebanco.ui.FiltroOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    protected PersonaRepository personaRepository;
    @Autowired
    protected CuentaRepository cuentaRepository;
    @Autowired
    protected OperacionRepository operacionRepository;
    @Autowired
    protected TipoRolRepository tipoRolRepository;

    @GetMapping("/")
    public String doEntrar(@RequestParam("id") Integer idpersona,Model model, HttpSession session) {
        PersonaEntity persona = personaRepository.findById(idpersona).orElse(null);//(PersonaEntity) session.getAttribute("persona");
        if (persona == null) {
            return "redirect:/";

        } else {
            return this.procesarFiltro(null, persona, model, session);
        }
    }
    @PostMapping("/filtrar")
    public String doFiltrar(@ModelAttribute("filtro")FiltroOperacion filtro, Model model, HttpSession session){
        return this.procesarFiltro(filtro, personaRepository.findById(filtro.getIdpersona()).orElse(null), model, session);
    }
    protected String procesarFiltro(FiltroOperacion filtro, PersonaEntity persona, Model model, HttpSession session){
        if(persona==null){
            return "redirect:/";
        }else{
            model.addAttribute("persona",persona);
            List<CuentaEntity> cuentas = this.cuentaRepository.cuentasPorPersona(persona);
            model.addAttribute("cuentas",cuentas);
            List<ConversacionEntity> conversaciones = this.personaRepository.conversacionPorPersona(persona);
            model.addAttribute("conversaciones", conversaciones);
            List<OperacionEntity> operaciones;
            if(filtro==null||(filtro.getTipo().equals("todo"))&&filtro.getMoneda().equals("todo")){
                operaciones = this.operacionRepository.operacionesPorPersona(persona);
                filtro = new FiltroOperacion();
                filtro.setIdpersona(persona.getId());
            }else if(filtro.getTipo().equals("todo")){
                operaciones = this.operacionRepository.operacionesPorPersonaYMoneda(persona,filtro.getMoneda());
            }else if(filtro.getMoneda().equals("todo")){
                operaciones = this.operacionRepository.operacionesPorPersonaYTipo(persona,filtro.getTipo());
            }else{
                operaciones = this.operacionRepository.operacionesPorPersonaYTipoYMoneda(persona,filtro.getTipo(),filtro.getMoneda());
            }
            model.addAttribute("operaciones",operaciones);
            model.addAttribute("filtro",filtro);

            List<String> tipos_rol = tipoRolRepository.obtenerRoles();
            model.addAttribute("tipos_rol",tipos_rol);
            model.addAttribute("rolCuentaNueva",new RolEntity());

        }


        return "persona";
    }

/*
    @GetMapping("/")
    public String doMostrarPersonaYCuentas(@RequestParam("id") Integer idpersona, Model model, HttpSession session){
        String urlTo = "persona";
        PersonaEntity persona = personaRepository.findById(idpersona).orElse(null);//(PersonaEntity) session.getAttribute("persona");
        if(persona==null){
            urlTo="redirect:/";
        }else{
            model.addAttribute("persona",persona);
            List<CuentaEntity> cuentas = this.cuentaRepository.cuentasPorPersona(persona);
            model.addAttribute("cuentas",cuentas);
            List<OperacionEntity> operaciones = this.operacionRepository.operacionesPorPersona(persona);
            model.addAttribute("operaciones",operaciones);
            List<ConversacionEntity> conversaciones = this.personaRepository.conversacionPorPersona(persona);
            model.addAttribute("conversaciones", conversaciones);
        }

        return urlTo;
    }
 */



    @GetMapping("/editar")
    public String doEditarPersona(@RequestParam("id") Integer idpersona, Model model){
        PersonaEntity persona = personaRepository.findById(idpersona).orElse(null);
        return this.mostrarEditarONuevo(persona,model);
    }
    protected String mostrarEditarONuevo(PersonaEntity persona, Model model){
        model.addAttribute("persona", persona);
        return "datos";
    }
    @PostMapping("/guardar")
    public String doGuardar (@ModelAttribute("persona") PersonaEntity persona) {
        persona.setEstado("esperandoConfirmacion");
        //si la persona ya esta registrada no la deja (comprobar usuario)
        String usuario=persona.getUsuario();
        if(personaRepository.buscarSiExisteUsuario(usuario)!=null){
            return "redirect:/persona/registrarPersona";
        }else{
            this.personaRepository.save(persona);
            return "redirect:/persona/?id="+persona.getId();
        }

    }

    @GetMapping("/registrarPersona")
    public String registrarPersona(Model model){
        model.addAttribute("persona",new PersonaEntity());
        return "registroPersona";
    }





}
