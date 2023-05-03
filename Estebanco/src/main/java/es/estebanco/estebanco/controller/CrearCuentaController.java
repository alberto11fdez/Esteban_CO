package es.estebanco.estebanco.controller;

import es.estebanco.estebanco.dao.CuentaRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dao.RolRepository;
import es.estebanco.estebanco.dto.CuentaEntityDto;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.dto.RolEntityDto;
import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import es.estebanco.estebanco.entity.RolEntity;
import es.estebanco.estebanco.service.CuentaPersonaService;
import es.estebanco.estebanco.service.PersonaService;
import es.estebanco.estebanco.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.swing.text.html.parser.Entity;
import java.sql.Timestamp;

@Controller
public class CrearCuentaController {
    @Autowired
    protected RolService rolService;
    @Autowired
    protected CuentaPersonaService cuentaService;
    @Autowired
    protected PersonaService personaService;

    @PostMapping ("/crearCuenta")
    public String crearCuenta(@ModelAttribute("rolCuentaNueva") RolEntityDto rol, HttpSession session){


        CuentaEntityDto cuenta = new CuentaEntityDto();
        cuenta.setEstado("esperandoConfirmacion");
        Integer idcuenta = cuentaService.saveCuentaNueva(cuenta);
        cuenta=cuentaService.encontrarCuentaPorId(idcuenta);
        rol.setCuentaByCuentaId(cuenta);

        rol.setRol(rol.getRol());

        PersonaEntityDto personaEntityDto =(PersonaEntityDto) session.getAttribute("persona");
        personaEntityDto= personaService.buscarPersonaPorId(personaEntityDto.getId());
        rol.setPersonaByPersonaId(personaEntityDto);

        rolService.saveRol(rol);

        session.setAttribute("cuenta",cuenta);
        if(rol.getRol().equals("empresa")){
            return "redirect:/cuentaEmpresa/crearSocio?idCuenta="+cuenta.getId();
        }else{
            return "redirect:/persona/?id="+rol.getPersonaByPersonaId().getId();
        }

    }
}
