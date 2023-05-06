package es.estebanco.estebanco.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
/*
   FERNANDO -> 100%.
 */
/**
 * A DTO for the {@link es.estebanco.estebanco.entity.OperacionEntity} entity
 */
public class OperacionEntityDto implements Serializable {
    private  Integer idOperacion;
    private  Date fechaOperacion;
    private  String tipo;
    private  Integer cantidad;
    private  String moneda;
    private  String ibanCuentaDestinoOrigen;
    private CuentaEntityDto cuentaByCuentaId;
    private PersonaEntityDto personaByPersonaId;


    public int getIdOperacion() {
        return idOperacion;
    }

    public Date getFechaOperacion() {
        return fechaOperacion;
    }

    public String getTipo() {
        return tipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public String getMoneda() {
        return moneda;
    }

    public String getIbanCuentaDestinoOrigen() {
        return ibanCuentaDestinoOrigen;
    }

    public void setIdOperacion(Integer idOperacion) {
        this.idOperacion = idOperacion;
    }

    public void setFechaOperacion(Date fechaOperacion) {
        this.fechaOperacion = fechaOperacion;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public LocalDate getFechaOperacionLocal(){
        return fechaOperacion.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public void setIbanCuentaDestinoOrigen(String ibanCuentaDestinoOrigen) {
        this.ibanCuentaDestinoOrigen = ibanCuentaDestinoOrigen;
    }

    public CuentaEntityDto getCuentaByCuentaId() {
        return cuentaByCuentaId;
    }

    public void setCuentaByCuentaId(CuentaEntityDto cuentaByCuentaId) {
        this.cuentaByCuentaId = cuentaByCuentaId;
    }

    public PersonaEntityDto getPersonaByPersonaId() {
        return personaByPersonaId;
    }

    public void setPersonaByPersonaId(PersonaEntityDto personaByPersonaId) {
        this.personaByPersonaId = personaByPersonaId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OperacionEntityDto entity = (OperacionEntityDto) o;
        return Objects.equals(this.idOperacion, entity.idOperacion) &&
                Objects.equals(this.fechaOperacion, entity.fechaOperacion) &&
                Objects.equals(this.tipo, entity.tipo) &&
                Objects.equals(this.cantidad, entity.cantidad) &&
                Objects.equals(this.moneda, entity.moneda) &&
                Objects.equals(this.ibanCuentaDestinoOrigen, entity.ibanCuentaDestinoOrigen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOperacion, fechaOperacion, tipo, cantidad, moneda, ibanCuentaDestinoOrigen);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idOperacion = " + idOperacion + ", " +
                "fechaOperacion = " + fechaOperacion + ", " +
                "tipo = " + tipo + ", " +
                "cantidad = " + cantidad + ", " +
                "moneda = " + moneda + ", " +
                "ibanCuentaDestinoOrigen = " + ibanCuentaDestinoOrigen + ")";
    }
}