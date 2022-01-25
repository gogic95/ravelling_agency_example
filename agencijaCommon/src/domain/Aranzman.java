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
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Gogic
 */
public class Aranzman implements GenericObject{

    private Destinacija destinacija;
    private int aranzmanRB=-1;
    private String nazivSmestaja;
    private BigDecimal cenaPoKrevetu;
    private int brojKreveta;
    private boolean podUgovorom;
    private Komercijalista komercijalista;

    public Aranzman() {
    }

    public Aranzman(Destinacija destinacija, int aranzmanRB, String nazivSmestaja, BigDecimal cenaPoKrevetu, int brojKreveta, boolean podUgovorom, Komercijalista komercijalista) {
        this.destinacija = destinacija;
        this.aranzmanRB = aranzmanRB;
        this.nazivSmestaja = nazivSmestaja;
        this.cenaPoKrevetu = cenaPoKrevetu;
        this.brojKreveta = brojKreveta;
        this.podUgovorom = podUgovorom;
        this.komercijalista = komercijalista;
    }
    
    
    
    @Override
    public String getTableName() {
        return "aranzman";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "destinacijaid, nazivsmestaja, cenapokrevetu, brojkreveta, podugovorom, komercijalistaid";
    }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        sb.append(destinacija.getDestinacijaID()).append(",")
                .append("'").append(nazivSmestaja).append("',")
                .append(cenaPoKrevetu).append(",")
                .append(brojKreveta).append(",")
                .append(podUgovorom).append(",")
                .append(komercijalista.getKomercijalistaID());
        return sb.toString();
    }

    @Override
    public void setID(int ID) {
        aranzmanRB = ID;
    }

    public Komercijalista getKomercijalista() {
        return komercijalista;
    }

    public void setKomercijalista(Komercijalista komercijalista) {
        this.komercijalista = komercijalista;
    }

    public boolean isPodUgovorom() {
        return podUgovorom;
    }

    public void setPodUgovorom(boolean podUgovorom) {
        this.podUgovorom = podUgovorom;
    }

    public int getBrojKreveta() {
        return brojKreveta;
    }

    public void setBrojKreveta(int brojKreveta) {
        this.brojKreveta = brojKreveta;
    }

    public BigDecimal getCenaPoKrevetu() {
        return cenaPoKrevetu;
    }

    public void setCenaPoKrevetu(BigDecimal cenaPoKrevetu) {
        this.cenaPoKrevetu = cenaPoKrevetu;
    }

    public String getNazivSmestaja() {
        return nazivSmestaja;
    }

    public void setNazivSmestaja(String nazivSmestaja) {
        this.nazivSmestaja = nazivSmestaja;
    }

    public int getAranzmanRB() {
        return aranzmanRB;
    }

    public void setAranzmanRB(int aranzmanRB) {
        this.aranzmanRB = aranzmanRB;
    }

    public Destinacija getDestinacija() {
        return destinacija;
    }

    public void setDestinacija(Destinacija destinacija) {
        this.destinacija = destinacija;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Aranzman other = (Aranzman) obj;
        if (this.aranzmanRB != other.aranzmanRB) {
            return false;
        }
        if (!Objects.equals(this.destinacija, other.destinacija)) {
            return false;
        }
        return true;
    }

    @Override
    public GenericObject getObject(ResultSet rs) throws SQLException {
        if (rs.next()) {
            Komercijalista komercijalistaa = new Komercijalista();
            komercijalistaa.setKomercijalistaID(rs.getInt("komercijalistaid"));
            
            Destinacija destinacijaa = new Destinacija();
            destinacijaa.setDestinacijaID(rs.getInt("destinacijaid"));
            
            Aranzman aranzmanA = new Aranzman();
            aranzmanA.setKomercijalista(komercijalista);
            aranzmanA.setDestinacija(destinacija);
            aranzmanA.setAranzmanRB(rs.getInt("rbaranzmana"));
            aranzmanA.setBrojKreveta(rs.getInt("brojkreveta"));
            aranzmanA.setCenaPoKrevetu(rs.getBigDecimal("cenapokrevetu"));
            aranzmanA.setNazivSmestaja(rs.getString("nazivsmestaja"));
            aranzmanA.setPodUgovorom(rs.getBoolean("podugovorom"));
            
            return aranzmanA;
        }

        throw new SQLException("Greska kod aranzmana.");
    }

    @Override
    public String getWhereCase() {
        return "destinacijaid = "+this.getDestinacija().getDestinacijaID()+" AND "+"rbaranzmana = "+this.getAranzmanRB();
    }

    @Override
    public String getUpdateValues() {
        return new StringBuilder()
                .append("nazivsmestaja = '").append(this.getNazivSmestaja()).append("', ")
                .append("cenapokrevetu = ").append(this.getCenaPoKrevetu()).append(", ")
                .append("brojkreveta = ").append(this.getBrojKreveta()).append(", ")
                .append("podugovorom = ").append(this.isPodUgovorom())
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
            list.add(new Aranzman(
                    new Destinacija(rs.getInt("destinacijaid"), "", "", null), 
                    rs.getInt("rbaranzmana"),
                    rs.getString("nazivsmestaja"), 
                    rs.getBigDecimal("cenapokrevetu"), 
                    rs.getInt("brojkreveta"), 
                    rs.getBoolean("podugovorom"), 
                    new Komercijalista(rs.getInt("komercijalistaid"), "", "", "", "")));
        }

        return list;
    }
    
    
}
