/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.komercijalista;

import constants.ServerConstants;
import controller.Controller;
import domain.Komercijalista;
import operation.GenericOperation;

/**
 *
 * @author Gogic
 */
public class LoginKomercijalista extends GenericOperation {

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Komercijalista)) {
            throw new Exception("Invalid data (komercijalista)");
        }
    }

    @Override
    protected Object executeOperation(Object param) throws Exception {
//        try {
            Komercijalista expected = (Komercijalista) repositoryDB.get(param);
            Controller.getInstance().addParameter(ServerConstants.LOGGED_USER, expected);
            return expected;
//        } catch (Exception e) {
//            
//            throw new Exception("Neispravan username ili password!\n");
//        }
        
    }

}
