package es.estebanco.estebanco.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link es.estebanco.estebanco.entity.TipoMonedaEntity} entity
 */
public class TipoMonedaEntityDto implements Serializable {
    private  Integer id;
    private  String moneda;

    public int getId() {
        return id;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoMonedaEntityDto entity = (TipoMonedaEntityDto) o;
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