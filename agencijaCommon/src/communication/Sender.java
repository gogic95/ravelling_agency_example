/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gogic
 */
public class Sender implements Serializable{
    private Socket socket;

    public Sender(Socket socket) {
        this.socket = socket;
    }

    
    
    public void sendObject(Object object) throws Exception{
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(object);
            oos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Greska u komunikaciji(slanje objekta)!\n"+ex.getMessage());
        }
    }
}
