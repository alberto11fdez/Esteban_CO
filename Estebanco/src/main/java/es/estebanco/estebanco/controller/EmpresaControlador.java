package es.estebanco.estebanco.controller;
import es.estebanco.estebanco.dao.OperacionRepository;
import es.estebanco.estebanco.dao.PersonaRepository;
import es.estebanco.estebanco.dao.RolRepository;
import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.OperacionEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import es.estebanco.estebanco.entity.RolEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.estebanco.estebanco.dao.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EmpresaControlador {

    private CuentaEntity cuentaEmpresa;
    @Autowired
    protected CuentaRepository cuentaRepository;
    @Autowired
    protected OperacionRepository operacionRepository;
    @Autowired
    protected PersonaRepository personaRepository;
    @Autowired
    protected RolRepository rolRepository;
/**2
 *Este metodo te leva al perfil de la cuenta de la Empresa.
 * Para ello se le ha pasado el id de la cuenta, se ha buscado la entidad en la base de datos
 * y se ha enviado al cuentaEmpresa.jsp
 */
    @GetMapping("/cuentaEmpresa")
    public String goCuentaEmpresa(@RequestParam("id") Integer idCuentaEmpresa, Model model){

        cuentaEmpresa = cuentaRepository.findById(idCuentaEmpresa).orElse(null);
        model.addAttribute("cuentaEmpresa",cuentaEmpresa);

        List<OperacionEntity> operaciones = operacionRepository.obtenerListaOperaciones(cuentaEmpresa);
        model.addAttribute("operaciones",operaciones);

        List<PersonaEntity> socios = personaRepository.obtenerSocioEmpresa(cuentaEmpresa);
        model.addAttribute("socios",socios);
        return "cuentaEmpresa";
    }


    @GetMapping("/crearSocio")
    public String goCrearSocios(Model model){
        model.addAttribute("socio",new PersonaEntity());
        return "crearSocio";
    }
    @PostMapping("/socio/guardar")
    public String doGuardar (@ModelAttribute("socio") PersonaEntity socio) {
        //crea al socio
        socio.setEstado("pendiente");
        this.personaRepository.save(socio);
        //Unimos la tabla persona y cuentaEmpresa a traves de la tabla rol
        RolEntity rol = new RolEntity();
        rol.setRol("asistente");
        rol.setCuentaByCuentaId(cuentaEmpresa);
        rol.setPersonaByPersonaId(socio);
        this.rolRepository.save(rol);

        return "redirect:/cuentaEmpresa?id="+cuentaEmpresa.getId();
    }
    @GetMapping("/socio/bloquear")
    public String bloquearSocio(@RequestParam ("id") Integer idSocio){
        PersonaEntity socio = this.personaRepository.findById(idSocio).orElse(null);
        socio.setEstado("bloqueado");
        this.personaRepository.save(socio);
        return "redirect:/cuentaEmpresa?id="+cuentaEmpresa.getId();
    }

}