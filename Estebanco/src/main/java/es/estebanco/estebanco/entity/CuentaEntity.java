package es.estebanco.estebanco.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "cuenta", schema = "estebanco", catalog = "")
public class CuentaEntity {
    @Basic
    @Column(name = "IBAN", nullable = false, length = 45)
    private String iban;
    @Basic
    @Column(name = "saldo", nullable = false)
    private int saldo;
    @Basic
    @Column(name = "moneda", nullable = false, length = 45)
    private String moneda;
    @Basic
    @Column(name = "estado", nullable = false)
    private byte estado;
    @Basic
    @Column(name = "fecha_apertura", nullable = false)
    private Timestamp fechaApertura;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @OneToMany(mappedBy = "cuentaByCuentaId")
    private Collection<OperacionEntity> operacionsById;
    @OneToMany(mappedBy = "cuentaByCuentaId")
    private Collection<RolEntity> rolsById;

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

    public Timestamp getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Timestamp fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CuentaEntity that = (CuentaEntity) o;
        return saldo == that.saldo && estado == that.estado && id == that.id && Objects.equals(iban, that.iban) && Objects.equals(moneda, that.moneda) && Objects.equals(fechaApertura, that.fechaApertura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban, saldo, moneda, estado, fechaApertura, id);
    }

    public Collection<OperacionEntity> getOperacionsById() {
        return operacionsById;
    }

    public void setOperacionsById(Collection<OperacionEntity> operacionsById) {
        this.operacionsById = operacionsById;
    }

    public Collection<RolEntity> getRolsById() {
        return rolsById;
    }

    public void setRolsById(Collection<RolEntity> rolsById) {
        this.rolsById = rolsById;
    }
}
