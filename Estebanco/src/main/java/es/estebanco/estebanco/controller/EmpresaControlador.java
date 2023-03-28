package es.estebanco.estebanco.controller;
import es.estebanco.estebanco.entity.CuentaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.estebanco.estebanco.dao.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class EmpresaControlador {

    private CuentaEntity cuentaEmpresa;
    @Autowired
    protected CuentaRepository cuentaRepository;
/**
 *Este metodo te leva al perfil de la cuenta de la Empresa.
 * Para ello se le ha pasado el id de la cuenta, se ha buscado la entidad en la base de datos
 * y se ha enviado al cuentaEmpresa.jsp
 */
    @GetMapping("/cuentaEmpresa")
    public String goCuentaEmpresa(@RequestParam("id") Integer idCuentaEmpresa, Model model){
        CuentaEntity cuentaEmpresa = cuentaRepository.findById(idCuentaEmpresa).orElse(null);
        model.addAttribute("cuentaEmpresa",cuentaEmpresa);
        return "cuentaEmpresa";
    }


    @GetMapping()
    public String goCrearSocios(){
        return "crearSocios";
    }



}
