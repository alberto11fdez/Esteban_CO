package es.estebanco.estebanco.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "conversacion", schema = "estebanco", catalog = "")
public class ConversacionEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idconversacion", nullable = false)
    private int idconversacion;
    @Basic
    @Column(name = "estado", nullable = false)
    private byte estado;
    @Basic
    @Column(name = "fecha_inicio", nullable = false)
    private Timestamp fechaInicio;
    @Basic
    @Column(name = "fecha_fin", nullable = false)
    private Timestamp fechaFin;
    @ManyToOne
    @JoinColumn(name = "Persona_id", referencedColumnName = "id", nullable = false)
    private PersonaEntity personaByPersonaId;
    @ManyToOne
    @JoinColumn(name = "Asistente_id", referencedColumnName = "id", nullable = false)
    private PersonaEntity personaByAsistenteId;
    @OneToMany(mappedBy = "conversacionByConversacionIdconversacion")
    private List<MensajeEntity> mensajesByIdconversacion;

    public int getIdconversacion() {
        return idconversacion;
    }

    public void setIdconversacion(int idconversacion) {
        this.idconversacion = idconversacion;
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

    public Timestamp getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Timestamp fechaFin) {
        this.fechaFin = fechaFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversacionEntity that = (ConversacionEntity) o;
        return idconversacion == that.idconversacion && estado == that.estado && Objects.equals(fechaInicio, that.fechaInicio) && Objects.equals(fechaFin, that.fechaFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idconversacion, estado, fechaInicio, fechaFin);
    }

    public PersonaEntity getPersonaByPersonaId() {
        return personaByPersonaId;
    }

    public void setPersonaByPersonaId(PersonaEntity personaByPersonaId) {
        this.personaByPersonaId = personaByPersonaId;
    }

    public PersonaEntity getPersonaByAsistenteId() {
        return personaByAsistenteId;
    }

    public void setPersonaByAsistenteId(PersonaEntity personaByAsistenteId) {
        this.personaByAsistenteId = personaByAsistenteId;
    }

    public List<MensajeEntity> getMensajesByIdconversacion() {
        return mensajesByIdconversacion;
    }

    public void setMensajesByIdconversacion(List<MensajeEntity> mensajesByIdconversacion) {
        this.mensajesByIdconversacion = mensajesByIdconversacion;
    }
}
