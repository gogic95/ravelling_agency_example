/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gogic
 */
public class Destinacija implements GenericObject {

    private int destinacijaID;
    private String mesto;
    private String opis;
    private int ukupanKapacitet;
    private int popunjenKapacitet;
    private ArrayList<Aranzman> listaAranzmana = new ArrayList<>();
    private Komercijalista komercijalista;

    public Destinacija() {
    }

    public Destinacija(int destinacijaID, String mesto, String opis, Komercijalista komercijalista) {
        this.destinacijaID = destinacijaID;
        this.mesto = mesto;
        this.opis = opis;

        this.listaAranzmana = new ArrayList<>();

        this.komercijalista = komercijalista;
        this.ukupanKapacitet = 0;
        this.popunjenKapacitet = 0;
    }

    public Komercijalista getKomercijalista() {
        return komercijalista;
    }

    public void setKomercijalista(Komercijalista komercijalista) {
        this.komercijalista = komercijalista;
    }

    public ArrayList<Aranzman> getListaAranzmana() {
        return listaAranzmana;
    }

    public void setListaAranzmana(ArrayList<Aranzman> listaAranzmana) {
        this.listaAranzmana = listaAranzmana;
        setUkupanKapacitet();
        setPopunjenKapacitet();
    }

    public int getPopunjenKapacitet() {
        return popunjenKapacitet;
    }

    public void setPopunjenKapacitet() {
        int kapacitet = 0;
        if (!listaAranzmana.isEmpty() && listaAranzmana != null) {
            for (Aranzman aranzman : listaAranzmana) {
                if (aranzman.isPodUgovorom()) {
                    kapacitet += aranzman.getBrojKreveta();
                }
            }
        }
        this.popunjenKapacitet = kapacitet;
    }

    public int getUkupanKapacitet() {
        return ukupanKapacitet;
    }

    public void setUkupanKapacitet() {
        int kapacitet = 0;
        if (!listaAranzmana.isEmpty() && listaAranzmana != null) {
            for (Aranzman aranzman : listaAranzmana) {
                kapacitet += aranzman.getBrojKreveta();
            }

        }
        this.ukupanKapacitet = kapacitet;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public int getDestinacijaID() {
        return destinacijaID;
    }

    public void setDestinacijaID(int destinacijaID) {
        this.destinacijaID = destinacijaID;
    }

    @Override
    public String toString() {
        return mesto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Destinacija other = (Destinacija) obj;
        if (this.destinacijaID != other.destinacijaID) {
            return false;
        }
        return true;
    }

    @Override
    public String getTableName() {
        return "destinacija";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "mesto,opis,komercijalistaid";
    }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("'").append(mesto).append("',")
                .append("'").append(opis).append("',")
                .append(komercijalista.getKomercijalistaID());
        return sb.toString();
    }

    @Override
    public void setID(int ID) {
        destinacijaID = ID;
    }

    @Override
    public GenericObject getObject(ResultSet rs) throws SQLException {
        if (rs.next()) {
            setKomercijalista(new Komercijalista());
            komercijalista.setKomercijalistaID(rs.getInt("komercijalistaid"));

            Destinacija generatedDestinacija = new Destinacija(rs.getInt("destinacijaid"), rs.getString("mesto"), rs.getString("opis"), komercijalista);
            return generatedDestinacija;
        }

        throw new SQLException("Greska kod destinacija.");
    }

    @Override
    public String getWhereCase() {
        return "destinacijaid = " + this.getDestinacijaID();
    }

    @Override
    public String getUpdateValues() {
        return new StringBuilder()
                .append("mesto = '").append(this.getMesto()).append("', ")
                .append("opis = '").append(this.getOpis()).append("'")
                .toString();
    }

    @Override
    public Integer getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<GenericObject> getList(ResultSet rs) throws SQLException {
        List<GenericObject> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Destinacija(rs.getInt("destinacijaid"), rs.getString("mesto"), rs.getString("opis"),
                    new Komercijalista(rs.getInt("komercijalistaid"), "", "", "", "")));
        }

        return list;
    }

    public void refreshDestinacija() {
        setUkupanKapacitet();
        setPopunjenKapacitet();
    }
}
