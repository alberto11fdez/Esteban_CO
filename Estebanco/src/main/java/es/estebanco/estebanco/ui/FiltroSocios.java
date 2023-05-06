package es.estebanco.estebanco.ui;

import java.util.ArrayList;
import java.util.List;

/*
   JOSÉ -> 100%.
 */
public class FiltroSocios {
    private String tipo;

    public List<String> getTipos_filtro_socio() {
        return tipos_filtro_socio;
    }

    private List<String> tipos_filtro_socio ;



    private int idpersona;
    public int getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(int idpersona) {
        this.idpersona = idpersona;
    }


    public FiltroSocios(){
        tipo="todo";
        tipos_filtro_socio = new ArrayList<>();

        tipos_filtro_socio.add("Orden Dni Ascendente");
        tipos_filtro_socio.add("Orden Dni Descendente");
        tipos_filtro_socio.add("Bloqueados");
        tipos_filtro_socio.add("Activos");
        tipos_filtro_socio.add("Orden Apellidos Ascendente");
        tipos_filtro_socio.add("Orden Apellidos Descendente");


    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
