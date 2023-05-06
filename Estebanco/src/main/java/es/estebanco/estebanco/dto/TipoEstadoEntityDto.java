package es.estebanco.estebanco.dto;

import java.io.Serializable;
import java.util.Objects;

/*
   FERNANDO -> 100%.
 */

/**
 * A DTO for the {@link es.estebanco.estebanco.entity.TipoEstadoEntity} entity
 */
public class TipoEstadoEntityDto implements Serializable {
    private  Integer id;
    private  String nombre;


    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoEstadoEntityDto entity = (TipoEstadoEntityDto) o;
        return Objects.equals(this.id, entity.id) &&
                Objects.equals(this.nombre, entity.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "nombre = " + nombre + ")";
    }
}