package es.estebanco.estebanco.dao;

import es.estebanco.estebanco.entity.TipoOperacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
    FERNANDO -> 100%.
 */
public interface TipoOperacionEntityRepository extends JpaRepository<TipoOperacionEntity, Integer> {

    @Query("select t from TipoOperacionEntity t where t.id= :id")
    public TipoOperacionEntity buscarTipo(@Param("id") Integer id);

}