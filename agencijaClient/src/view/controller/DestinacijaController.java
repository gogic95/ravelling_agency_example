/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.Destinacija;
import domain.Komercijalista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import view.constants.Constants;
import view.coordinator.Coordinator;
import view.form.FrmDestinacija;
import view.form.mode.FormMode;

/**
 *
 * @author Gogic
 */
public class DestinacijaController {

    private final FrmDestinacija frmDestinacija;

    public DestinacijaController(FrmDestinacija frmDestinacija) {
        this.frmDestinacija = frmDestinacija;

    }

    private void addActionListeners() {
        //btnDodaj
        frmDestinacija.addBtnDodajActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertDestinacija();
            }

            private void insertDestinacija() {
                String mesto = frmDestinacija.getTxtNazivMesta().getText().trim();
                String opis = frmDestinacija.getTxtOpis().getText().trim();
                if(mesto.isEmpty() || opis.isEmpty()){
                    JOptionPane.showMessageDialog(frmDestinacija, "Greska pri unosu destinacije!\nSva polja moraju biti popunjena!","Greska",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Destinacija destinacija = new Destinacija();
                destinacija.setKomercijalista((Komercijalista) Coordinator.getInstance().getParam(Constants.LOGGED_USER));
                destinacija.setMesto(mesto.substring(0, 1).toUpperCase()+mesto.substring(1));
                destinacija.setOpis(opis.substring(0, 1).toUpperCase()+opis.substring(1));
                try {
                    checkIfExists(mesto);
                   
                    Destinacija generated = Communication.getInstance().kreirajDestinaciju(destinacija);
                    ArrayList<Destinacija> list = (ArrayList<Destinacija>) Coordinator.getInstance().getParam(Constants.LIST_DESTINACIJA);
                    list.add(generated);
                    JOptionPane.showMessageDialog(frmDestinacija, "Uspesno sacuvana destinacija! Dodeljen ID: "+generated.getDestinacijaID(),"Obavestenje",JOptionPane.INFORMATION_MESSAGE);
                    frmDestinacija.dispose();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmDestinacija, "Sistem ne moze da kreira destinaciju\n"+e.getMessage(),"Greska",JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }

            private void checkIfExists(String mesto) throws Exception {
                ArrayList<Destinacija> list = (ArrayList<Destinacija>) Coordinator.getInstance().getParam(Constants.LIST_DESTINACIJA);
                for (Destinacija destinacija : list) {
                    if(destinacija.getMesto().equals(mesto)) throw new Exception("Destinacija vec postoji!");
                }
            }
        });
        
        
        //btnIzmeni
        frmDestinacija.addBtnIzmeniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mesto = frmDestinacija.getTxtNazivMesta().getText().trim();
                String opis = frmDestinacija.getTxtOpis().getText().trim();
                if(mesto.isEmpty() || opis.isEmpty()){
                    JOptionPane.showMessageDialog(frmDestinacija, "Sva polja moraju biti popunjena!","Greska",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Destinacija updateDestinacija = (Destinacija) Coordinator.getInstance().getParam(Constants.UPDATE_DESTINACIJA);
                
                if(mesto.equals(updateDestinacija.getMesto()) && opis.equals(updateDestinacija.getOpis())){
                    JOptionPane.showMessageDialog(frmDestinacija, "Sistem ne moze da zapamti destinaciju!\nNiste napravili nikakve izmene.","Greska",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                
                try {
                    updateDestinacija.setMesto(mesto.substring(0, 1).toUpperCase()+mesto.substring(1));
                    updateDestinacija.setOpis(opis.substring(0, 1).toUpperCase()+opis.substring(1));
                    Communication.getInstance().izmeniDestinaciju(updateDestinacija);
                    JOptionPane.showMessageDialog(frmDestinacija, "Destinacija uspesno izmenjena!","Info",JOptionPane.INFORMATION_MESSAGE);
                    
                    frmDestinacija.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmDestinacija, "Destinacija nije izmenjena!\n"+ex.getMessage(),"Greska",JOptionPane.ERROR_MESSAGE);
                }
                
            }

        });
        
        //btnOdustani
        frmDestinacija.addBtnOdustaniActionListener((ActionEvent e) -> {
            frmDestinacija.dispose();
        });
    }
        
    public void openForm(FormMode formMode) {
        prepareComponents(formMode);
        addActionListeners();
        frmDestinacija.setLocationRelativeTo(Coordinator.getInstance().getMainController().getFrmMain());
        frmDestinacija.setVisible(true);
    }

    private void prepareComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmDestinacija.getBtnIzmeni().setEnabled(false);
                frmDestinacija.getBtnDodaj().setEnabled(true);
                frmDestinacija.getLblId2().setText("Bice automatski generisan nakon uspesnog dodavanja.");
                frmDestinacija.getTxtNazivMesta().grabFocus();
                frmDestinacija.setTitle("Dodavanje destinacije");
                break;
            case FORM_EDIT:
                frmDestinacija.getBtnIzmeni().setEnabled(true);
                frmDestinacija.getBtnDodaj().setEnabled(false);
                Destinacija updateDestinacija = (Destinacija) Coordinator.getInstance().getParam(Constants.UPDATE_DESTINACIJA);
                frmDestinacija.getLblId().setText("ID: "+updateDestinacija.getDestinacijaID());
                frmDestinacija.getTxtNazivMesta().setText(updateDestinacija.getMesto());
                frmDestinacija.getTxtOpis().setText(updateDestinacija.getOpis());
                frmDestinacija.setTitle("Izmena destinacije");
                frmDestinacija.getTxtOpis().grabFocus();
                frmDestinacija.getTxtOpis().selectAll();
                break;
        }
    }

}
