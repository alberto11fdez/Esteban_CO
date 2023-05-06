package es.estebanco.estebanco.entity;

import es.estebanco.estebanco.dto.OperacionEntityDto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/*
   ALBERTO -> 80%
   FERNANDO -> 10%.
   JOSE -> 10%.
 */

@Entity
@Table(name = "operacion", schema = "estebanco", catalog = "")
public class OperacionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idOperacion", nullable = false)
    private int idOperacion;
    @Basic
    @Column(name = "fecha_operacion", nullable = false, length = 45)
    private Date fechaOperacion;
    @Basic
    @Column(name = "tipo", nullable = false, length = 45)
    private String tipo;
    @Basic
    @Column(name = "cantidad", nullable = true)
    private Integer cantidad;
    @Basic
    @Column(name = "moneda", nullable = true, length = 45)
    private String moneda;
    @Basic
    @Column(name = "IBAN_cuentaDestinoOrigen", nullable = true, length = 45)
    private String ibanCuentaDestinoOrigen;
    @ManyToOne
    @JoinColumn(name = "Cuenta_id", referencedColumnName = "id", nullable = false)
    private CuentaEntity cuentaByCuentaId;

    @ManyToOne
    @JoinColumn(name = "Persona_id", referencedColumnName = "id", nullable = false)
    private PersonaEntity personaByPersonaId;

    public int getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(int idOperacion) {
        this.idOperacion = idOperacion;
    }

    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    public LocalDate getFechaOperacionLocal(){
        return fechaOperacion.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getIbanCuentaDestinoOrigen() {
        return ibanCuentaDestinoOrigen;
    }

    public void setIbanCuentaDestinoOrigen(String ibanCuentaDestinoOrigen) {
        this.ibanCuentaDestinoOrigen = ibanCuentaDestinoOrigen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperacionEntity that = (OperacionEntity) o;
        return idOperacion == that.idOperacion && Objects.equals(fechaOperacion, that.fechaOperacion) && Objects.equals(tipo, that.tipo) && Objects.equals(cantidad, that.cantidad) && Objects.equals(moneda, that.moneda) && Objects.equals(ibanCuentaDestinoOrigen, that.ibanCuentaDestinoOrigen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOperacion, fechaOperacion, tipo, cantidad, moneda, ibanCuentaDestinoOrigen);
    }

    public CuentaEntity getCuentaByCuentaId() {
        return cuentaByCuentaId;
    }

    public void setCuentaByCuentaId(CuentaEntity cuentaByCuentaId) {
        this.cuentaByCuentaId = cuentaByCuentaId;
    }

    public PersonaEntity getPersonaByPersonaId() {
        return personaByPersonaId;
    }

    public void setPersonaByPersonaId(PersonaEntity personaByPersonaId) {
        this.personaByPersonaId = personaByPersonaId;
    }

    public OperacionEntityDto toDTO(){
        OperacionEntityDto dto = new OperacionEntityDto();
        dto.setIdOperacion(this.idOperacion);
        dto.setFechaOperacion(this.fechaOperacion);
        dto.setTipo(this.tipo);
        dto.setCantidad(this.cantidad);
        dto.setMoneda(this.moneda);
        dto.setIbanCuentaDestinoOrigen(this.ibanCuentaDestinoOrigen);
        dto.setCuentaByCuentaId(this.cuentaByCuentaId.toDTO());
        dto.setPersonaByPersonaId(this.personaByPersonaId.toDTO());
        return dto;
    }

}
