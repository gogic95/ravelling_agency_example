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
import domain.Ugovor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.constants.Constants;
import view.coordinator.Coordinator;
import view.form.FrmAranzmanMain;
import view.form.table.TableModelAranzman;

/**
 *
 * @author Gogic
 */
public class AranzmanMainController {

    private final FrmAranzmanMain frmAranzmanMain;
    private Destinacija destinacija;

    public AranzmanMainController(FrmAranzmanMain frmAranzmanMain) {
        this.frmAranzmanMain = frmAranzmanMain;
        addAcitionListeners();
    }

    public AranzmanMainController(FrmAranzmanMain frmAranzmanMain, Destinacija selectedDestinacija) {
        this.frmAranzmanMain = frmAranzmanMain;
        this.destinacija = selectedDestinacija;
        addAcitionListeners();

    }

    public FrmAranzmanMain getFrmAranzmanMain() {
        return frmAranzmanMain;
    }

    public void openForm() {

        prepareForm();
        prepareTable();
        frmAranzmanMain.setVisible(true);

    }

    private void addAcitionListeners() {
        //btnDodajAranzman
        frmAranzmanMain.addBtnDodajAranzmanActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Coordinator.getInstance().openInsertAranzmanAddEditForm(destinacija);
                //Destinacija destinacija = (Destinacija) Coordinator.getInstance().getParam(Constants.OPEN_DESTINACIJA);

                ((TableModelAranzman) frmAranzmanMain.getTblAranzmani().getModel()).setDestinacija(destinacija);
                destinacija.refreshDestinacija();
                updateForm(destinacija);
            }

            private void updateForm(Destinacija destinacija) {
                frmAranzmanMain.getLblPopunjen().setText(String.valueOf(destinacija.getPopunjenKapacitet()));
                frmAranzmanMain.getLblUkupan().setText(String.valueOf(destinacija.getUkupanKapacitet()));
            }
        });

        //brnIzmeniAranzman
        frmAranzmanMain.addBtnIzmeniAranzmanActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAranzman();
            }

            private void updateAranzman() {
                int selectedIndex = frmAranzmanMain.getTblAranzmani().getSelectedRow();
                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(frmAranzmanMain, "Niste odabrali nijedan aranzman!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    TableModelAranzman tma = (TableModelAranzman) frmAranzmanMain.getTblAranzmani().getModel();
                    Aranzman selectedAranzman = tma.getAranzmanAt(selectedIndex);
                    if (selectedAranzman.isPodUgovorom()) {
                        JOptionPane.showMessageDialog(frmAranzmanMain, "Aranzman je pod ugovorom i ne moze se menjati!!", "Greska", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    //Coordinator.getInstance().addParameter(Constants.UPDATE_ARANZMAN, selectedAranzman);
                    Coordinator.getInstance().openUpdateAranzmanAddEditForm(selectedAranzman);
                    //((TableModelDestinacija) frmMain.getTblDestinacija().getModel()).setListDestinacija((ArrayList<Destinacija>) Coordinator.getInstance().getParam(Constants.LIST_DESTINACIJA));
                    destinacija.refreshDestinacija();
                    updateForm();
                    tma.refreshTable();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmAranzmanMain, "Greska prilikom izmene aranzmana!\n" + e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void updateForm() {
                frmAranzmanMain.getLblUkupan().setText(String.valueOf(destinacija.getUkupanKapacitet()));
                frmAranzmanMain.getLblPopunjen().setText(String.valueOf(destinacija.getPopunjenKapacitet()));
            }
        });

        //btnPretrazi
        frmAranzmanMain.addBtnPretraziAranzmanActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTable();
            }

            private void searchTable() {
                String search = frmAranzmanMain.getTxtPretrazi().getText().trim();

                try {
                    TableModelAranzman tma = (TableModelAranzman) frmAranzmanMain.getTblAranzmani().getModel();
                    tma.searchTable(search);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmAranzmanMain, ex.getMessage(), "Greska prilikom pretrage", JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        //btnResetujTabelu
        frmAranzmanMain.addBtnResetujTabeluActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    TableModelAranzman tma = (TableModelAranzman) frmAranzmanMain.getTblAranzmani().getModel();
                    tma.searchTable("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmAranzmanMain, ex.getMessage(), "Greska prilikom pretrage", JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        //btnObrisiIzTabele
        frmAranzmanMain.addBtnObrisiAranzmanActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = frmAranzmanMain.getTblAranzmani().getSelectedRow();
                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(frmAranzmanMain, "Niste odabrali nijedan aranzman!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                TableModelAranzman tma = (TableModelAranzman) frmAranzmanMain.getTblAranzmani().getModel();
                Aranzman selectedAranzman = tma.getAranzmanAt(selectedIndex);
                if (selectedAranzman.isPodUgovorom()) {
                    JOptionPane.showMessageDialog(frmAranzmanMain, "Aranzman je pod ugovorom i ne moze se obrisati!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {

                    Communication.getInstance().obrisiAranzman(selectedAranzman);
                    destinacija.getListaAranzmana().remove(selectedAranzman);
                    destinacija.refreshDestinacija();
                    updateForm();
                    tma.searchTable("");
                    JOptionPane.showMessageDialog(frmAranzmanMain, "Uspesno ste obrisali aranzman!\nNaziv: " + selectedAranzman.getNazivSmestaja() + "\nRB: " + selectedAranzman.getAranzmanRB(), "Info", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmAranzmanMain, "Greska prilikom brisanja!\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }

                destinacija.refreshDestinacija();
                updateForm();
                tma.refreshTable();

            }

            private void updateForm() {
                frmAranzmanMain.getLblUkupan().setText(String.valueOf(destinacija.getUkupanKapacitet()));
                frmAranzmanMain.getLblPopunjen().setText(String.valueOf(destinacija.getPopunjenKapacitet()));
            }
        });

        //btnKreirajUgovor
        frmAranzmanMain.addBtnKreirajUgovorActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createUgovor();
            }

            private void createUgovor() {
                int selectedIndex = frmAranzmanMain.getTblAranzmani().getSelectedRow();
                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(frmAranzmanMain, "Niste odabrali nijedan aranzman!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                TableModelAranzman tma = (TableModelAranzman) frmAranzmanMain.getTblAranzmani().getModel();
                Aranzman selectedAranzman = tma.getAranzmanAt(selectedIndex);
                if (selectedAranzman.isPodUgovorom()) {
                    JOptionPane.showMessageDialog(frmAranzmanMain, "Sistem ne moze da kreira ugovor.\nAranzman je vec pod ugovorom!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Coordinator.getInstance().openCreateUgovorForm(selectedAranzman);
                destinacija.refreshDestinacija();
                updateForm();
                tma.refreshTable();
            }

            private void updateForm() {
                frmAranzmanMain.getLblUkupan().setText(String.valueOf(destinacija.getUkupanKapacitet()));
                frmAranzmanMain.getLblPopunjen().setText(String.valueOf(destinacija.getPopunjenKapacitet()));
            }
        });

        //btnPogledajUgovor
        frmAranzmanMain.addBbtnPogledajUgovorActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewUgovor();
            }

            private void viewUgovor() {
                int selectedIndex = frmAranzmanMain.getTblAranzmani().getSelectedRow();
                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(frmAranzmanMain, "Niste odabrali nijedan aranzman!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                TableModelAranzman tma = (TableModelAranzman) frmAranzmanMain.getTblAranzmani().getModel();
                Aranzman selectedAranzman = tma.getAranzmanAt(selectedIndex);
                if (!selectedAranzman.isPodUgovorom()) {
                    JOptionPane.showMessageDialog(frmAranzmanMain, "Aranzman NIJE pod ugovorom!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Coordinator.getInstance().openViewUgovorForm(selectedAranzman);
                destinacija.refreshDestinacija();
                updateForm();
                tma.refreshTable();
            }

            private void updateForm() {
                frmAranzmanMain.getLblUkupan().setText(String.valueOf(destinacija.getUkupanKapacitet()));
                frmAranzmanMain.getLblPopunjen().setText(String.valueOf(destinacija.getPopunjenKapacitet()));
            }
        });

        //btnPretraziPoBrojuUgovora
        frmAranzmanMain.addBtnPretraziUgovorActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findUgovor();
            }

            private void findUgovor() {
                try {
                    int ugovorID = Integer.parseInt(frmAranzmanMain.getTxtPretraziUgovore().getText());
                    Ugovor ugovor = Communication.getInstance().pretraziUgovore(ugovorID);
                    if(!ugovor.getAranzman().getDestinacija().equals(destinacija)){
                        JOptionPane.showMessageDialog(frmAranzmanMain, "Ugovor nije vezan za destinaciju: "+destinacija.getMesto()+"\nProbajte u okviru destinacije: "+ugovor.getAranzman().getDestinacija().getMesto(),"Obavestenje",JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    //Coordinator.getInstance().openViewUgovorFormBySearch(ugovor);
                    
                    ArrayList<Aranzman> listAranzman = destinacija.getListaAranzmana();
                    for (Aranzman aranzman : listAranzman) {
                        if (aranzman.getAranzmanRB() == ugovor.getAranzman().getAranzmanRB()) {
                            ugovor.setAranzman(aranzman);
                            Coordinator.getInstance().openViewUgovorFormBySearch(ugovor);
                        }
                    }

                    destinacija.refreshDestinacija();
                    updateForm();
                    TableModelAranzman tma = (TableModelAranzman) frmAranzmanMain.getTblAranzmani().getModel();
                    tma.refreshTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frmAranzmanMain, "Neispravan format unosa.", "Greska", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    Logger.getLogger(AranzmanMainController.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmAranzmanMain, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void updateForm() {
                frmAranzmanMain.getLblUkupan().setText(String.valueOf(destinacija.getUkupanKapacitet()));
                frmAranzmanMain.getLblPopunjen().setText(String.valueOf(destinacija.getPopunjenKapacitet()));
            }
        });
    }

    private void prepareForm() {
        //Destinacija destinacija = (Destinacija) Coordinator.getInstance().getParam(Constants.OPEN_DESTINACIJA);

        Komercijalista komercijalista = (Komercijalista) Coordinator.getInstance().getParam(Constants.LOGGED_USER);
        frmAranzmanMain.getLblNaziv().setText(destinacija.getMesto());
        frmAranzmanMain.getLblOpis().setText(destinacija.getOpis());
        frmAranzmanMain.getLblDestinacija().setText(String.valueOf(destinacija.getDestinacijaID()));
        frmAranzmanMain.getLblUkupan().setText(String.valueOf(destinacija.getUkupanKapacitet()));
        frmAranzmanMain.getLblPopunjen().setText(String.valueOf(destinacija.getPopunjenKapacitet()));
        frmAranzmanMain.getLblKomercijalista().setText(komercijalista.toString());
        frmAranzmanMain.setLocationRelativeTo(null);
    }

    private void prepareTable() {
        //Destinacija destinacija = (Destinacija) Coordinator.getInstance().getParam(Constants.OPEN_DESTINACIJA);
        TableModelAranzman tma = new TableModelAranzman(destinacija);
        frmAranzmanMain.getTblAranzmani().setModel(tma);
        frmAranzmanMain.setColumnWidth();
    }

}
