/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.klijent;

import domain.Klijent;
import operation.GenericOperation;

/**
 *
 * @author Gogic
 */
public class PretraziKlijente extends GenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
       
    }

    @Override
    protected Object executeOperation(Object param) throws Exception {
        Klijent klijent = new Klijent();
        klijent.setJmbg((String) param);
        
        klijent = (Klijent) repositoryDB.get(klijent);
        
        
        return klijent;
    }
    
}
