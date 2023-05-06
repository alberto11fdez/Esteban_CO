package es.estebanco.estebanco.dao;

import es.estebanco.estebanco.entity.TipoMonedaEntity;
import es.estebanco.estebanco.entity.TipoOperacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
    FERNANDO -> 100%.
 */

public interface TipoMonedaEntityRepository extends JpaRepository<TipoMonedaEntity, Integer> {
    @Query("select m from TipoMonedaEntity m where m.moneda = :moneda")
    public TipoMonedaEntity buscarMoneda(@Param("moneda") String moneda);

}