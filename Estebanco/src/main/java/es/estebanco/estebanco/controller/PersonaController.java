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
    private CuentaPersonaService cuentaPersonaService;
    @Autowired
    private RolService rolService;
    @Autowired
    private PersonaService personaService;


    @GetMapping("/")
    public String doEntrar(@RequestParam("id") Integer idpersona,Model model, HttpSession session) {
        PersonaEntityDto persona = (PersonaEntityDto) session.getAttribute("persona");
        if (persona == null) {
            return "redirect:/";

        } else {
            model.addAttribute("persona",persona);
            List<CuentaEntityDto> cuentas = this.cuentaPersonaService.cuentasPorPersona(persona);
            model.addAttribute("cuentas",cuentas);
            List<ConversacionEntityDto> conversaciones = this.cuentaPersonaService.conversacionPorPersona(persona);
            model.addAttribute("conversaciones", conversaciones);
            List<String> tipos_rol = cuentaPersonaService.obtenerRoles();
            model.addAttribute("tipos_rol",tipos_rol);
            //RolEntity rol1 = new RolEntity();
           // RolEntityDto rol = rol1.toDTO();
            RolEntityDto rol=new RolEntityDto();
           // rol.setPersonaByPersonaId(persona);
            model.addAttribute("rolCuentaNueva",rol);

            List<Integer> listaCuentasNormalesOEmpresa= rolService.obtenerCuentasNormlesOEmpresa(idpersona);
            model.addAttribute("listaCuentasNormalesOEmpresa",listaCuentasNormalesOEmpresa);

            List<Integer> listaCuentasSocioActivado=rolService.obtenerCuentasSocioActivado(idpersona);
            model.addAttribute("listaCuentasSocioActivado",listaCuentasSocioActivado);

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
        PersonaEntityDto persona = cuentaPersonaService.encontrarPersona(idpersona);
        return this.mostrarEditarONuevo(persona,model);
    }
    protected String mostrarEditarONuevo(PersonaEntityDto persona, Model model){
        model.addAttribute("persona", persona);
        return "datos";
    }

    /*@PostMapping("/guardar")
    public String doGuardar (@ModelAttribute("persona") PersonaEntityDto persona) {
        persona.setEstado("esperandoConfirmacion");
        //si la persona ya esta registrada no la deja (comprobar usuario)
        String usuario=persona.getUsuario();
        if(cuentaPersonaService.buscarSiExisteUsuario(usuario)!=null){
            return "redirect:/persona/registrarPersona";
        }else{
            this.cuentaPersonaService.savePersona(persona);
            return "redirect:/persona/?id="+persona.getId();
        }

    }


     */
     @PostMapping("/guardar")
     public String doGuardar(@ModelAttribute("persona") PersonaEntityDto persona,HttpSession session){
         persona.setEstado("esperandoConfirmacion");
         int id = this.cuentaPersonaService.guardarPersona(persona);
         PersonaEntityDto personaDto = personaService.buscarPersonaPorId(id);
         session.setAttribute("persona",personaDto);
         return "redirect:/persona/?id="+id;
     }

    @GetMapping("/registrarPersona")
    public String registrarPersona(Model model){
        PersonaEntityDto dto = new PersonaEntityDto();
        model.addAttribute("persona",dto);
        return "datos";
    }




    @GetMapping("/entrarEnCuenta")
    public String entrarEnCuenta(@RequestParam("idPersona") Integer idPersona,@RequestParam("idCuenta") Integer idCuenta,Model model,HttpSession session){
        RolEntityDto rol=cuentaPersonaService.obtenerRol_Persona_en_Empresa(idPersona,idCuenta);

        if(Objects.equals(rol.getRol(), "empresa") || Objects.equals(rol.getRol(), "socio") ){
            session.setAttribute("cuenta",cuentaPersonaService.encontrarCuentaPorId(idCuenta));
            return "redirect:/cuentaEmpresa/?id="+idCuenta+"&idPersona="+idPersona;
        }else{
            return  "redirect:/cuentaPersona/?idCuenta="+idCuenta;
        }
    }


    @GetMapping("/solicitarActivacion")
    public String solicitarActivacion(@RequestParam("idCuenta") Integer idCuenta, @RequestParam("idPersona") Integer idpersona){
        CuentaEntityDto cuenta=cuentaPersonaService.encontrarCuentaPorId(idCuenta);
        cuenta.setEstado("esperandoConfirmacion");
        cuentaPersonaService.saveCuenta(cuenta);
        return "redirect:/persona/?id="+idpersona;
    }
}
