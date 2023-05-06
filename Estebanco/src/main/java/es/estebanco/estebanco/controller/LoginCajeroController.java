package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dao.RolRepository;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.entity.PersonaEntity;
import es.estebanco.estebanco.service.LoginCajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
/*
   FERNANDO LÓPEZ MURILLO -> 100%.
 */
@Controller
@RequestMapping("/cajeroLogin")
public class LoginCajeroController {

    @Autowired
    protected LoginCajeroService loginCajeroService;

    @GetMapping("/")
    public String doLoginCajero(){return "loginCajero";}
    @PostMapping("/")
    public String doAutenticarCajero(@RequestParam("usuario") String user,
                               @RequestParam("clave") String contrasena,
                               Model model, HttpSession session){
        String urlTo = "redirect:/cajero/";
        PersonaEntityDto persona = this.loginCajeroService.doAutenticar(user,contrasena);
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
