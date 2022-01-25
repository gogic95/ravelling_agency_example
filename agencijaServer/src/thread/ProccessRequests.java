/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import communication.Receiver;
import communication.Request;
import communication.Response;
import communication.Sender;
import controller.Controller;
import domain.Aranzman;
import domain.Destinacija;
import domain.Klijent;
import domain.Komercijalista;
import domain.Ugovor;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gogic
 */
public class ProccessRequests extends Thread {

    Socket socket;
    Sender sender;
    Receiver receiver;

    public ProccessRequests(Socket socket) {
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    @Override
    public void run() {
        while (socket.isConnected() && !socket.isClosed()) {
            try {
                Request request = (Request) receiver.receiveObject();
                Response response = new Response();

                try {
                    switch (request.getOperation()) {
                        case INSERT_DESTINACIJA:
                            //Destinacija destinacija = (Destinacija) request.getArgument();
                            //Destinacija generatedDestinacija = Controller.getInstance().kreirajDestinaciju((Destinacija) request.getArgument());
                            response.setResult(Controller.getInstance().kreirajDestinaciju((Destinacija) request.getArgument()));
                            break;
                        case INSERT_ARANZMAN:
                            //Aranzman aranzman = (Aranzman) request.getArgument();
                            //Aranzman generatedAranzman = Controller.getInstance().kreirajAranzman((Aranzman) request.getArgument());
                            response.setResult(Controller.getInstance().kreirajAranzman((Aranzman) request.getArgument()));
                            break;
                        case LOGIN:
                            //Komercijalista user = Controller.getInstance().login((Komercijalista) request.getArgument());
                            response.setResult(Controller.getInstance().login((Komercijalista) request.getArgument()));
                            break;
                        case GET_ALL_DESTINACIJA:
                            response.setResult(Controller.getInstance().ucitajListuDestinacija());
                            break;
                        case UPDATE_DESTINACIJA:
                            //Destinacija zapamtiDestinaciju = (Destinacija) request.getArgument();
                            Controller.getInstance().zapamtiDestinaciju((Destinacija) request.getArgument());
                            break;
                        case UPDATE_ARANZMAN:
                            //Aranzman zapamtiAranzman = (Aranzman) request.getArgument();
                            Controller.getInstance().zapamtiAranzman((Aranzman) request.getArgument());
                            break;
                        case DELETE_ARANZMAN:
                            //Aranzman obrisiAranzman = (Aranzman) request.getArgument();
                            Controller.getInstance().obrisiAranzman((Aranzman) request.getArgument());
                            break;
                        case INSERT_UGOVOR:
                            response.setResult((Ugovor) Controller.getInstance().obradiUgovor((Ugovor) request.getArgument()));
                            break;
                        case UPDATE_UGOVOR:
                            Controller.getInstance().stornirajUgovor((Ugovor) request.getArgument());
                            break;
                        case GET_UGOVOR_BY_ARANZMANID:
                            response.setResult((Ugovor) Controller.getInstance().getUgovorByAranzmanID((Aranzman) request.getArgument()));
                            break;
                        case GET_UGOVOR_BY_ID:
                            response.setResult((Ugovor) Controller.getInstance().pretraziUgovore((int) request.getArgument()));
                            break;
                        case GET_KLIJENT_BY_JMBG:
                            response.setResult((Klijent) Controller.getInstance().pretraziKlijente((String) request.getArgument()));
                            break;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    response.setException(e);
                    System.out.println(e.getMessage());
                }

                sender.sendObject(response);
            } catch (Exception ex) {
                try {
                    socket.close();
                } catch (IOException ex1) {
                    Logger.getLogger(ProccessRequests.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(ProccessRequests.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
