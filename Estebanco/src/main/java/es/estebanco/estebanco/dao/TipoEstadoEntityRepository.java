package es.estebanco.estebanco.dao;

import es.estebanco.estebanco.entity.TipoEstadoEntity;
import es.estebanco.estebanco.entity.TipoOperacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TipoEstadoEntityRepository extends JpaRepository<TipoEstadoEntity, Integer> {
    @Query("select e from TipoEstadoEntity e where e.id= :id")
    public TipoEstadoEntity buscarTipo(@Param("id") Integer id);
}