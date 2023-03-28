package es.estebanco.estebanco.ui;

import es.estebanco.estebanco.entity.MensajeEntity;

import java.util.List;

public class Filtro {

    private int idConver;
    private int clienteId;
    private byte estado;
    private List<MensajeEntity> mensajesByIdconversacion;

    public Filtro() {
        idConver = 0;
        clienteId = 0;
        estado = 0;
        mensajesByIdconversacion = null;
    }

    public int getIdConver() {
        return idConver;
    }

    public void setIdConver(int idConver) {
        this.idConver = idConver;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }

    public List<MensajeEntity> getMensajesByIdconversacion() {
        return mensajesByIdconversacion;
    }

    public void setMensajesByIdconversacion(List<MensajeEntity> mensajesByIdconversacion) {
        this.mensajesByIdconversacion = mensajesByIdconversacion;
    }
}
