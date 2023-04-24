package es.estebanco.estebanco.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * A DTO for the {@link es.estebanco.estebanco.entity.CuentaEntity} entity
 */
public class CuentaEntityDto implements Serializable {
    private  String iban;
    private  Integer saldo;
    private  String moneda;
    private  String estado;
    private  Timestamp fechaApertura;
    private  Integer id;


    public String getIban() {
        return iban;
    }

    public int getSaldo() {
        return saldo;
    }

    public String getMoneda() {
        return moneda;
    }

    public String getEstado() {
        return estado;
    }

    public Timestamp getFechaApertura() {
        return fechaApertura;
    }

    public int getId() {
        return id;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setFechaApertura(Timestamp fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuentaEntityDto entity = (CuentaEntityDto) o;
        return Objects.equals(this.iban, entity.iban) &&
                Objects.equals(this.saldo, entity.saldo) &&
                Objects.equals(this.moneda, entity.moneda) &&
                Objects.equals(this.estado, entity.estado) &&
                Objects.equals(this.fechaApertura, entity.fechaApertura) &&
                Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban, saldo, moneda, estado, fechaApertura, id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "iban = " + iban + ", " +
                "saldo = " + saldo + ", " +
                "moneda = " + moneda + ", " +
                "estado = " + estado + ", " +
                "fechaApertura = " + fechaApertura + ", " +
                "id = " + id + ")";
    }
}