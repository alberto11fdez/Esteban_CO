package es.estebanco.estebanco.dto;

import es.estebanco.estebanco.entity.PersonaEntity;

import java.io.Serializable;
import java.util.Objects;
/*
   FERNANDO -> 90%.
   JOSE -> 10%.
 */
/**
 * A DTO for the {@link es.estebanco.estebanco.entity.RolEntity} entity
 */
public class RolEntityDto implements Serializable {
    private  String rol;
    private  Integer id;
    private  byte bloqueado_empresa;
    private PersonaEntityDto personaByPersonaId;
    private CuentaEntityDto cuentaByCuentaId;

    public String getRol() {
        return rol;
    }

    public int getId() {
        return id;
    }

    public byte getBloqueado_empresa() {
        return bloqueado_empresa;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setBloqueado_empresa(byte bloqueado_empresa) {
        this.bloqueado_empresa = bloqueado_empresa;
    }

    public PersonaEntityDto getPersonaByPersonaId() {
        return personaByPersonaId;
    }

    public void setPersonaByPersonaId(PersonaEntityDto personaByPersonaId) {
        this.personaByPersonaId = personaByPersonaId;
    }

    public CuentaEntityDto getCuentaByCuentaId() {
        return cuentaByCuentaId;
    }

    public void setCuentaByCuentaId(CuentaEntityDto cuentaByCuentaId) {
        this.cuentaByCuentaId = cuentaByCuentaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolEntityDto entity = (RolEntityDto) o;
        return Objects.equals(this.rol, entity.rol) &&
                Objects.equals(this.id, entity.id) &&
                Objects.equals(this.bloqueado_empresa, entity.bloqueado_empresa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rol, id, bloqueado_empresa);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "rol = " + rol + ", " +
                "id = " + id + ", " +
                "bloqueado_empresa = " + bloqueado_empresa + ")";
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer idPersona;
}