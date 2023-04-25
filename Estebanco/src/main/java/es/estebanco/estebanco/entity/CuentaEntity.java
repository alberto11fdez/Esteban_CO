package es.estebanco.estebanco.entity;

import es.estebanco.estebanco.dto.CuentaEntityDto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cuenta", schema = "estebanco", catalog = "")
public class CuentaEntity {
    @Basic
    @Column(name = "IBAN", nullable = true, length = 45)
    private String iban;
    @Basic
    @Column(name = "saldo", nullable = true)
    private int saldo;
    @Basic
    @Column(name = "moneda", nullable = true, length = 45)
    private String moneda;
    @Basic
    @Column(name = "estado", nullable = true, length = 45)
    private String estado;
    @Basic
    @Column(name = "fecha_apertura", nullable = true)
    private Timestamp fechaApertura;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = true)
    private int id;
    @OneToMany(mappedBy = "cuentaByCuentaId")
    private List<OperacionEntity> operacionsById;
    @OneToMany(mappedBy = "cuentaByCuentaId")
    private List<RolEntity> rolsById;

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
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
        return saldo == that.saldo && id == that.id && Objects.equals(iban, that.iban) && Objects.equals(moneda, that.moneda) && Objects.equals(estado, that.estado) && Objects.equals(fechaApertura, that.fechaApertura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban, saldo, moneda, estado, fechaApertura, id);
    }

    public List<OperacionEntity> getOperacionsById() {
        return operacionsById;
    }

    public void setOperacionsById(List<OperacionEntity> operacionsById) {
        this.operacionsById = operacionsById;
    }

    public List<RolEntity> getRolsById() {
        return rolsById;
    }

    public void setRolsById(List<RolEntity> rolsById) {
        this.rolsById = rolsById;
    }

    public CuentaEntityDto toDTO(){
        CuentaEntityDto dto = new CuentaEntityDto();
        dto.setIban(this.iban);
        dto.setSaldo(this.saldo);
        dto.setMoneda(this.moneda);
        dto.setEstado(this.estado);
        dto.setFechaApertura(this.fechaApertura);
        dto.setId(this.id);
        dto.setOperacionsById(this.operacionsById);
        dto.setRolsById(this.rolsById);
        return dto;
    }
}
