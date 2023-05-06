package es.estebanco.estebanco.dto;

import java.io.Serializable;
import java.util.Objects;

/*
   FERNANDO -> 100%.
 */
/**
 * A DTO for the {@link es.estebanco.estebanco.entity.TipoRolEntity} entity
 */
public class TipoRolEntityDto implements Serializable {
    private  Integer idtipoRol;
    private  String nombre;

    public int getIdtipoRol() {
        return idtipoRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setIdtipoRol(Integer idtipoRol) {
        this.idtipoRol = idtipoRol;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoRolEntityDto entity = (TipoRolEntityDto) o;
        return Objects.equals(this.idtipoRol, entity.idtipoRol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idtipoRol);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idtipoRol = " + idtipoRol + ")";
    }
}