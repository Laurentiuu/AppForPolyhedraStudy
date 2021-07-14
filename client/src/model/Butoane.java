package model;

import java.util.ArrayList;

public abstract class Butoane {
    ArrayList<Observer> listaButoane = new ArrayList<>();

    public void adaugare(Observer obs){
        this.listaButoane.add(obs);
    }
    public void stergere(Observer obs){
        this.listaButoane.remove(obs);
    }
    public void notificare(){
        for(Observer o: listaButoane){
            o.actualizare();
        }
    }
}
