package es.estebanco.estebanco.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tipo_estado", schema = "estebanco", catalog = "")
public class TipoEstadoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoEstadoEntity that = (TipoEstadoEntity) o;
        return id == that.id && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
}
