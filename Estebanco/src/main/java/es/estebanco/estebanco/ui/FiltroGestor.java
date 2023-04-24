package es.estebanco.estebanco.ui;

import es.estebanco.estebanco.dao.CuentaRepository;

import java.util.List;

public class FiltroGestor {

    private String texto;
    private List<String> monedas;

    private String[] checkboxes;

    public FiltroGestor(){
        texto = "";
        monedas = null;
    }
    public String getTexto(){
        return texto;
    }

    public void setTexto(String texto){
        this.texto = texto;
    }

    public List<String> getMonedas(){
        return monedas;
    }

    public void setMonedas(List<String> monedas){
        this.monedas = monedas;
    }

    public String[] getCheckboxes(){
        return checkboxes;
    }

    public void setCheckboxes(String[] checkboxes){
        this.checkboxes = checkboxes;
    }

}
