package es.estebanco.estebanco.dao;

import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/*
   JOSÉ MANUEL SÁNCHEZ NAVARRO , FERNANDO LÓPEZ MURILLO, NICOLÁS ZAMBRANA SMITH, SERGIO BERINO GARCÍA (25% CADA UNO). =)

 */

public interface CuentaRepository extends JpaRepository<CuentaEntity, Integer> {

    @Query("select c from CuentaEntity c join RolEntity r on c=r.cuentaByCuentaId and :persona=r.personaByPersonaId.id")
    public List<CuentaEntity> cuentasPorPersona(@Param("persona")int persona);

    @Query("select c from CuentaEntity c where c.id = :idCuenta")
    public CuentaEntity cuentaOrigen (@Param("idCuenta")Integer idCuenta);

    @Query("select c from CuentaEntity c where c.iban = :destino")
    public CuentaEntity cuentaDestinoTransferencia (@Param("destino") String destino);
    @Query("select c from CuentaEntity c where c.iban like CONCAT('%', :iban, '%')")
    public List<CuentaEntity> cuentaPorIban(@Param("iban") String iban);

    @Query("select c from CuentaEntity c where c.moneda in :divisa")
    public List<CuentaEntity> cuentaPorDivisa(@Param("divisa") List<String> divisa);

    @Query("select c from CuentaEntity c where (c.iban like CONCAT('%', :texto, '%') ) and c.moneda in :divisa")
    public List<CuentaEntity> buscarPorIbanYDivisa(@Param("texto") String texto, @Param("divisa") List<String> divisa);

    @Query("select moneda from CuentaEntity")
    public List<String> monedas();

    @Query("select distinct c from CuentaEntity c join  OperacionEntity o on c=o.cuentaByCuentaId and o.fechaOperacion <= :fechas")
    public List<CuentaEntity> cuentasInactivas(@Param("fechas") Date fechas);


    @Query("select distinct c from CuentaEntity c join OperacionEntity o on c= o.cuentaByCuentaId and o.fechaOperacion <= :fechas and c.iban like CONCAT('%', :texto, '%')")
    List<CuentaEntity> cuentaPorIbanInactiva(@Param("texto") String texto, @Param("fechas")Date fechas);

    @Query("select distinct c from CuentaEntity c join OperacionEntity o on c= o.cuentaByCuentaId and o.fechaOperacion <= :fechas and c.moneda in :divisa")
    List<CuentaEntity> cuentaPorDivisaInactiva(@Param("divisa")List<String> divisa, @Param("fechas")Date fechas);

    @Query("select distinct c from CuentaEntity c join OperacionEntity o on c= o.cuentaByCuentaId and o.fechaOperacion <= :fechas and c.iban like CONCAT('%', :texto, '%') and c.moneda in :divisa")
    List<CuentaEntity> buscarPorIbanYDivisaInactiva(String texto, List<String> divisa, java.sql.Date fechas);

    @Query("select  c from CuentaEntity c join OperacionEntity o on c= o.cuentaByCuentaId and o.ibanCuentaDestinoOrigen = (select c.iban from CuentaEntity c where c.estado = 'sospechosa')")
    List<CuentaEntity> findTransferenciaSospechosa();

    @Query("select c from CuentaEntity c where c.estado = 'sospechosa'")
    List<CuentaEntity> findCuentasExternas();
}
