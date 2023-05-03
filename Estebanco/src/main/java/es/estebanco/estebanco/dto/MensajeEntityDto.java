package es.estebanco.estebanco.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * A DTO for the {@link es.estebanco.estebanco.entity.MensajeEntity} entity
 */
public class MensajeEntityDto implements Serializable {
    private  Integer idmensaje;
    private  Timestamp fechaEnvio;
    private  String texto;
    private  Integer conversacionEmisorId;
    private  Integer conversacionReceptorId;
    private ConversacionEntityDto conversacionByConversacionIdconversacion;
    private Integer idconversacion;

    public Integer getIdconversacion() {
        return idconversacion;
    }

    public void setIdconversacion(Integer idconversacion) {
        this.idconversacion = idconversacion;
    }

    public int getIdmensaje() {
        return idmensaje;
    }

    public Timestamp getFechaEnvio() {
        return fechaEnvio;
    }

    public String getTexto() {
        return texto;
    }

    public int getConversacionEmisorId() {
        return conversacionEmisorId;
    }

    public int getConversacionReceptorId() {
        return conversacionReceptorId;
    }

    public void setIdmensaje(Integer idmensaje) {
        this.idmensaje = idmensaje;
    }

    public void setFechaEnvio(Timestamp fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setConversacionEmisorId(Integer conversacionEmisorId) {
        this.conversacionEmisorId = conversacionEmisorId;
    }

    public void setConversacionReceptorId(Integer conversacionReceptorId) {
        this.conversacionReceptorId = conversacionReceptorId;
    }

    public ConversacionEntityDto getConversacionByConversacionIdconversacion() {
        return conversacionByConversacionIdconversacion;
    }

    public void setConversacionByConversacionIdconversacion(ConversacionEntityDto conversacionByConversacionIdconversacion) {
        this.conversacionByConversacionIdconversacion = conversacionByConversacionIdconversacion;
    }

 
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MensajeEntityDto entity = (MensajeEntityDto) o;
        return Objects.equals(this.idmensaje, entity.idmensaje) &&
                Objects.equals(this.fechaEnvio, entity.fechaEnvio) &&
                Objects.equals(this.texto, entity.texto) &&
                Objects.equals(this.conversacionEmisorId, entity.conversacionEmisorId) &&
                Objects.equals(this.conversacionReceptorId, entity.conversacionReceptorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idmensaje, fechaEnvio, texto, conversacionEmisorId, conversacionReceptorId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "idmensaje = " + idmensaje + ", " +
                "fechaEnvio = " + fechaEnvio + ", " +
                "texto = " + texto + ", " +
                "conversacionEmisorId = " + conversacionEmisorId + ", " +
                "conversacionReceptorId = " + conversacionReceptorId + ")";
    }
}