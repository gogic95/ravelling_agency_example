/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller;

import form.view.DbConfigureForm;
import form.view.MainForm;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import thread.ServerThread;

/**
 *
 * @author Gogic
 */
public class MainFormController {

    private final MainForm mainForm;
    private ServerThread serverThread;

    public MainFormController(MainForm mainForm) {
        this.mainForm = mainForm;
        openForm();
    }

    public MainForm getMainForm() {
        return mainForm;
    }

    private void openForm() {
        mainForm.setLocationRelativeTo(null);
        mainForm.getBtnZaustaviServer().setEnabled(false);
        addActionListeners();
        mainForm.setVisible(true);
    }

    private void addActionListeners() {
        //btnPokreniServer
        mainForm.addBtnPokreniServerActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverThread == null || !serverThread.isAlive()) {
                    mainForm.getBtnPokreniServer().setEnabled(false);
                    mainForm.getBtnZaustaviServer().setEnabled(true);
                    serverThread = new ServerThread();
                    serverThread.start();
                    mainForm.getLblStatus().setText("Status: Server je pokrenut!");
                    mainForm.getLblStatus().setForeground(Color.GREEN);
                    JOptionPane.showMessageDialog(mainForm, "Server je uspesno pokrenut!", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //btnZaustaviServer
        mainForm.addBtnZaustaviServerActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (serverThread.getServerSocket() != null && serverThread.getServerSocket().isBound()) {
                        serverThread.getServerSocket().close();
                        mainForm.getBtnPokreniServer().setEnabled(true);
                        mainForm.getBtnZaustaviServer().setEnabled(false);
                        mainForm.getLblStatus().setText("Status: Server je zaustavljen!");
                        mainForm.getLblStatus().setForeground(Color.red);
                        JOptionPane.showMessageDialog(mainForm, "Server je zaustavljen!", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //btnConfigure
        mainForm.addBtnConfigureActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DbConfigureFormController(new DbConfigureForm(mainForm, true));
            }
        });
    }

}
