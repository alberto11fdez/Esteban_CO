package es.estebanco.estebanco.dao;

import es.estebanco.estebanco.entity.CuentaEntity;
import es.estebanco.estebanco.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CuentaRepository extends JpaRepository<CuentaEntity, Integer> {

    @Query("select c from CuentaEntity c join RolEntity r on c=r.cuentaByCuentaId and :persona=r.personaByPersonaId.id")
    public List<CuentaEntity> cuentasPorPersona(@Param("persona")int persona);

    @Query("select c from CuentaEntity c where c.id = :idCuenta")
    public CuentaEntity cuentaOrigen (@Param("idCuenta")Integer idCuenta);

    @Query("select c from CuentaEntity c where c.iban = :destino")
    public CuentaEntity cuentaDestinoTransferencia (@Param("destino") String destino);
    @Query("select c from CuentaEntity c where c.iban = :iban")
    public List<CuentaEntity> cuentaPorIban(@Param("iban") String iban);

    @Query("select c from CuentaEntity c where c.moneda in :divisa")
    public List<CuentaEntity> cuentaPorDivisa(@Param("divisa") List<String> divisa);

    @Query("select c from CuentaEntity c where (c.iban like CONCAT('%', :texto, '%') ) and c.moneda in :divisa")
    public List<CuentaEntity> buscarPorIbanYDivisa(@Param("texto") String texto, @Param("divisa") List<String> divisa);

    @Query("select moneda from CuentaEntity")
    public List<String> monedas();



}
