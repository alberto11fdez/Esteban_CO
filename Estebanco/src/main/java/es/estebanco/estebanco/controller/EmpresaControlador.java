package es.estebanco.estebanco.controller;
import es.estebanco.estebanco.dao.OperacionRepository;
import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.OperacionEntity;
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

import java.util.List;

@Controller
public class EmpresaControlador {

    private CuentaEntity cuentaEmpresa;
    @Autowired
    protected CuentaRepository cuentaRepository;
    @Autowired
    protected OperacionRepository operacionRepository;
/**
 *Este metodo te leva al perfil de la cuenta de la Empresa.
 * Para ello se le ha pasado el id de la cuenta, se ha buscado la entidad en la base de datos
 * y se ha enviado al cuentaEmpresa.jsp
 */
    @GetMapping("/cuentaEmpresa")
    public String goCuentaEmpresa(@RequestParam("id") Integer idCuentaEmpresa, Model model){

         cuentaEmpresa = cuentaRepository.findById(idCuentaEmpresa).orElse(null);
        //CuentaEntity cuentaEmpresa = cuentaRepository.findById(1).orElse(null);
        model.addAttribute("cuentaEmpresa",cuentaEmpresa);

        List<OperacionEntity> operaciones = operacionRepository.obtenerListaOperaciones(cuentaEmpresa);
       // List<OperacionEntity> operaciones = cuentaEmpresa.getOperacionsById();
        model.addAttribute("operaciones",operaciones);


        return "cuentaEmpresa";
    }

    @GetMapping("/nff")
    public String goCrearSocios(){
        return "crearSocios";
    }

}
