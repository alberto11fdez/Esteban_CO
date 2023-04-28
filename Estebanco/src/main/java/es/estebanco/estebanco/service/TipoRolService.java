package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.TipoRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoRolService {
    @Autowired
    protected TipoRolRepository tipoRolRepository;

    public List<String> obtenerRoles(){
        return tipoRolRepository.obtenerRoles();
    }
}
