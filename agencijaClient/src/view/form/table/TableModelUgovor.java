/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.form.table;

import domain.StavkaUgovora;
import domain.Ugovor;
import java.math.BigDecimal;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Gogic
 */
public class TableModelUgovor extends AbstractTableModel {

    private final Ugovor ugovor;

    private final String[] columnNames = {"RB ", "Ime i Prezime klijenta", "JMBG", "Cena"};
    

    public TableModelUgovor(Ugovor ugovor) {
        this.ugovor = ugovor;
    }

    
    @Override
    public int getRowCount() {
        if (!ugovor.getListaStavki().isEmpty() && ugovor.getListaStavki()!=null &&ugovor != null) {
            return ugovor.getListaStavki().size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaUgovora stavka = ugovor.getListaStavki().get(rowIndex);

        switch (columnIndex) {
            case 0:
                return stavka.getStavkaRB();
            case 1:
                return stavka.getImePrezimeKlijenta();
            case 2:
                return stavka.getJMBG();
            case 3:
                return stavka.getCena();
            
            default:
                return null;
        }
    }

    public void addStavkaUgovor(String imePrezime, String JMBG, BigDecimal cena ) {
        StavkaUgovora stavka = new StavkaUgovora();
        stavka.setUgovor(ugovor);
        stavka.setStavkaRB(ugovor.getListaStavki().size()+1);
        stavka.setImePrezimeKlijenta(imePrezime);
        stavka.setJMBG(JMBG);
        stavka.setCena(cena);
        
        ugovor.getListaStavki().add(stavka);
        ugovor.refreshUgovor();
        fireTableRowsInserted(ugovor.getListaStavki().size() - 1, ugovor.getListaStavki().size() - 1);
    }

   
    public void removeStavkaUgovor(int rowIndex){
        
        ugovor.getListaStavki().remove(rowIndex);
        ugovor.refreshUgovor();
        setStavkaOrder();
        fireTableRowsDeleted(ugovor.getListaStavki().size() - 1, ugovor.getListaStavki().size() - 1);
    }

    private void setStavkaOrder() {
        int orderNo = 0;
        for (StavkaUgovora stavka : ugovor.getListaStavki()) {
            stavka.setStavkaRB(++orderNo);
        }
        fireTableDataChanged();
    }

    public Ugovor getUgovor() {
        return ugovor;
    }
    
    
}
