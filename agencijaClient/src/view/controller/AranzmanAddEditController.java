/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.Aranzman;
import domain.Destinacija;
import domain.Komercijalista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.constants.Constants;
import view.coordinator.Coordinator;
import view.form.FrmAranzmanAddEdit;
import view.form.mode.FormMode;

/**
 *
 * @author Gogic
 */
public class AranzmanAddEditController {

    private final FrmAranzmanAddEdit frmAranzmanAddEdit;
    private Destinacija destinacija;
    private Aranzman aranzman;

    public AranzmanAddEditController(FrmAranzmanAddEdit frmAranzmanAddEdit) {
        this.frmAranzmanAddEdit = frmAranzmanAddEdit;
    }

    public AranzmanAddEditController(FrmAranzmanAddEdit frmAranzmanAddEdit, Destinacija destinacija) {
        this.frmAranzmanAddEdit = frmAranzmanAddEdit;
        this.destinacija = destinacija;
    }

    public AranzmanAddEditController(FrmAranzmanAddEdit frmAranzmanAddEdit, Aranzman selectedAranzman) {
        this.frmAranzmanAddEdit = frmAranzmanAddEdit;
        this.aranzman = selectedAranzman;
        this.destinacija = selectedAranzman.getDestinacija();
    }

    public void openForm(FormMode formMode) {
        fillComboBox();
        prepareComponents(formMode);
        fillLabels();
        addActoinListeners();
        frmAranzmanAddEdit.setLocationRelativeTo(null);
        frmAranzmanAddEdit.setVisible(true);
    }

    private void prepareComponents(FormMode formMode) {

        switch (formMode) {
            case FORM_ADD:
                frmAranzmanAddEdit.getBtnIzmeniAranzman().setEnabled(false);
                frmAranzmanAddEdit.getBtnSacuvajAranzman().setEnabled(true);
                frmAranzmanAddEdit.getLblRBaranzmana().setText("Bice automatski generisan nakon uspesnog dodavanja aranzmana.");
                frmAranzmanAddEdit.setTitle("Dodavanje aranzmana");
                break;
            case FORM_EDIT:
                frmAranzmanAddEdit.getBtnIzmeniAranzman().setEnabled(true);
                frmAranzmanAddEdit.getBtnSacuvajAranzman().setEnabled(false);
                frmAranzmanAddEdit.getTxtCenaPoKrevetu().setText(String.valueOf(aranzman.getCenaPoKrevetu()));
                frmAranzmanAddEdit.getTxtNazivSmestaja().setText(aranzman.getNazivSmestaja());
                frmAranzmanAddEdit.getTxtCenaPoKrevetu().grabFocus();
                frmAranzmanAddEdit.getTxtCenaPoKrevetu().selectAll();
                frmAranzmanAddEdit.getLblRBaranzmana().setText(String.valueOf(aranzman.getAranzmanRB()));
                frmAranzmanAddEdit.setTitle("Izmena aranzmana");
                frmAranzmanAddEdit.getCmbBrojKreveta().setSelectedItem(aranzman.getBrojKreveta());
                break;
        }
    }

    private void addActoinListeners() {
        //btnSacuvajAranzman
        frmAranzmanAddEdit.addBtnSacuvajAranzmanActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertAranzman();
            }

            private void insertAranzman() {
                String nazivSmestaja = frmAranzmanAddEdit.getTxtNazivSmestaja().getText().trim();
                String cenaPoKrevetu = frmAranzmanAddEdit.getTxtCenaPoKrevetu().getText().trim();
                int brojKreveta = (int) frmAranzmanAddEdit.getCmbBrojKreveta().getSelectedItem();
                if (nazivSmestaja.isEmpty() || cenaPoKrevetu.isEmpty()) {
                    JOptionPane.showMessageDialog(frmAranzmanAddEdit, "Greska pri unosu aranzmana!\nSva polja moraju biti popunjena!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    BigDecimal cenaPoKrevetuDouble = new BigDecimal(cenaPoKrevetu);
                    if(cenaPoKrevetuDouble.compareTo(new BigDecimal(0)) < 1 ){
                        JOptionPane.showMessageDialog(frmAranzmanAddEdit, "Greska pri unosu aranzmana!\nCena mora biti veca od 0!", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Aranzman aranzman = new Aranzman(
                            //                           (Destinacija)Coordinator.getInstance().getParam(Constants.OPEN_DESTINACIJA), 
                            destinacija,
                            0,
                            nazivSmestaja.substring(0, 1).toUpperCase()+nazivSmestaja.substring(1),
                            cenaPoKrevetuDouble,
                            brojKreveta,
                            false,
                            (Komercijalista) Coordinator.getInstance().getParam(Constants.LOGGED_USER));

                    Aranzman generatedAranzman = Communication.getInstance().kreirajAranzman(aranzman);
                    destinacija.getListaAranzmana().add(generatedAranzman);
                    destinacija.refreshDestinacija();
                    JOptionPane.showMessageDialog(frmAranzmanAddEdit, "Sistem je zapamtio aranzman! Dodeljen RB: " + generatedAranzman.getAranzmanRB(), "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                    frmAranzmanAddEdit.dispose();
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(frmAranzmanAddEdit, "Sistem ne moze da kreira aranzman!\nCena nije u odgovarajucem formatu!", "Greska", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmAranzmanAddEdit, "Sistem ne moze da zapamti aranzman!", "Greska", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(AranzmanAddEditController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });

        //btnIzmeni
        frmAranzmanAddEdit.addBtnIzmeniAranzmanActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nazivSmestaja = frmAranzmanAddEdit.getTxtNazivSmestaja().getText().trim();
                String cenaPoKrevetu = frmAranzmanAddEdit.getTxtCenaPoKrevetu().getText().trim();
                int brojKreveta = (int) frmAranzmanAddEdit.getCmbBrojKreveta().getSelectedItem();
                if (nazivSmestaja.isEmpty() || cenaPoKrevetu.isEmpty()) {
                    JOptionPane.showMessageDialog(frmAranzmanAddEdit, "Sva polja moraju biti popunjena!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    BigDecimal cenaPoKrevetuDouble = new BigDecimal(cenaPoKrevetu);
                    if(cenaPoKrevetuDouble.compareTo(new BigDecimal(0)) < 1 ){
                        JOptionPane.showMessageDialog(frmAranzmanAddEdit, "Greska pri izmeni aranzmana!\nCena mora biti veca od 0!", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (nazivSmestaja.equals(aranzman.getNazivSmestaja()) && Objects.equals(aranzman.getCenaPoKrevetu(), cenaPoKrevetuDouble) && brojKreveta == aranzman.getBrojKreveta()) {
                        JOptionPane.showMessageDialog(frmAranzmanAddEdit, "Sistem ne moze da zapamti aranzman.\nNiste napravili nikakve izmene.", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    aranzman.setBrojKreveta(brojKreveta);
                    aranzman.setCenaPoKrevetu(cenaPoKrevetuDouble);
                    aranzman.setNazivSmestaja(nazivSmestaja.substring(0, 1).toUpperCase()+nazivSmestaja.substring(1));

                    Communication.getInstance().izmeniAranzman(aranzman);
                    JOptionPane.showMessageDialog(frmAranzmanAddEdit, "Aranzman uspesno izmenjen!", "Info", JOptionPane.INFORMATION_MESSAGE);

                    frmAranzmanAddEdit.dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frmAranzmanAddEdit, "Sistem ne moze da izmeni aranzman!\nCena nije u odgovarajucem formatu!", "Greska", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmAranzmanAddEdit, "Aranzman nije izmenjen!\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        //btnOdustani
        frmAranzmanAddEdit.addBtnOdustaniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmAranzmanAddEdit.dispose();
            }
        });
    }

    private void fillComboBox() {
        frmAranzmanAddEdit.getCmbBrojKreveta().removeAllItems();
        for (int i = 1; i < 10; i++) {
            frmAranzmanAddEdit.getCmbBrojKreveta().addItem(i);
        }
    }

    private void fillLabels() {
        //frmAranzmanAddEdit.getLblDestinacija().setText(((Destinacija)Coordinator.getInstance().getParam(Constants.OPEN_DESTINACIJA)).toString());
        frmAranzmanAddEdit.getLblDestinacija().setText(destinacija.toString());
        frmAranzmanAddEdit.getLblKomercijalista().setText(((Komercijalista) Coordinator.getInstance().getParam(Constants.LOGGED_USER)).toString());
    }

}
