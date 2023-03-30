package es.estebanco.estebanco.controller;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dao.RolRepository;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
@Controller
public class LoginController {
    @Autowired
    protected PersonaRepository personaRepository;
    @Autowired
    protected RolRepository rolRepository;
    @GetMapping
    public String doLogin(){return "login";}
    @PostMapping
    public String doAutenticar(@RequestParam("usuario") String user,
                               @RequestParam("contraseña") String contrasena,
                               Model model, HttpSession session){
        String urlTo = "redirect:/persona/";
        PersonaEntity persona = this.personaRepository.autenticar(user,contrasena);
        if(persona==null){
            model.addAttribute("error","Credenciales incorrectas");
            urlTo="login";
        }else{
            int rol = this.rolRepository.getById(persona.getId()).getId();
            if(rol == 2){
                session.setAttribute("persona",persona);
                urlTo = "redirect:/asistente/";
            }else {
                urlTo = "redirect:/persona/?id="+persona.getId();
            }

        }
        return urlTo;
    }
    @GetMapping("/logout")
    public String doLogout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
