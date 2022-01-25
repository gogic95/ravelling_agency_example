/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.ugovor;

import domain.Aranzman;
import domain.StavkaUgovora;
import domain.Ugovor;
import java.util.ArrayList;
import operation.GenericOperation;

/**
 *
 * @author Gogic
 */
public class GetUgovorByAranzmanID extends GenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if(param == null || !(param instanceof Aranzman)){
            throw new Exception("Invalid data(aranzman)");
        }
    }

    @Override
    protected Ugovor executeOperation(Object param) throws Exception {
        Ugovor ugovor = new Ugovor();
        
        ugovor.setAranzman((Aranzman)param);
        ugovor = (Ugovor) repositoryDB.get(ugovor);
        ugovor.setAranzman((Aranzman)param);
        ugovor.setKomercijalista(((Aranzman)param).getKomercijalista());
        ArrayList<StavkaUgovora> listaStavkiUgovora = (ArrayList<StavkaUgovora>) repositoryDB.getBy(new StavkaUgovora(), "brojugovora", String.valueOf(ugovor.getBrojUgovora()));
        for (StavkaUgovora stavkaUgovora : listaStavkiUgovora) {
            stavkaUgovora.setUgovor(ugovor);
        }
        ugovor.setListaStavki(listaStavkiUgovora);
        ugovor.refreshUgovor();
        return ugovor;
    }
    
}
