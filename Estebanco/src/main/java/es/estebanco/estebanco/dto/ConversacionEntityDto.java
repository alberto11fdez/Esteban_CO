package es.estebanco.estebanco.dto;

import es.estebanco.estebanco.entity.MensajeEntity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link es.estebanco.estebanco.entity.ConversacionEntity} entity
 */
public class ConversacionEntityDto implements Serializable {
    private  Integer idconversacion;
    private  byte estado;
    private  Timestamp fechaInicio;
    private  Timestamp fechaFin;
    private PersonaEntityDto personaByPersonaId;
    private PersonaEntityDto personaByAsistenteId;
    private List<MensajeEntity> mensajesByIdconversacion;
    private Integer idAsistente;

    public Integer getIdPersona() {
        return idAsistente;
    }

    public void setIdPersona(Integer idPersona) {
        this.idAsistente = idPersona;
    }

    public Integer getIdconversacion() {
        return idconversacion;
    }

    public void setIdconversacion(Integer idconversacion) {
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

    public PersonaEntityDto getPersonaByPersonaId() {
        return personaByPersonaId;
    }

    public void setPersonaByPersonaId(PersonaEntityDto personaByPersonaId) {
        this.personaByPersonaId = personaByPersonaId;
    }

    public PersonaEntityDto getPersonaByAsistenteId() {
        return personaByAsistenteId;
    }

    public void setPersonaByAsistenteId(PersonaEntityDto personaByAsistenteId) {
        this.personaByAsistenteId = personaByAsistenteId;
    }

    public List<MensajeEntity> getMensajesByIdconversacion() {
        return mensajesByIdconversacion;
    }

    public void setMensajesByIdconversacion(List<MensajeEntity> mensajesByIdconversacion) {
        this.mensajesByIdconversacion = mensajesByIdconversacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConversacionEntityDto entity = (ConversacionEntityDto) o;
        return Objects.equals(this.idconversacion, entity.idconversacion) &&
                Objects.equals(this.estado, entity.estado) &&
                Objects.equals(this.fechaInicio, entity.fechaInicio) &&
                Objects.equals(this.fechaFin, entity.fechaFin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idconversacion, estado, fechaInicio, fechaFin);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idconversacion = " + idconversacion + ", " +
                "estado = " + estado + ", " +
                "fechaInicio = " + fechaInicio + ", " +
                "fechaFin = " + fechaFin + ")";
    }
}