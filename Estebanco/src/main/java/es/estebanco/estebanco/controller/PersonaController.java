package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PersonaController {

    @Autowired
    protected PersonaRepository personaRepository;

    @GetMapping("/")
    public String doMostrarPersona(Model model){

        List<PersonaEntity> listaPersonas = this.personaRepository.findAll();

        model.addAttribute("personas", listaPersonas);

        //return "/WEB-INF/view/personas.jsp";
        return "personas";
    }

    @GetMapping("/p")
    public String doMostrarEjemplo(){

        return "p";
    }




}
