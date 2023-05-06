package es.estebanco.estebanco.ui;

import java.util.List;
/*
    FERNANDO 100%.
 */
public class FiltroCajero {
    private String texto;
    private List<String> tipoOperacion;

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public FiltroCajero(){
        texto="";
        tipoOperacion = null;
    }


    public List<String> getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(List<String> tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }
}
