/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.form.table;

import domain.Aranzman;
import domain.Destinacija;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


/**
 *
 * @author Gogic
 */
public class TableModelAranzman extends AbstractTableModel {

    Destinacija destinacija;
    ArrayList<Aranzman> listaAranzmana;

    public TableModelAranzman(Destinacija destinacija) {
        this.destinacija = destinacija;
        listaAranzmana = destinacija.getListaAranzmana();
    }

    String[] columnNames = {"RB aranzmana", "Naziv smestaja", "Cena po krevetu", "Broj kreveta", "Pod ugovorom"};
    Class [] columnClass = {Integer.class, String.class, BigDecimal.class, Integer.class, Boolean.class};

    @Override
    public int getRowCount() {
        if (!listaAranzmana.isEmpty() && listaAranzmana != null) {
            return listaAranzmana.size();
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
    public Class<?> getColumnClass(int columnIndex) {
        return columnClass[columnIndex];
    }

    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Aranzman aranzman = listaAranzmana.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return aranzman.getAranzmanRB();
            case 1:
                return aranzman.getNazivSmestaja();
            case 2:
                return aranzman.getCenaPoKrevetu();
            case 3:
                return aranzman.getBrojKreveta();
            case 4:
                return aranzman.isPodUgovorom();
            default:
                return null;
        }
    }

    public void setListaAranzmana(ArrayList<Aranzman> listaAranzmana) {
        this.listaAranzmana = listaAranzmana;
        fireTableDataChanged();
    }

    public ArrayList<Aranzman> getListaAranzmana() {
        return listaAranzmana;
    }

    public void addAranzman(Aranzman aranzman) {
        listaAranzmana.add(aranzman);
        fireTableRowsInserted(listaAranzmana.size() - 1, listaAranzmana.size() - 1);
    }

    public Aranzman getAranzmanAt(int index) {
        return listaAranzmana.get(index);
    }

//    @Override
//    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        return (columnIndex == 0) || (columnIndex == 1);
//    }
    public void searchTable(String search) throws Exception {
        ArrayList<Aranzman> listaAranzmans = destinacija.getListaAranzmana();
        if (search.isEmpty()) {
            setListaAranzmana(destinacija.getListaAranzmana());
        } else {
            ArrayList<Aranzman> helpList = new ArrayList<>();
            for (Aranzman aranzman : listaAranzmans) {
                if (aranzman.getNazivSmestaja().toLowerCase().startsWith(search.trim().toLowerCase())) {
                    helpList.add(aranzman);
                }
            }
            if (helpList.isEmpty()) {
                throw new Exception("Sistem ne moze da pronadje aranzman po zadatoj vrednosti!");
            }
            ;
            setListaAranzmana(helpList);
        }
    }

    public void setDestinacija(Destinacija destinacija) {
        this.destinacija = destinacija;
        fireTableDataChanged();
    }
    
    public void refreshTable(){
        
        fireTableDataChanged();
    }

}
