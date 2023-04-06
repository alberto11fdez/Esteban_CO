package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dao.RolRepository;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cajeroLogin")
public class LoginCajeroController {
    @Autowired
    protected PersonaRepository personaRepository;
    @Autowired
    protected RolRepository rolRepository;
    @GetMapping("/")
    public String doLoginCajero(){return "loginCajero";}
    @PostMapping("/")
    public String doAutenticarCajero(@RequestParam("usuario") String user,
                               @RequestParam("clave") String contrasena,
                               Model model, HttpSession session){
        String urlTo = "redirect:/cajero/";
        PersonaEntity persona = this.personaRepository.autenticar(user,contrasena);
        if(persona==null){
            model.addAttribute("error","Credenciales incorrectas");
            urlTo="loginCajero";
        } else {
            /*int rol = this.rolRepository.getById(persona.getId()).getId();
            if(rol==2){
                model.addAttribute("error","Rol incorrecto");
                urlTo="loginCajero";
            } else {*/
                session.setAttribute("persona", persona);
            //}
        }
        return urlTo;
    }
    @GetMapping("/logout")
    public String doLogoutCajero(HttpSession session){
        session.invalidate();
        return "loginCajero";
    }
}
