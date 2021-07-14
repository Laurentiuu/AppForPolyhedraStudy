package model;

import java.util.ArrayList;

public class ModelButon extends Butoane {
    private String buton;

    public ModelButon(String buton) {
        this.listaButoane = new ArrayList<Observer>();
        this.buton = null;
    }

    public ModelButon() {

    }

    public String getButon() {
        return buton;
    }

    public void setButon(String buton) {
        this.buton = buton;
    }

    public void actualizareButon(String b) {
        this.notificare();
        System.out.println("Notificare: " + this.buton);
    }

}
