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
public class StavkaUgovora implements GenericObject {

    private Ugovor ugovor;
    private int stavkaRB;
    private String imePrezimeKlijenta;
    private String JMBG;
    private BigDecimal cena;

    public StavkaUgovora() {
    }

    public StavkaUgovora(Ugovor ugovor, int stavkaRB, String imePrezimeKlijenta, String JMBG, BigDecimal cena) {
        this.ugovor = ugovor;
        this.stavkaRB = stavkaRB;
        this.imePrezimeKlijenta = imePrezimeKlijenta;
        this.JMBG = JMBG;
        this.cena = cena;
    }

   

    public Ugovor getUgovor() {
        return ugovor;
    }

    public void setUgovor(Ugovor ugovor) {
        this.ugovor = ugovor;
    }

    public int getStavkaRB() {
        return stavkaRB;
    }

    public void setStavkaRB(int stavkaRB) {
        this.stavkaRB = stavkaRB;
    }

    public String getImePrezimeKlijenta() {
        return imePrezimeKlijenta;
    }

    public void setImePrezimeKlijenta(String imePrezimeKlijenta) {
        this.imePrezimeKlijenta = imePrezimeKlijenta;
    }

    public String getJMBG() {
        return JMBG;
    }

    public void setJMBG(String JMBG) {
        this.JMBG = JMBG;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
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
        final StavkaUgovora other = (StavkaUgovora) obj;
        if (this.stavkaRB != other.stavkaRB) {
            return false;
        }
        if (!Objects.equals(this.ugovor, other.ugovor)) {
            return false;
        }
        return true;
    }
    
     @Override
    public String getTableName() {
        return "stavkaugovora";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "brojugovora, rbstavke, imeprezimeklijenta, jmbg, cena";
    }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getUgovor().getBrojUgovora()).append(",")
                .append(getStavkaRB()).append(",")
                .append(" '").append(getImePrezimeKlijenta()).append("',")
                .append(" '").append(getJMBG()).append("',")
                .append(getCena());
        return sb.toString();
    }

    @Override
    public void setID(int ID) {

    }

    @Override
    public GenericObject getObject(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getWhereCase() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUpdateValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<GenericObject> getList(ResultSet rs) throws SQLException {
        List<GenericObject> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new StavkaUgovora(
                    new Ugovor(),
                    rs.getInt("rbstavke"),
                    rs.getString("imeprezimeklijenta"),
                    rs.getString("jmbg"),
                    rs.getBigDecimal("cena")
            ));
        }

        return list;
    }

}
