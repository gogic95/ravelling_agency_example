/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation.destinacija;

import constants.ServerConstants;
import controller.Controller;
import domain.Aranzman;
import domain.Destinacija;
import domain.Komercijalista;
import java.util.ArrayList;
import operation.GenericOperation;

/**
 *
 * @author Gogic
 */
public class UcitajListuDestinacija extends GenericOperation {

    @Override
    protected void preconditions(Object param) throws Exception {

    }

    @Override
    protected Object executeOperation(Object param) throws Exception {
        ArrayList<Destinacija> listDestinacija = (ArrayList<Destinacija>) repositoryDB.getAll((Destinacija) param);

        ArrayList<Destinacija> validatedUserList = validateUserList(listDestinacija);

        ArrayList<Aranzman> listAranzman = (ArrayList<Aranzman>) repositoryDB.getAll(new Aranzman());

        for (Destinacija destinacija : validatedUserList) {
            ArrayList<Aranzman> helpListAranzman = new ArrayList<>();
            for (Aranzman aranzman : listAranzman) {
                if (aranzman.getDestinacija().getDestinacijaID() == destinacija.getDestinacijaID()) {
                    aranzman.setDestinacija(destinacija);
                    aranzman.setKomercijalista((Komercijalista) Controller.getInstance().getParam(ServerConstants.LOGGED_USER));
                    helpListAranzman.add(aranzman);
                }
            }

            destinacija.setListaAranzmana(helpListAranzman);
        }

        return validatedUserList;
    }

    private ArrayList<Destinacija> validateUserList(ArrayList<Destinacija> list) {
        Komercijalista user = (Komercijalista) Controller.getInstance().getParam(ServerConstants.LOGGED_USER);
        ArrayList<Destinacija> validatedList = new ArrayList<>();

        for (Destinacija destinacija : list) {
            if (destinacija.getKomercijalista().getKomercijalistaID() == user.getKomercijalistaID()) {
                destinacija.setKomercijalista((Komercijalista) Controller.getInstance().getParam(ServerConstants.LOGGED_USER));
                validatedList.add(destinacija);
            }
        }

        return validatedList;
    }

}
