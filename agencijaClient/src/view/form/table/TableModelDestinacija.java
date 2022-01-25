/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.form.table;

import domain.Destinacija;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import view.constants.Constants;
import view.coordinator.Coordinator;

/**
 *
 * @author Gogic
 */
public class TableModelDestinacija extends AbstractTableModel {

    ArrayList<Destinacija> listDestinacija = new ArrayList<>();

    String[] columnNames = {"Naziv", "Opis", "Ukupan kapacitet", "Popunjen kapacitet"};

    @Override
    public int getRowCount() {
        if (!listDestinacija.isEmpty() && listDestinacija != null) {
            return listDestinacija.size();
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
        Destinacija destinacija = listDestinacija.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return destinacija.getMesto();
            case 1:
                return destinacija.getOpis();
            case 2:
                return destinacija.getUkupanKapacitet();
            case 3:
                return destinacija.getPopunjenKapacitet();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Destinacija destinacija = listDestinacija.get(rowIndex);

        switch (columnIndex) {
            case 0:
                destinacija.setMesto((String) aValue);
                break;
            case 1:
                destinacija.setOpis((String) aValue);
                break;
        }
    }

    public void setListDestinacija(ArrayList<Destinacija> listDestinacija) {
        this.listDestinacija = listDestinacija;
        fireTableDataChanged();
    }

    public ArrayList<Destinacija> getListDestinacija() {
        return listDestinacija;
    }

    public void addDestinacija(Destinacija destinacija) {
        listDestinacija.add(destinacija);
        fireTableRowsInserted(listDestinacija.size() - 1, listDestinacija.size() - 1);
    }

    public Destinacija getDestinacijaAt(int index) {
        return listDestinacija.get(index);
    }

//    @Override
//    public boolean isCellEditable(int rowIndex, int columnIndex) {
//        return (columnIndex == 0) || (columnIndex == 1);
//    }

    public void searchTable(String search) throws Exception {
        ArrayList<Destinacija> allDestinacijaList = (ArrayList<Destinacija>) Coordinator.getInstance().getParam(Constants.LIST_DESTINACIJA);
        if (search.isEmpty()) {
            setListDestinacija(allDestinacijaList);
        } else {
            ArrayList<Destinacija> helpList = new ArrayList<>();
            for (Destinacija destinacija : allDestinacijaList) {
                if (destinacija.getMesto().toLowerCase().startsWith(search.trim().toLowerCase())) {
                    helpList.add(destinacija);
                }
            }
            if (helpList.isEmpty()) {
                throw new Exception("Sistem ne moze da pronadje destinaciju po zadatoj vrednosti!");
            }

            setListDestinacija(helpList);
        }
    }
    
    public void refreshTable(){
        fireTableDataChanged();
    }
    
}
