package es.estebanco.estebanco.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tipo_moneda", schema = "estebanco", catalog = "")
public class TipoMonedaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "moneda", nullable = true, length = 45)
    private String moneda;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoMonedaEntity that = (TipoMonedaEntity) o;
        return id == that.id && Objects.equals(moneda, that.moneda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, moneda);
    }
}
