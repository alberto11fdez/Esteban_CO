package es.estebanco.estebanco.controller;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    protected PersonaRepository personaRepository;

    @GetMapping("/")
    public String doMostrarPersonaYCuentas(Model model, HttpSession session){
        String urlTo = "persona";
        PersonaEntity persona = (PersonaEntity) session.getAttribute("persona");
        if(persona==null){
            urlTo="redirect:/";
        }else{
            model.addAttribute("persona",persona);
            List<CuentaEntity> cuentas = this.personaRepository.cuentasPorPersona(persona);
            model.addAttribute("cuentas",cuentas);
        }

        return urlTo;
    /*
        List<PersonaEntity> listaPersonas = this.personaRepository.findAll();

        model.addAttribute("personas", listaPersonas);

        //return "/WEB-INF/view/personas.jsp";
    */
    }

    @GetMapping("/p")
    public String doMostrarEjemplo(){

        return "p";
    }




}
