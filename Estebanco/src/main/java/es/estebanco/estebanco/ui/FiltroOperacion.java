package es.estebanco.estebanco.ui;

import java.util.ArrayList;
import java.util.List;

/*
   NICOLÁS -> 100%.
 */

public class FiltroOperacion {
    private String tipo;

    public List<String> getTipos_filtro() {
        return tipos_filtro;
    }

    private List<String> tipos_filtro ;

    /*
        private String moneda;
    private String cantidad;
    private String fecha;
     */

    private int idpersona;
    public int getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(int idpersona) {
        this.idpersona = idpersona;
    }


    public FiltroOperacion(){
        tipo="todo";
        tipos_filtro = new ArrayList<>();

        tipos_filtro.add("sacar");
        tipos_filtro.add("meter");
        tipos_filtro.add("cambio divisa");
        tipos_filtro.add("euro");
        tipos_filtro.add("libra");
        tipos_filtro.add("ordenar por fecha");
        tipos_filtro.add("ordenar por cantidad");
        /*
                moneda="todo";
        cantidad="asc";
        fecha="asc";
         */

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    /*
        public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
     */


}
