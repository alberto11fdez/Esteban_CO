package es.estebanco.estebanco.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "rol", schema = "estebanco", catalog = "")
public class RolEntity {
    @Basic
    @Column(name = "rol", nullable = false, length = 45)
    private String rol;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "Persona_id", referencedColumnName = "id", nullable = false)
    private PersonaEntity personaByPersonaId;
    @ManyToOne
    @JoinColumn(name = "Cuenta_id", referencedColumnName = "id", nullable = false)
    private CuentaEntity cuentaByCuentaId;

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
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
        RolEntity rolEntity = (RolEntity) o;
        return id == rolEntity.id && Objects.equals(rol, rolEntity.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rol, id);
    }

    public PersonaEntity getPersonaByPersonaId() {
        return personaByPersonaId;
    }

    public void setPersonaByPersonaId(PersonaEntity personaByPersonaId) {
        this.personaByPersonaId = personaByPersonaId;
    }

    public CuentaEntity getCuentaByCuentaId() {
        return cuentaByCuentaId;
    }

    public void setCuentaByCuentaId(CuentaEntity cuentaByCuentaId) {
        this.cuentaByCuentaId = cuentaByCuentaId;
    }
}
