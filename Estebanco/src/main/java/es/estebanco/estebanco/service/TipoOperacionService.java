package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.TipoMonedaEntityRepository;
import es.estebanco.estebanco.dao.TipoOperacionEntityRepository;
import es.estebanco.estebanco.dto.PersonaEntityDto;
import es.estebanco.estebanco.dto.TipoOperacionEntityDto;
import es.estebanco.estebanco.entity.PersonaEntity;
import es.estebanco.estebanco.entity.TipoOperacionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TipoOperacionService {
    @Autowired
    protected TipoOperacionEntityRepository tipoOperacionEntityRepository;
    public TipoOperacionEntityDto buscarTipo(int i) {
        return tipoOperacionEntityRepository.buscarTipo(i).toDTO();
    }

    public List<TipoOperacionEntityDto> findAll() {
        List<TipoOperacionEntity> tipoOperacionEntityList = tipoOperacionEntityRepository.findAll();
        ArrayList dtos = new ArrayList<PersonaEntityDto>();

        tipoOperacionEntityList.forEach((final TipoOperacionEntity tipoOperacionEntity) -> dtos.add(tipoOperacionEntity.toDTO()));

        return  dtos;
    }
}
