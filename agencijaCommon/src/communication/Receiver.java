/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gogic
 */
public class Receiver implements Serializable{
    private Socket socket;

    public Receiver(Socket socket) {
        this.socket = socket;
    }
    
    public Object receiveObject() throws Exception{
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            return ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            //Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Greska u komunikaciji (prijem objekta)!\n"+ex.getMessage());
        }
        
       
    }
    
    
}
