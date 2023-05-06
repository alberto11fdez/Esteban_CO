package es.estebanco.estebanco.dao;

import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.OperacionEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/*
   NICOLAS ZAMBRANA SMITH, JOSÉ MANUEL SÁNCHEZ NAVARRO, FERNANDO LÓPEZ MURILLO (33% CADA UNO). =)
 */


public interface OperacionRepository extends JpaRepository<OperacionEntity, Integer> {

    @Query("select o from OperacionEntity o where o.cuentaByCuentaId = :cuenta ")
    public List<OperacionEntity> obtenerListaOperaciones(@Param("cuenta") CuentaEntity cuenta);





    @Query("select o from OperacionEntity o join CuentaEntity c on o.cuentaByCuentaId=c  and :cuenta = c.id")
    public List<OperacionEntity> operacionesPorCuenta(@Param("cuenta") int cuenta);
    @Query("select o from OperacionEntity o   join CuentaEntity c on o.cuentaByCuentaId=c  and :cuenta = c.id and o.tipo=:tipo")
    public List<OperacionEntity> operacionesPorCuentaYTipo(@Param("cuenta") int cuenta, @Param("tipo") String tipo);
    @Query("select o from OperacionEntity o   join CuentaEntity c on o.cuentaByCuentaId=c  and :cuenta = c.id and o.moneda=:moneda")
    public List<OperacionEntity> operacionesPorCuentaYMoneda(@Param("cuenta") int cuenta, @Param("moneda") String moneda);
    @Query("select o from OperacionEntity o join CuentaEntity c on o.cuentaByCuentaId=c and :cuenta=c.id order by o.fechaOperacion")
    public List<OperacionEntity> operacionesPorCuentaOrdenadoPorFecha(@Param("cuenta") int cuenta);
    @Query("select o from OperacionEntity o join CuentaEntity c on o.cuentaByCuentaId=c  and :cuenta = c.id order by o.cantidad")
    public List<OperacionEntity> operacionesPorCuentaOrdenadoPorCantidad(@Param("cuenta") int cuenta);
    @Query("select o from OperacionEntity o join CuentaEntity c on o.cuentaByCuentaId=c  join RolEntity  r on r.cuentaByCuentaId=c and r.personaByPersonaId=:persona")
    public List<OperacionEntity> operacionesPorPersona(@Param("persona") PersonaEntity persona);



    @Query("select o from OperacionEntity o join CuentaEntity c on o.cuentaByCuentaId=c  join RolEntity  r on r.cuentaByCuentaId=c and r.personaByPersonaId.id=:idPersona")
    public List<OperacionEntity> buscarOperacionesPorPersona(@Param("idPersona") Integer idPersona);
    @Query("select o from OperacionEntity o join CuentaEntity c on o.cuentaByCuentaId=c join RolEntity r on c=r.cuentaByCuentaId and r.personaByPersonaId.id=:idPersona and o.tipo in :tipos")
    public List<OperacionEntity> buscarPorTipoOperacion(@Param("idPersona") Integer idPersona,@Param("tipos") List<String> tipos);
    @Query("select o from OperacionEntity o join CuentaEntity c on o.cuentaByCuentaId=c join RolEntity r on c=r.cuentaByCuentaId and r.personaByPersonaId.id=:idPersona and o.ibanCuentaDestinoOrigen like CONCAT('%', :texto, '%')")
    public List<OperacionEntity> buscarPorIban(@Param("idPersona") Integer idPersona,@Param("texto") String texto);
    @Query("select o from OperacionEntity o join CuentaEntity c on o.cuentaByCuentaId=c join RolEntity r on c=r.cuentaByCuentaId and r.personaByPersonaId.id=:idPersona and o.ibanCuentaDestinoOrigen like CONCAT('%', :texto, '%') and o.tipo in :tipos")
    public List<OperacionEntity> buscarPorTipoOperacionEIban(@Param("idPersona") Integer idPersona,@Param("texto") String texto, @Param("tipos") List<String> tipos);


    @Query("select  o from OperacionEntity o where o.personaByPersonaId.id = :socioFiltro and o.cuentaByCuentaId.id = :cuentaEmpresa ")
    public List<OperacionEntity> getOperacionesSocio(@Param("socioFiltro")Integer socioFiltro, @Param("cuentaEmpresa") Integer cuentaEmpresa);

    @Query("select o from OperacionEntity o where o.ibanCuentaDestinoOrigen= :iban")
    public List<OperacionEntity> buscarOperacionesRecibidas(@Param("iban") String iban);

    @Query("select o from OperacionEntity o where o.cuentaByCuentaId.id= :idCuenta and o.fechaOperacion between :fechaInicio and :fechaFin")
    public List<OperacionEntity> sortOperacionesFecha(@Param("idCuenta") Integer idCuenta, LocalDate fechaInicio, LocalDate fechaFin);
    @Query("select o from OperacionEntity o where o.cuentaByCuentaId.id= :idCuenta and o.fechaOperacion <= :fechaFin")
    public List<OperacionEntity> sortOperacionesFechaAntiguas(@Param("idCuenta") Integer idCuenta, LocalDate fechaFin);
}
