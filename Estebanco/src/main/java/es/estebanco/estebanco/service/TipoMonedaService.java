package es.estebanco.estebanco.service;

import es.estebanco.estebanco.dao.TipoMonedaEntityRepository;
import es.estebanco.estebanco.dto.OperacionEntityDto;
import es.estebanco.estebanco.dto.TipoMonedaEntityDto;
import es.estebanco.estebanco.entity.OperacionEntity;
import es.estebanco.estebanco.entity.TipoMonedaEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*
   JOSE -> 100%.
 */

@Service
public class TipoMonedaService {
    private final TipoMonedaEntityRepository tipoMonedaEntityRepository;

    public TipoMonedaService(TipoMonedaEntityRepository tipoMonedaEntityRepository) {
        this.tipoMonedaEntityRepository = tipoMonedaEntityRepository;
    }

    public List<TipoMonedaEntityDto> findAll() {
        List<TipoMonedaEntity> tipoMonedaEntitiesList=tipoMonedaEntityRepository.findAll();
        ArrayList dtos = new ArrayList<OperacionEntityDto>();

        tipoMonedaEntitiesList.forEach((final TipoMonedaEntity tipoMoneda) -> dtos.add(tipoMoneda.toDTO()));

        return  dtos;
    }

    public TipoMonedaEntityDto buscarMoneda(String moneda) {
        return tipoMonedaEntityRepository.buscarMoneda(moneda).toDTO();
    }
}
