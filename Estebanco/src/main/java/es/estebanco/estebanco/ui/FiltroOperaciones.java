package es.estebanco.estebanco.ui;

import java.util.Date;
import java.util.List;

public class FiltroOperaciones {

    private List<Date> fechas;

    public FiltroOperaciones(){
        fechas = null;
    }

    public List<Date> getFechas(){
        return fechas;
    }

    public void setFechas(List<Date> fechas){
        this.fechas = fechas;
    }
}
