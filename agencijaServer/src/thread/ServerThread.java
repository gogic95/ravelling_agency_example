/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gogic
 */
public class ServerThread extends Thread {

    private ServerSocket serverSocket;
    private List<ProccessRequests> clients;

    public ServerThread() {
        clients = new ArrayList<>();
    }
    
    
    
    @Override
    public void run() {

        try {
            
            serverSocket = new ServerSocket(9000);
            System.out.println("Server pokrenut");
            while (!serverSocket.isClosed()) {
                try {

                    System.out.println("Cekam klijente...");
                    Socket socket = serverSocket.accept();
                    System.out.println("Klijent povezan");
                    handleClient(socket);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    killClients();
                }
            }

        } catch (Exception ex) {
            killClients();
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private void handleClient(Socket socket) throws Exception {
        ProccessRequests pr = new ProccessRequests(socket);
        clients.add(pr);
        pr.start();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    private void killClients() {
        for (ProccessRequests client : clients) {
            try {
                client.socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
