/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *
 * @author Gogic
 */
public class MainForm extends javax.swing.JFrame {



    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnPokreniServer = new javax.swing.JButton();
        btnZaustaviServer = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        btnConfigure = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Serverska forma");

        btnPokreniServer.setText("Pokreni server");

        btnZaustaviServer.setText("Zaustavi server");
        btnZaustaviServer.setMaximumSize(new java.awt.Dimension(115, 25));
        btnZaustaviServer.setMinimumSize(new java.awt.Dimension(115, 25));

        lblStatus.setText("Status: Server nije pokrenut!");

        btnConfigure.setText("Configure Database");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPokreniServer, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus))
                .addGap(86, 86, 86)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnZaustaviServer, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                    .addComponent(btnConfigure, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnZaustaviServer, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPokreniServer, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus)
                    .addComponent(btnConfigure))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    

    public JButton getBtnConfigure() {
        return btnConfigure;
    }

    public JButton getBtnPokreniServer() {
        return btnPokreniServer;
    }

    public JButton getBtnZaustaviServer() {
        return btnZaustaviServer;
    }

    public JLabel getLblStatus() {
        return lblStatus;
    }
    
    public void addBtnConfigureActionListener(ActionListener actionListener){
        btnConfigure.addActionListener(actionListener);
    }
    
    public void addBtnPokreniServerActionListener(ActionListener actionListener){
        btnPokreniServer.addActionListener(actionListener);
    }
    
    public void addBtnZaustaviServerActionListener(ActionListener actionListener){
        btnZaustaviServer.addActionListener(actionListener);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfigure;
    private javax.swing.JButton btnPokreniServer;
    private javax.swing.JButton btnZaustaviServer;
    private javax.swing.JLabel lblStatus;
    // End of variables declaration//GEN-END:variables
}
