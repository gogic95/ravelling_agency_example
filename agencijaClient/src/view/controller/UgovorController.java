/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controller;

import communication.Communication;
import domain.Aranzman;
import domain.Klijent;
import domain.StavkaUgovora;
import domain.Ugovor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.form.FrmUgovor;
import view.form.mode.FormMode;
import view.form.table.TableModelUgovor;

/**
 *
 * @author Gogic
 */
public class UgovorController {

    private final FrmUgovor frmUgovor;
    private Ugovor ugovor;
    private Aranzman aranzman;

    public UgovorController(FrmUgovor frmUgovor) {
        this.frmUgovor = frmUgovor;
    }

    //controller za Add i View
    public UgovorController(FrmUgovor frmUgovor, Aranzman aranzman) {
        this.frmUgovor = frmUgovor;
        this.aranzman = aranzman;
        ugovor = new Ugovor();
        ugovor.setKomercijalista(aranzman.getKomercijalista());
        ugovor.setAranzman(aranzman);
        ugovor.setDatumsklapanja(new Date());
    }

    //controller za Pretrazi
    public UgovorController(FrmUgovor frmUgovor, Ugovor ugovor) {
        this.frmUgovor = frmUgovor;
        this.aranzman = ugovor.getAranzman();
        this.ugovor = ugovor;

    }

    public void openForm(FormMode formMode) {
        try {
            setComponents(formMode);
            prepareTable();

            addActionListeners();

            frmUgovor.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmUgovor, "Greska prilikom pronalazenja ugovora.\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(UgovorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setComponents(FormMode formMode) throws Exception {
        switch (formMode) {
            case FORM_ADD:
                frmUgovor.getBtnObradi().setEnabled(true);
                frmUgovor.getBtnStorniraj().setEnabled(false);
                frmUgovor.getBtnUnesiKlijenta().setEnabled(true);
                frmUgovor.getBtnObrisiKlijenta().setEnabled(true);
                fillLabelsForAdd();

                break;

            case FORM_VIEW:
                frmUgovor.getBtnObradi().setEnabled(false);
                frmUgovor.getBtnStorniraj().setEnabled(true);
                frmUgovor.getBtnUnesiKlijenta().setEnabled(false);
                frmUgovor.getBtnObrisiKlijenta().setEnabled(false);
                frmUgovor.getTxtCena().setEnabled(false);
                frmUgovor.getTxtImeIPrezimeKlijenta().setEnabled(false);
                frmUgovor.getTxtJMBG().setEnabled(false);
                if (ugovor.getBrojUgovora() == -1) {
                    getUgovorByAranzmanID();
                }
                fillLabelsForView();

                break;
        }
    }

    private void fillLabelsForAdd() {
        frmUgovor.getLblDestinacija().setText(aranzman.getDestinacija().getMesto());
        frmUgovor.getLblSmestaj().setText(aranzman.getNazivSmestaja());
        frmUgovor.getLblBrojKreveta().setText(String.valueOf(aranzman.getBrojKreveta()));
        frmUgovor.getLblKomercijalista().setText(aranzman.getKomercijalista().toString());
        frmUgovor.getLblBrojUgovora().setText("Broj ugovora će biti kreiran nakon obrade");
        frmUgovor.getLblBrojKlijenata().setText(String.valueOf(0));
        frmUgovor.getLblUkupnaCena().setText("0");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
        frmUgovor.getLblDatum().setText(sdf.format(ugovor.getDatumsklapanja()));
    }

    private void fillLabelsForView() {
        frmUgovor.getLblDestinacija().setText(aranzman.getDestinacija().getMesto());
        frmUgovor.getLblSmestaj().setText(aranzman.getNazivSmestaja());
        frmUgovor.getLblBrojKreveta().setText(String.valueOf(aranzman.getBrojKreveta()));
        frmUgovor.getLblKomercijalista().setText(aranzman.getKomercijalista().toString());
        frmUgovor.getLblBrojUgovora().setText(String.valueOf(ugovor.getBrojUgovora()));
        frmUgovor.getLblBrojKlijenata().setText(String.valueOf(ugovor.getBrojKlijenata()));
        frmUgovor.getLblUkupnaCena().setText(String.valueOf(ugovor.getUkupnaCena()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
        frmUgovor.getLblDatum().setText(sdf.format(ugovor.getDatumsklapanja()));
        frmUgovor.getBtnPretraziUBazi().setEnabled(false);
    }

    private void getUgovorByAranzmanID() throws Exception {

        ugovor = Communication.getInstance().findUgovor(aranzman);

    }

    private void prepareTable() {

        TableModelUgovor tmu = new TableModelUgovor(ugovor);
        frmUgovor.getTblStavkeUgovora().setModel(tmu);
        frmUgovor.setColumnWidth();
    }

    private void addActionListeners() {
        //btnUnesiKlijenta
        frmUgovor.addBtnUnesiKlijentaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imePrezime = frmUgovor.getTxtImeIPrezimeKlijenta().getText().trim();
                String JMBG = frmUgovor.getTxtJMBG().getText().trim();
                String cena = frmUgovor.getTxtCena().getText().trim();
                try {
                    validateFields(imePrezime, JMBG, cena);
                    imePrezime = firstLettersToUpercase(imePrezime);

                    TableModelUgovor tmu = (TableModelUgovor) frmUgovor.getTblStavkeUgovora().getModel();
                    tmu.addStavkaUgovor(imePrezime, JMBG, new BigDecimal(cena));
                    frmUgovor.getLblBrojKlijenata().setText(String.valueOf(ugovor.getBrojKlijenata()));
                    frmUgovor.getTxtImeIPrezimeKlijenta().setText("");
                    frmUgovor.getTxtJMBG().setText("");
                    frmUgovor.getLblUkupnaCena().setText(String.valueOf(ugovor.getUkupnaCena()));
                    if (aranzman.getBrojKreveta() <= ugovor.getListaStavki().size()) {
                        frmUgovor.getBtnUnesiKlijenta().setEnabled(false);
                        frmUgovor.getBtnPretraziUBazi().setEnabled(false);
                        JOptionPane.showMessageDialog(frmUgovor, "Popunjen kapacitet smestaja!\n (Broj kreveta: " + aranzman.getBrojKreveta() + "; Broj klijenata: " + ugovor.getBrojKlijenata() + ")", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmUgovor, "Greska prilikom unosa klijenta.\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void validateFields(String imePrezime, String JMBG, String cena) throws Exception {
                if (imePrezime.isEmpty() || JMBG.isEmpty() || cena.isEmpty()) {
                    throw new Exception("Sva polja moraju biti popunjena!\n");
                }

                if (!imePrezime.contains(" ")) {
                    throw new Exception("Ime i prezime uneti sa razmakom!\n");
                }

                for (int i = 0; i < JMBG.length(); i++) {
                    if (!Character.isDigit(JMBG.charAt(i))) {
                        throw new Exception("JMBG treba da sadrži samo cifre!\n");
                    }
                }

                if (JMBG.length() != 13) {
                    throw new Exception("JMBG mora imati 13 cifara!\n");
                }

                jmbgDetailValidation(JMBG);

                try {
                    BigDecimal bigCena = new BigDecimal(cena);
                    if (bigCena.compareTo(new BigDecimal(0)) < 1) {
                        throw new Exception("Cena mora biti veca od 0!\n");
                    }
                } catch (NumberFormatException e) {
                    throw new Exception("Cena nije u odgovarajucem formatu!\n");
                }

                ArrayList<StavkaUgovora> listaStavki = ugovor.getListaStavki();
                for (StavkaUgovora stavkaUgovora : listaStavki) {
                    if (stavkaUgovora.getJMBG().equals(JMBG)) {
                        throw new Exception("Vec ste uneli korisnika sa tim JMBG-om!\n");
                    }
                }

            }

            private String firstLettersToUpercase(String imePrezime) {
                String[] arr = imePrezime.split(" ");
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < arr.length; i++) {
                    sb.append(arr[i].substring(0, 1).toUpperCase())
                            .append(arr[i].substring(1))
                            .append(" ");
                }
                return sb.toString().trim();
            }

            private void jmbgDetailValidation(String JMBG) throws Exception {
                int mesec = Integer.parseInt(JMBG.substring(2, 4));
                int dan = Integer.parseInt(JMBG.substring(0, 2));
                int godina = 0;

                String godinaS = JMBG.substring(4, 7);
                switch (godinaS.charAt(0)) {
                    case '9':
                        godina = 1000 + Integer.parseInt(godinaS);
                        break;
                    case '0':
                        godina = 2000 + Integer.parseInt(godinaS);
                        break;
                    default:
                        throw new Exception("Neispravna godina rodjenja!");  // 1900 - 2021
                }
                
                
                if (mesec < 1 || mesec > 12) {
                    throw new Exception("Neispravan mesec rodjenja! (01-12)");
                }

                if (mesec == 1 || mesec == 3 || mesec == 5 || mesec == 7 || mesec == 8 || mesec == 10 || mesec == 12) {
                    if (dan < 1 || dan > 31) {
                        throw new Exception("Neispravan dan rodjenja! (01-31)");
                    }
                }

                if (mesec == 4 || mesec == 6 || mesec == 9 || mesec == 11) {
                    if (dan < 1 || dan > 30) {
                        throw new Exception("Neispravan dan rodjenja! (01-30)");
                    }
                }

                if (mesec == 2) {
                    if (((godina % 4) == 0) && (((godina % 100) != 0) || ((godina % 400) == 0))) {
                        if (dan < 1 || dan > 29) {
                            throw new Exception("Neispravan dan rodjenja! (01-29)");
                        }
                    } else {
                        if (dan < 1 || dan > 28) {
                            throw new Exception("Neispravan dan rodjenja! (01-28)");
                        }
                    }
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Date datumRodjenja = sdf.parse(String.valueOf(dan)+"."+String.valueOf(mesec)+"."+String.valueOf(godina));
                if(datumRodjenja.after(new Date())){
                    throw new Exception("Dete jos nije rodjeno!");
                }
            }
        });

        //btnObrisiKlijenta
        frmUgovor.addBtnObrisiKlijentaActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = frmUgovor.getTblStavkeUgovora().getSelectedRow();
                if (index == -1) {
                    JOptionPane.showMessageDialog(frmUgovor, "Niste odabrali nijednog klijenta!\n", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                TableModelUgovor tmu = (TableModelUgovor) frmUgovor.getTblStavkeUgovora().getModel();
                tmu.removeStavkaUgovor(index);
                if (!frmUgovor.getBtnUnesiKlijenta().isEnabled()) {
                    frmUgovor.getBtnUnesiKlijenta().setEnabled(true);
                    frmUgovor.getBtnPretraziUBazi().setEnabled(true);
                }
                frmUgovor.getLblBrojKlijenata().setText(String.valueOf(ugovor.getBrojKlijenata()));
                frmUgovor.getLblUkupnaCena().setText(String.valueOf(ugovor.getUkupnaCena()));
                JOptionPane.showMessageDialog(frmUgovor, "Uspesno obrisan klijent!\n", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        //btnObradiUgovor
        frmUgovor.addBtnObradiUgovorActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ugovor.getListaStavki().isEmpty()) {
                    JOptionPane.showMessageDialog(frmUgovor, "Sistem ne moze da obradi ugovor!\nNije unet nijedan klijent!\n", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int i = JOptionPane.showConfirmDialog(frmUgovor, "Da li ste sigurni da zelite da obradite ugovor?", "Obrada ugovora", JOptionPane.OK_CANCEL_OPTION);
                if (i == 0) {
                    try {

                        ugovor.setObradjen(true);
                        aranzman.setPodUgovorom(true);
                        ugovor = Communication.getInstance().obradiUgovor(ugovor);

                        JOptionPane.showMessageDialog(frmUgovor, "Uspesno obradjen ugovor!\nBroj ugovora: " + ugovor.getBrojUgovora());
                        frmUgovor.getBtnObradi().setEnabled(false);
                        frmUgovor.getBtnObrisiKlijenta().setEnabled(false);
                        frmUgovor.getBtnUnesiKlijenta().setEnabled(false);
                        frmUgovor.getBtnStorniraj().setEnabled(true);
                        frmUgovor.getTxtCena().setEnabled(false);
                        frmUgovor.getTxtImeIPrezimeKlijenta().setEnabled(false);
                        frmUgovor.getTxtJMBG().setEnabled(false);
                        frmUgovor.getLblBrojUgovora().setText(String.valueOf(ugovor.getBrojUgovora()));
                        frmUgovor.getBtnPretraziUBazi().setEnabled(false);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmUgovor, "Greska prilikom obrade ugovora!\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        //btnStornirajUgovor
        frmUgovor.addBtnStornirajUgovorActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(frmUgovor, "Da li ste sigurni da zelite da stornirate ugovor?", "Storniranje ugovora", JOptionPane.OK_CANCEL_OPTION);
                if (i == 0) {
                    try {
                        ugovor.setStorniran(true);
                        aranzman.setPodUgovorom(false);
                        ugovor.setAranzman(aranzman);
                        Communication.getInstance().stornirajUgovor(ugovor);
                        JOptionPane.showMessageDialog(frmUgovor, "Uspesno storniran ugovor!\nBroj ugovora: " + ugovor.getBrojUgovora());
                        frmUgovor.dispose();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frmUgovor, "Greska prilikom storniranja ugovora!", "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        //btnOdustani
        frmUgovor.addBtnOdustaniActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frmUgovor.dispose();
            }
        });

        //btnPretraziUBazi
        frmUgovor.addBtnPretraziUBaziActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String JMBG = frmUgovor.getTxtJMBG().getText().trim();
                try {
                    validateField(JMBG);

                    Klijent klijent = Communication.getInstance().pretraziKlijente(JMBG);
                    frmUgovor.getTxtImeIPrezimeKlijenta().setText(klijent.getImeprezime());
                    JOptionPane.showMessageDialog(frmUgovor, "Pronadjen klijent: " + klijent.getImeprezime(), "Informacija", JOptionPane.INFORMATION_MESSAGE);
                    frmUgovor.getTxtCena().grabFocus();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmUgovor, "Neuspesna pretraga!\n" + ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            private void validateField(String JMBG) throws Exception {
                if (JMBG.isEmpty()) {
                    throw new Exception("Polje ne moze biti prazno!\n");
                }

                for (int i = 0; i < JMBG.length(); i++) {
                    if (!Character.isDigit(JMBG.charAt(i))) {
                        throw new Exception("JMBG treba da sadrži samo cifre!\n");
                    }
                }

                if (JMBG.length() != 13) {
                    throw new Exception("JMBG mora imati 13 cifara!\n");
                }

                ArrayList<StavkaUgovora> listaStavki = ugovor.getListaStavki();
                for (StavkaUgovora stavkaUgovora : listaStavki) {
                    if (stavkaUgovora.getJMBG().equals(JMBG)) {
                        throw new Exception("Vec ste uneli korisnika sa tim JMBG-om!\n");
                    }
                }
            }
        });
    }

}
