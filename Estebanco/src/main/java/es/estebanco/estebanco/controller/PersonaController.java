package es.estebanco.estebanco.controller;
import es.estebanco.estebanco.dao.CuentaRepository;
import es.estebanco.estebanco.dao.OperacionRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.OperacionEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
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
            //List<OperacionEntity> operaciones = null;
            model.addAttribute("operaciones",operaciones);
        }

        return urlTo;
    }
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
        this.personaRepository.save(persona);
        return "redirect:/persona/?id="+persona.getId();
    }






}
