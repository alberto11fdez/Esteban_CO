package es.estebanco.estebanco.dao;

import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.OperacionEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

<<<<<<< HEAD
public interface OperacionRepository extends JpaRepository<OperacionEntity, Integer> {

    @Query("select o from OperacionEntity o where o.cuentaByCuentaId = :cuenta ")
    public List<OperacionEntity> obtenerListaOperaciones(@Param("cuenta") CuentaEntity cuenta);


=======
public interface OperacionRepository extends JpaRepository<CuentaEntity, Integer> {

    @Query("select o from OperacionEntity o join CuentaEntity c on o.cuentaByCuentaId=c join RolEntity r on c=r.cuentaByCuentaId and :persona=r.personaByPersonaId")
    public List<OperacionEntity> operacionesPorPersona(@Param("persona") PersonaEntity persona);
>>>>>>> 854fd03087c73ea732de5ef0807ec439143379f3
}
