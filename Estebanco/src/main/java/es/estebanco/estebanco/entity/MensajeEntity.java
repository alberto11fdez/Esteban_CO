package es.estebanco.estebanco.entity;

import es.estebanco.estebanco.dto.MensajeEntityDto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mensaje", schema = "estebanco", catalog = "")
public class MensajeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idmensaje", nullable = false)
    private int idmensaje;
    @Basic
    @Column(name = "fecha_envio", nullable = false)
    private Timestamp fechaEnvio;
    @Basic
    @Column(name = "texto", nullable = false, length = 100)
    private String texto;
    @Basic
    @Column(name = "conversacion_Emisor_id", nullable = false)
    private int conversacionEmisorId;
    @Basic
    @Column(name = "conversacion_Receptor_id", nullable = false)
    private int conversacionReceptorId;
    @ManyToOne
    @JoinColumn(name = "conversacion_idconversacion", referencedColumnName = "idconversacion", nullable = false)
    private ConversacionEntity conversacionByConversacionIdconversacion;

    public int getIdmensaje() {
        return idmensaje;
    }

    public void setIdmensaje(int idmensaje) {
        this.idmensaje = idmensaje;
    }

    public Timestamp getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Timestamp fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getConversacionEmisorId() {
        return conversacionEmisorId;
    }

    public void setConversacionEmisorId(int conversacionEmisorId) {
        this.conversacionEmisorId = conversacionEmisorId;
    }

    public int getConversacionReceptorId() {
        return conversacionReceptorId;
    }

    public void setConversacionReceptorId(int conversacionReceptorId) {
        this.conversacionReceptorId = conversacionReceptorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MensajeEntity that = (MensajeEntity) o;
        return idmensaje == that.idmensaje && conversacionEmisorId == that.conversacionEmisorId && conversacionReceptorId == that.conversacionReceptorId && Objects.equals(fechaEnvio, that.fechaEnvio) && Objects.equals(texto, that.texto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idmensaje, fechaEnvio, texto, conversacionEmisorId, conversacionReceptorId);
    }

    public ConversacionEntity getConversacionByConversacionIdconversacion() {
        return conversacionByConversacionIdconversacion;
    }

    public void setConversacionByConversacionIdconversacion(ConversacionEntity conversacionByConversacionIdconversacion) {
        this.conversacionByConversacionIdconversacion = conversacionByConversacionIdconversacion;
    }

    public MensajeEntityDto toDTO(){
        MensajeEntityDto dto = new MensajeEntityDto();
        dto.setIdmensaje(this.idmensaje);
        dto.setFechaEnvio(this.fechaEnvio);
        dto.setTexto(this.texto);
        dto.setConversacionEmisorId(this.conversacionEmisorId);
        dto.setConversacionReceptorId(this.conversacionReceptorId);
        dto.setConversacionByConversacionIdconversacion(this.conversacionByConversacionIdconversacion.toDTO());
        return dto;
    }
}
