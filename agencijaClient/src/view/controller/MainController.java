/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.Destinacija;
import domain.Komercijalista;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.constants.Constants;
import view.coordinator.Coordinator;
import view.form.FrmMain;
import view.form.table.TableModelDestinacija;

/**
 *
 * @author Gogic
 */
public class MainController {

    private final FrmMain frmMain;

    public MainController(FrmMain frmMain) {
        this.frmMain = frmMain;

        addActionListeners();
    }

    public FrmMain getFrmMain() {
        return frmMain;
    }

    private void addActionListeners() {
        //btnSearch
        frmMain.addBtnSearchActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTable(e);

            }

            private void searchTable(ActionEvent e) {
                String search = frmMain.getTxtSearchDestinacija().getText().trim();

                try {
                    TableModelDestinacija tmd = (TableModelDestinacija) frmMain.getTblDestinacija().getModel();
                    tmd.searchTable(search);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmMain, ex.getMessage(), "Greska prilikom pretrage", JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        //btnReset
        frmMain.addBtnResetTableActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTable();
            }

            private void resetTable() {
                try {
                    TableModelDestinacija tmd = (TableModelDestinacija) frmMain.getTblDestinacija().getModel();
                    tmd.searchTable("");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmMain, ex.getMessage(), "Greska prilikom pretrage", JOptionPane.ERROR_MESSAGE);

                }
            }
        });

        //btnInsert
        frmMain.addBtnInsertDestinacijaActionListener((ActionEvent e) -> {
            Coordinator.getInstance().openInsertDestinacijaForm();
            ((TableModelDestinacija) frmMain.getTblDestinacija().getModel()).setListDestinacija((ArrayList<Destinacija>) Coordinator.getInstance().getParam(Constants.LIST_DESTINACIJA));
        });

        //btnUpdate
        frmMain.addBtnUpdateDestinacijaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDestinacija();
            }

            private void updateDestinacija() {
                int selectedIndex = frmMain.getTblDestinacija().getSelectedRow();
                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(frmMain, "Niste odabrali nijednu destinaciju!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    TableModelDestinacija tmd = (TableModelDestinacija) frmMain.getTblDestinacija().getModel();
                    Destinacija selectedDestinacija = tmd.getDestinacijaAt(selectedIndex);
                    Coordinator.getInstance().addParameter(Constants.UPDATE_DESTINACIJA, selectedDestinacija);
                    Coordinator.getInstance().openUpdateDestinacijaForm();
                    //((TableModelDestinacija) frmMain.getTblDestinacija().getModel()).setListDestinacija((ArrayList<Destinacija>) Coordinator.getInstance().getParam(Constants.LIST_DESTINACIJA));
                    ((TableModelDestinacija) frmMain.getTblDestinacija().getModel()).refreshTable();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frmMain, "Greska prilikom izmene destinacije!\n" + e.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        //btnOtvoriAranzmane
        frmMain.addBtnOpenAranzmanActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = frmMain.getTblDestinacija().getSelectedRow();
                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(frmMain, "Niste odabrali nijednu destinaciju!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    TableModelDestinacija tmd = (TableModelDestinacija) frmMain.getTblDestinacija().getModel();
                    Destinacija selectedDestinacija = tmd.getDestinacijaAt(selectedIndex);
                    //Coordinator.getInstance().addParameter(Constants.OPEN_DESTINACIJA, selectedDestinacija);
                    Coordinator.getInstance().openAranzmanMainForm(selectedDestinacija);

                    updateTable(tmd);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmMain, "Greska prilikom otvaranja aranzmana!\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
                
            }

            private void updateTable(TableModelDestinacija tmd) {

//                ArrayList<Destinacija> list = (ArrayList<Destinacija>) Coordinator.getInstance().getParam(Constants.LIST_DESTINACIJA);
//                tmd.setListDestinacija(list);
                tmd.refreshTable();
            }
        });
    }

    public void openForm() {

        Komercijalista user = (Komercijalista) Coordinator.getInstance().getParam(Constants.LOGGED_USER);
        frmMain.setVisible(true);
        frmMain.setExtendedState(Frame.MAXIMIZED_BOTH);
        prepareForm(user);
        prepareTable();
    }

    private void prepareTable() {
        TableModelDestinacija tmd = new TableModelDestinacija();
        frmMain.getTblDestinacija().setModel(tmd);
        frmMain.setColumnWidth();
        try {
            ArrayList<Destinacija> listDestinacija = Communication.getInstance().getAllDestinacija();
            Coordinator.getInstance().addParameter(Constants.LIST_DESTINACIJA, listDestinacija);
            tmd.setListDestinacija(listDestinacija);
        } catch (Exception ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }

    private void prepareForm(Komercijalista user) {
        frmMain.getLblKomercijalista().setText("Komercijalista: " + user.toString());
        //frmMain.getLblBrojUgovora().setText("Broj sklopljenih ugovora: " + user.getBrojSklopljenihUgovora());
    }

}
