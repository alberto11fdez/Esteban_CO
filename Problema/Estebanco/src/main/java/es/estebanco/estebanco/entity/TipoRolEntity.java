package es.estebanco.estebanco.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tipo_rol", schema = "estebanco", catalog = "")
public class TipoRolEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idtipo_rol", nullable = false)
    private int idtipoRol;
    @Basic
    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    public int getIdtipoRol() {
        return idtipoRol;
    }

    public void setIdtipoRol(int idtipoRol) {
        this.idtipoRol = idtipoRol;
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
        TipoRolEntity that = (TipoRolEntity) o;
        return idtipoRol == that.idtipoRol && Objects.equals(nombre, that.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idtipoRol, nombre);
    }
}
