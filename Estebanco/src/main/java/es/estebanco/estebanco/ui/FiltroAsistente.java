package es.estebanco.estebanco.ui;

import es.estebanco.estebanco.entity.MensajeEntity;

import java.util.List;

public class FiltroAsistente {


    private int idCliente;

    private byte estado;

    public FiltroAsistente() {
        idCliente=-1;
        estado = -1;
    }

    public byte getEstado() {
        return estado;
    }

    public void setEstado(byte estado) {
        this.estado = estado;
    }


    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
