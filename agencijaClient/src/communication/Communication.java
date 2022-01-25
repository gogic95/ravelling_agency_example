/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import domain.Aranzman;
import domain.Destinacija;
import domain.Klijent;
import domain.Komercijalista;
import domain.Ugovor;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gogic
 */
public class Communication {

    private static Communication instance;

    Socket socket;
    Sender sender;
    Receiver receiver;

    private Communication() throws Exception {
        try {
            socket = new Socket("localhost", 9000);
            System.out.println("Povezan na server");
            sender = new Sender(socket);
            receiver = new Receiver(socket);
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Neuspesno povezivanje na server");
        }
    }

    public static Communication getInstance() throws Exception {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    public Komercijalista login(String username, String password) throws Exception {
        Komercijalista user = new Komercijalista();
        user.setUsername(username);
        user.setPassword(password);

        sender.sendObject(new Request(Operations.LOGIN, user));

        Response response = (Response) receiver.receiveObject();
        if (response.getException() == null) {
            return (Komercijalista) response.getResult();
        } else {
            throw response.getException();
        }

    }

    public ArrayList<Destinacija> getAllDestinacija() throws Exception {
        sender.sendObject(new Request(Operations.GET_ALL_DESTINACIJA, null));
        Response response = (Response) receiver.receiveObject();
        if (response.getException() == null) {
            return (ArrayList<Destinacija>) response.getResult();

        } else {
            throw response.getException();
        }

    }

    public Destinacija kreirajDestinaciju(Destinacija destinacija) throws Exception {
        sender.sendObject(new Request(Operations.INSERT_DESTINACIJA, destinacija));
        Response response = (Response) receiver.receiveObject();

        if (response.getException() == null) {
//            ArrayList<Destinacija> list = (ArrayList<Destinacija>) Coordinator.getInstance().getParam(Constants.LIST_DESTINACIJA);
//            list.add((Destinacija) response.getResult());

            //Coordinator.getInstance().addParameter(Constants.LIST_DESTINACIJA, list);
            return (Destinacija) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void izmeniDestinaciju(Destinacija updateDestinacija) throws Exception {
        sender.sendObject(new Request(Operations.UPDATE_DESTINACIJA, updateDestinacija));
        Response response = (Response) receiver.receiveObject();

        if (response.getException() == null) {

            //ArrayList<Destinacija> list = Communication.getInstance().getAllDestinacija();
            //Coordinator.getInstance().addParameter(Constants.LIST_DESTINACIJA, list);
        } else {
            throw response.getException();
        }
    }

    public Aranzman kreirajAranzman(Aranzman aranzman) throws Exception {
        sender.sendObject(new Request(Operations.INSERT_ARANZMAN, aranzman));
        Response response = (Response) receiver.receiveObject();

        if (response.getException() == null) {
//            Destinacija edited = (Destinacija) Coordinator.getInstance().getParam(Constants.OPEN_DESTINACIJA);
//            ArrayList<Aranzman> list = edited.getListaAranzmana();
//            list.add((Aranzman) response.getResult());
//
//            
//                ArrayList<Destinacija> listD = (ArrayList<Destinacija>) Coordinator.getInstance().getParam(Constants.LIST_DESTINACIJA);
//                for (Destinacija destinacija : listD) {
//                    if(destinacija.getDestinacijaID()==edited.getDestinacijaID()){
//                        destinacija.setListaAranzmana(edited.getListaAranzmana());
//                    }
//                }

            //Coordinator.getInstance().addParameter(Constants.LIST_DESTINACIJA, list);
            return (Aranzman) response.getResult();
        } else {
            throw response.getException();
        }
    }

    public void izmeniAranzman(Aranzman aranzman) throws Exception {
        sender.sendObject(new Request(Operations.UPDATE_ARANZMAN, aranzman));
        Response response = (Response) receiver.receiveObject();

        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public void obrisiAranzman(Aranzman selectedAranzman) throws Exception {
        sender.sendObject(new Request(Operations.DELETE_ARANZMAN, selectedAranzman));
        Response response = (Response) receiver.receiveObject();

        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public Ugovor findUgovor(Aranzman aranzman) throws Exception {
        sender.sendObject(new Request(Operations.GET_UGOVOR_BY_ARANZMANID, aranzman));
        Response response = (Response) receiver.receiveObject();

        if (response.getException() != null) {
            throw response.getException();
        } else {
            return (Ugovor) response.getResult();
        }
    }

    public Ugovor obradiUgovor(Ugovor ugovor) throws Exception {
        sender.sendObject(new Request(Operations.INSERT_UGOVOR, ugovor));
        Response response = (Response) receiver.receiveObject();

        if (response.getException() != null) {
            throw response.getException();
        } else {
            return (Ugovor) response.getResult();
        }

    }

    public void stornirajUgovor(Ugovor ugovor) throws Exception {
        sender.sendObject(new Request(Operations.UPDATE_UGOVOR, ugovor));
        Response response = (Response) receiver.receiveObject();

        if (response.getException() != null) {
            throw response.getException();
        }
    }

    public Ugovor pretraziUgovore(int ugovorID) throws Exception {
        sender.sendObject(new Request(Operations.GET_UGOVOR_BY_ID, ugovorID));
        Response response = (Response) receiver.receiveObject();

        if (response.getException() != null) {
            throw response.getException();
        } else {
            return (Ugovor) response.getResult();
        }
    }

    public Klijent pretraziKlijente(String JMBG) throws Exception {
        sender.sendObject(new Request(Operations.GET_KLIJENT_BY_JMBG, JMBG));
        Response response = (Response) receiver.receiveObject();

        if (response.getException() != null) {
            throw response.getException();
        } else {
            return (Klijent) response.getResult();
        }
    }
}
