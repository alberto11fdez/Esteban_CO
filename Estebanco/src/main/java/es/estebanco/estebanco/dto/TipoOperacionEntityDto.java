package es.estebanco.estebanco.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link es.estebanco.estebanco.entity.TipoOperacionEntity} entity
 */
public class TipoOperacionEntityDto implements Serializable {
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
        TipoOperacionEntityDto entity = (TipoOperacionEntityDto) o;
        return Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ")";
    }
}