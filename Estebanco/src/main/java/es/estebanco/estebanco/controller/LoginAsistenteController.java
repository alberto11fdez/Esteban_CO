package es.estebanco.estebanco.controller;
import es.estebanco.estebanco.dao.AsistenteRepository;
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
@RequestMapping("/loginAsistente")
public class LoginAsistenteController {
    @Autowired
    protected AsistenteRepository asistenteRepository;
    @GetMapping
    public String doLogin(){return "loginAsistente";}
    @PostMapping
    public String doAutenticar(@RequestParam("usuario") String user,
                               @RequestParam("contraseña") String contrasena,
                               Model model, HttpSession session){
        String urlTo = "redirect:/asistente/";
        PersonaEntity persona = this.asistenteRepository.autenticar(user,contrasena);
        if(persona==null){
            model.addAttribute("error","Credenciales incorrectas");
            urlTo="loginAsistente";
        }else {
            session.setAttribute("persona",persona);
        }
        return urlTo;
    }
    @GetMapping("/logout")
    public String doLogout(HttpSession session){
        session.invalidate();
        return "redirect:/loginAsistente";
    }
}
