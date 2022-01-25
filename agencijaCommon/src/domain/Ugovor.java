/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Gogic
 */
public class Ugovor implements GenericObject {

    private int brojUgovora = -1;
    private int brojKlijenata = 0;
    private BigDecimal ukupnaCena = new BigDecimal(0);
    private Boolean obradjen = false;
    private Boolean storniran = false;
    private ArrayList<StavkaUgovora> listaStavki = new ArrayList<>();
    private Komercijalista komercijalista;
    private Aranzman aranzman = new Aranzman();
    private Date datumsklapanja = new Date();

    public Ugovor() {
    }

    public Ugovor(int brojUgovora, int brojKlijenata, BigDecimal ukupnaCena, Boolean obradjen, Boolean storniran, ArrayList<StavkaUgovora> listaStavki, Komercijalista komercijalista, Aranzman aranzman, Date datumsklapanja) {
        this.brojUgovora = brojUgovora;
        this.brojKlijenata = brojKlijenata;
        this.ukupnaCena = ukupnaCena;
        this.obradjen = obradjen;
        this.storniran = storniran;
        this.listaStavki = listaStavki;
        this.komercijalista = komercijalista;
        this.aranzman = aranzman;
        this.datumsklapanja = datumsklapanja;
    }

    public int getBrojUgovora() {
        return brojUgovora;
    }

    public void setBrojUgovora(int brojUgovora) {
        this.brojUgovora = brojUgovora;
    }

    public int getBrojKlijenata() {
        return brojKlijenata;
    }

    public void setBrojKlijenata() {
        int broj = 0;
        if (!listaStavki.isEmpty() && listaStavki != null) {
            broj = listaStavki.size();
        }
        this.brojKlijenata = broj;
    }

    public BigDecimal getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena() {
        BigDecimal cena = new BigDecimal(0);
        if (!listaStavki.isEmpty()) {
            for (StavkaUgovora stavkaUgovora : listaStavki) {
                cena = cena.add(stavkaUgovora.getCena());
            }
        }
        this.ukupnaCena = cena;
    }

    public Boolean getObradjen() {
        return obradjen;
    }

    public void setObradjen(Boolean obradjen) {
        this.obradjen = obradjen;
    }

    public Boolean getStorniran() {
        return storniran;
    }

    public void setStorniran(Boolean storniran) {
        this.storniran = storniran;
    }

    public ArrayList<StavkaUgovora> getListaStavki() {
        return listaStavki;
    }

    public void setListaStavki(ArrayList<StavkaUgovora> listaStavki) {
        this.listaStavki = listaStavki;
        setUkupnaCena();
        setBrojKlijenata();
    }

    public Komercijalista getKomercijalista() {
        return komercijalista;
    }

    public void setKomercijalista(Komercijalista komercijalista) {
        this.komercijalista = komercijalista;
    }

    public Aranzman getAranzman() {
        return aranzman;
    }

    public void setAranzman(Aranzman aranzman) {
        this.aranzman = aranzman;
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
        final Ugovor other = (Ugovor) obj;
        if (this.brojUgovora != other.brojUgovora) {
            return false;
        }
        return true;
    }

    @Override
    public String getTableName() {
        return "ugovor";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "brojklijenata, ukupnacena, obradjen, storniran, destinacijaid, rbaranzmana, komercijalistaid, datumsklapanjaugovora";
    }

    @Override
    public String getInsertValues() {
        java.sql.Date datumSQL = new java.sql.Date(datumsklapanja.getTime());
        StringBuilder sb = new StringBuilder();
        sb.append(brojKlijenata).append(",")
                .append(ukupnaCena).append(",")
                .append(obradjen).append(",")
                .append(storniran).append(",")
                .append(aranzman.getDestinacija().getDestinacijaID()).append(",")
                .append(aranzman.getAranzmanRB()).append(",")
                .append(komercijalista.getKomercijalistaID()).append(", ")
                .append("'").append(datumSQL).append("'");
        return sb.toString();
    }

    @Override
    public void setID(int ID) {
        brojUgovora = ID;
    }

    @Override
    public GenericObject getObject(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Komercijalista komercijalistaa = new Komercijalista();
            komercijalistaa.setKomercijalistaID(rs.getInt("komercijalistaid"));
            Destinacija destinacijaa = new Destinacija();
            destinacijaa.setDestinacijaID(rs.getInt("destinacijaid"));
            Aranzman aranzmanA = new Aranzman(destinacijaa, rs.getInt("rbaranzmana"), "", new BigDecimal(0), 0, true, komercijalistaa);
            return new Ugovor(
                    rs.getInt("brojugovora"),
                    rs.getInt("brojklijenata"),
                    rs.getBigDecimal("ukupnacena"),
                    rs.getBoolean("obradjen"),
                    rs.getBoolean("storniran"),
                    null,
                    komercijalistaa,
                    aranzmanA,
                    new Date(rs.getDate("datumsklapanjaugovora").getTime()));
        }

        throw new SQLException("Ugovor nije pronadjen ili je storniran!");
    }

    @Override
    public String getWhereCase() {
        return "storniran = 0 AND (brojugovora = " + getBrojUgovora() + " OR rbaranzmana = " + getAranzman().getAranzmanRB() + ")";
    }

    @Override
    public String getUpdateValues() {
        return "storniran = " + getStorniran();
    }

    @Override
    public Integer getId() {
        return brojUgovora;
    }

    @Override
    public List<GenericObject> getList(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void refreshUgovor() {
        setUkupnaCena();
        setBrojKlijenata();
    }

    public Date getDatumsklapanja() {
        return datumsklapanja;
    }

    public void setDatumsklapanja(Date datumsklapanja) {
        this.datumsklapanja = datumsklapanja;
    }

}
