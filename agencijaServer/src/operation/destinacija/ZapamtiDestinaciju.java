/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.destinacija;

import domain.Destinacija;
import operation.GenericOperation;

/**
 *
 * @author Gogic
 */
public class ZapamtiDestinaciju extends GenericOperation {

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Destinacija)) {
            throw new Exception("Invalid data(destinacija)");
        }
    }

    @Override
    protected Object executeOperation(Object param) throws Exception {
        
            repositoryDB.update((Destinacija) param);
            return param;
       

    }

}
