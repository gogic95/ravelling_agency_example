/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.ugovor;

import domain.Ugovor;
import operation.GenericOperation;

/**
 *
 * @author Gogic
 */
public class StornirajUgovor extends GenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        if(param == null || !(param instanceof Ugovor)){
            throw new Exception("Invalid data(ugovor)");
        }
    }

    @Override
    protected Object executeOperation(Object param) throws Exception {
        Ugovor ugovor = (Ugovor) param;
        repositoryDB.update(ugovor);
        repositoryDB.update(ugovor.getAranzman());
        return param;
    }
    
}
