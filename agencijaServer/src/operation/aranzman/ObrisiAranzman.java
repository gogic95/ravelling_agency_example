/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.aranzman;

import domain.Aranzman;
import operation.GenericOperation;

/**
 *
 * @author Gogic
 */
public class ObrisiAranzman extends GenericOperation{

      @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Aranzman)) {
            throw new Exception("Invalid data(aranzman)");
        }
    }

    @Override
    protected Object executeOperation(Object param) throws Exception {
        repositoryDB.delete((Aranzman)param);
        return param;
    }
    
}
