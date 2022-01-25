/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.ugovor;

import domain.Klijent;
import domain.StavkaUgovora;
import domain.Ugovor;
import java.util.ArrayList;
import operation.GenericOperation;

/**
 *
 * @author Gogic
 */
public class ObradiUgovor extends GenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if(param == null || !(param instanceof Ugovor)){
            throw new Exception("Invalid data(ugovor)");
        }
    }

    @Override
    protected Object executeOperation(Object param) throws Exception {
        Ugovor insertUgovor = (Ugovor) param;
        Ugovor generatedUgovor = (Ugovor) repositoryDB.insert(insertUgovor);
        ArrayList<Klijent> listaKlijenata = (ArrayList<Klijent>) repositoryDB.getAll(new Klijent());
        for(StavkaUgovora stavka : insertUgovor.getListaStavki()){
            Klijent klijent = new Klijent(-1, stavka.getJMBG(), stavka.getImePrezimeKlijenta());
            if(!listaKlijenata.contains(klijent)){
                repositoryDB.insert(klijent);
            }
            stavka.setUgovor(generatedUgovor);
            repositoryDB.insert(stavka);
        }
        repositoryDB.update(insertUgovor.getAranzman());
        
        return generatedUgovor;
    }
    
}
