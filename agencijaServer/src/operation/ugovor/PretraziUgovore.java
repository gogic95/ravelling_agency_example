/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.ugovor;

import domain.Aranzman;
import domain.Destinacija;
import domain.Komercijalista;
import domain.StavkaUgovora;
import domain.Ugovor;
import java.util.ArrayList;
import operation.GenericOperation;

/**
 *
 * @author Gogic
 */
public class PretraziUgovore extends GenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        
    }

    @Override
    protected Object executeOperation(Object param) throws Exception {
        Ugovor ugovor = new Ugovor();
        ugovor.setBrojUgovora((int) param);
        ugovor = (Ugovor) repositoryDB.get(ugovor);
        
        Komercijalista komercijalista = (Komercijalista) repositoryDB.get(ugovor.getKomercijalista());
        
        Destinacija destinacija = (Destinacija) repositoryDB.get(ugovor.getAranzman().getDestinacija());
        destinacija.setKomercijalista(komercijalista);
        
        Aranzman aranzman = (Aranzman) repositoryDB.get(ugovor.getAranzman());
        aranzman.setDestinacija(destinacija);
        aranzman.setKomercijalista(komercijalista);
        
        ugovor.setAranzman(aranzman);
        ugovor.setKomercijalista(komercijalista);
        
        ArrayList<StavkaUgovora> listaStavkiUgovora = (ArrayList<StavkaUgovora>) repositoryDB.getBy(new StavkaUgovora(), "brojugovora", String.valueOf(ugovor.getBrojUgovora()));
        for (StavkaUgovora stavkaUgovora : listaStavkiUgovora) {
            stavkaUgovora.setUgovor(ugovor);
        }
        ugovor.setListaStavki(listaStavkiUgovora);
        ugovor.refreshUgovor();
        
        return ugovor;
    }
    
}
