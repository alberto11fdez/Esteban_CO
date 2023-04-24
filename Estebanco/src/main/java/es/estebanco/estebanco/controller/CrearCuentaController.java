package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.CuentaRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dao.RolRepository;
import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import es.estebanco.estebanco.entity.RolEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.parser.Entity;
import java.sql.Timestamp;

@Controller
public class CrearCuentaController {
    @Autowired
    protected RolRepository rolRepository;
    @Autowired
    protected CuentaRepository cuentaRepository;
    @Autowired
    protected PersonaRepository personaRepository;
    @PostMapping ("/crearCuenta")
    public String crearCuenta(@ModelAttribute("rolCuentaNueva") RolEntity rol){

        CuentaEntity cuenta=new CuentaEntity();
        cuenta.setEstado("espera_confirmacion");
        cuentaRepository.save(cuenta);
        rol.setCuentaByCuentaId(cuenta);


        rol.setRol(rol.getRol());
        rolRepository.save(rol);

        if(rol.getRol().equals("empresa")){
            return "redirect:/cuentaEmpresa/crearSocio?idCuenta="+cuenta.getId();
        }else{
            return "redirect:/persona/?id="+rol.getPersonaByPersonaId().getId();
        }

    }
}
