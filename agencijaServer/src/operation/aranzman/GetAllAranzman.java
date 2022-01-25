/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.aranzman;

import domain.Aranzman;
import java.util.ArrayList;
import operation.GenericOperation;

/**
 *
 * @author Gogic
 */
public class GetAllAranzman extends GenericOperation{

    @Override
    protected void preconditions(Object param) throws Exception {
        
    }

    @Override
    protected Object executeOperation(Object param) throws Exception {
        ArrayList <Aranzman>  list = new ArrayList<>();
        list = (ArrayList<Aranzman>) repositoryDB.getAll((Aranzman)param);
        
        return list;
    }
    
}
