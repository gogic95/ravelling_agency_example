/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.Komercijalista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.constants.Constants;
import view.coordinator.Coordinator;
import view.form.FrmLogin;

/**
 *
 * @author Gogic
 */
public class LoginController {

    private final FrmLogin frmLogin;

    public LoginController(FrmLogin frmLogin) {
        this.frmLogin = frmLogin;
        addActionListener();
    }

    public void openForm() {
        frmLogin.setVisible(true);
    }

    private void addActionListener() {
        frmLogin.loginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login(e);
            }

            private void login(ActionEvent e) {
                resetForm();
                try {
                    String username = frmLogin.getTxtUsername().getText();
                    String password = new String(frmLogin.getTxtPassword().getPassword());

                    validateForm(username, password);

                    Komercijalista user = Communication.getInstance().login(username, password);
                    JOptionPane.showMessageDialog(frmLogin, "Welcome, " + user.toString() + "!", "Login", JOptionPane.INFORMATION_MESSAGE);
                    
                    frmLogin.dispose();
                    
                    Coordinator.getInstance().addParameter(Constants.LOGGED_USER, user);
                    Coordinator.getInstance().openMainForm();
                } catch (Exception ex) {
                    //Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmLogin, "Greska prilikom prijavljivanja na sistem!\nServer: "+ex.getMessage(), "Login error", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void validateForm(String username, String password) throws Exception {
                String error = "";

                if (username.isEmpty()) {
                    error += "Username field cannot be empty!\n";
                    frmLogin.getLblUsernameError().setText("Username field cannot be empty!");
                }

                if (password.isEmpty()) {
                    error += "Password field cannot be empty!\n";
                    frmLogin.getLblPasswordError().setText("Password field cannot be empty!");
                }

                if (!error.isEmpty()) {

                    throw new Exception(error);
                }
            }

            private void resetForm() {
                frmLogin.getLblUsernameError().setText("");
                frmLogin.getLblPasswordError().setText("");
            }

        });
    }

}
