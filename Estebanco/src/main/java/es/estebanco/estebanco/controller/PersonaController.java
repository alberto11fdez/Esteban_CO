package es.estebanco.estebanco.controller;
import es.estebanco.estebanco.dao.*;
import es.estebanco.estebanco.dto.ConversacionEntityDto;
import es.estebanco.estebanco.dto.CuentaEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.dto.RolEntityDto;
import es.estebanco.estebanco.entity.*;
import es.estebanco.estebanco.service.*;
import es.estebanco.estebanco.ui.FiltroOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/persona")
public class PersonaController {


    @Autowired
    protected CuentaService cuentaService;
    @Autowired
    protected ConversacionService conversacionService;
    @Autowired
    protected TipoRolService tipoRolService;
    @Autowired
    protected PersonaService personaService;
    @Autowired
    protected RolService rolService;

    @Autowired
    private RolRepository rolRepository;


    @GetMapping("/")
    public String doEntrar(@RequestParam("id") Integer idpersona,Model model, HttpSession session) {
        PersonaEntityDto persona = (PersonaEntityDto) session.getAttribute("persona");
        if (persona == null) {
            return "redirect:/";

        } else {
            model.addAttribute("persona",persona);
            List<CuentaEntityDto> cuentas = this.cuentaService.cuentasPorPersona(persona);
            model.addAttribute("cuentas",cuentas);
            List<ConversacionEntityDto> conversaciones = this.conversacionService.conversacionPorPersona(persona);
            model.addAttribute("conversaciones", conversaciones);
            List<String> tipos_rol = tipoRolService.obtenerRoles();
            model.addAttribute("tipos_rol",tipos_rol);
            RolEntityDto rol=new RolEntityDto();
            rol.setPersonaByPersonaId(persona);
            model.addAttribute("rolCuentaNueva",rol);

            model.addAttribute("rolrepository",rolRepository);
            return "persona";
            //return this.procesarFiltro(null, persona, model, session);
        }
    }
    /*
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
            if(filtro==null){
                filtro = new FiltroOperacion();
                filtro.setIdpersona(persona.getId());
            }
            switch(filtro.getTipo()){
                case "sacar":
                    operaciones = this.operacionRepository.operacionesPorPersonaYTipo(persona,filtro.getTipo());
                    break;
                case "meter":
                    operaciones = this.operacionRepository.operacionesPorPersonaYTipo(persona,filtro.getTipo());
                    break;
                case "cambio divisa":
                    operaciones = this.operacionRepository.operacionesPorPersonaYTipo(persona,filtro.getTipo());
                    break;
                case "euro":
                    operaciones = this.operacionRepository.operacionesPorPersonaYMoneda(persona,filtro.getTipo());
                    break;
                case "libra":
                    operaciones = this.operacionRepository.operacionesPorPersonaYMoneda(persona,filtro.getTipo());
                    break;
                case "ordenar por fecha":
                    operaciones = this.operacionRepository.operacionesPorPersonaOrdenadoPorFecha(persona);
                    break;
                case "ordenar por cantidad":
                    operaciones = this.operacionRepository.operacionesPorPersonaOrdenadoPorCantidad(persona);
                    break;
                default:
                    operaciones = this.operacionRepository.operacionesPorPersona(persona);

            }

            model.addAttribute("operaciones",operaciones);
            model.addAttribute("filtro",filtro);
            model.addAttribute("tipos_filtro",filtro.getTipos_filtro());

            List<String> tipos_rol = tipoRolRepository.obtenerRoles();
            model.addAttribute("tipos_rol",tipos_rol);
            RolEntity rol=new RolEntity();
            rol.setPersonaByPersonaId(persona);
            model.addAttribute("rolCuentaNueva",rol);

            model.addAttribute("rolrepository",rolRepository);

        }


        return "persona";
    }

     */




    @GetMapping("/editar")
    public String doEditarPersona(@RequestParam("id") Integer idpersona, Model model){
        PersonaEntityDto persona = personaService.encontrarPersona(idpersona);
        return this.mostrarEditarONuevo(persona,model);
    }
    protected String mostrarEditarONuevo(PersonaEntityDto persona, Model model){
        model.addAttribute("persona", persona);
        return "datos";
    }
    @PostMapping("/guardar")
    public String doGuardar (@ModelAttribute("persona") PersonaEntityDto persona) {
        persona.setEstado("esperandoConfirmacion");
        //si la persona ya esta registrada no la deja (comprobar usuario)
        String usuario=persona.getUsuario();
        if(personaService.buscarSiExisteUsuario(usuario)!=null){
            return "redirect:/persona/registrarPersona";
        }else{
            this.personaService.save(persona);
            return "redirect:/persona/?id="+persona.getId();
        }

    }

    @GetMapping("/registrarPersona")
    public String registrarPersona(Model model){
        model.addAttribute("persona",new PersonaEntityDto());
        return "datos";
    }


    @GetMapping("/entrarEnCuenta")
    public String entrarEnCuenta(@RequestParam("idPersona") Integer idPersona,@RequestParam("idCuenta") Integer idCuenta,Model model,HttpSession session){
        RolEntityDto rol=rolService.obtenerRol_Persona_en_Empresa(idPersona,idCuenta);

        if(Objects.equals(rol.getRol(), "empresa") || Objects.equals(rol.getRol(), "socio") ){
            session.setAttribute("cuenta",cuentaService.encontrarPorId(idCuenta));
            return "redirect:/cuentaEmpresa/?id="+idCuenta+"&idPersona="+idPersona;
        }else{
            return  "redirect:/cuentaPersona/?idCuenta="+idCuenta;
        }
    }


    @GetMapping("/solicitarActivacion")
    public String solicitarActivacion(@RequestParam("idCuenta") Integer idCuenta, @RequestParam("idPersona") Integer idpersona){
        CuentaEntityDto cuenta=cuentaService.encontrarPorId(idCuenta);
        cuenta.setEstado("esperandoConfirmacion");
        cuentaService.save(cuenta);
        return "redirect:/persona/?id="+idpersona;
    }
}
