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
import java.util.Objects;

/**
 *
 * @author Gogic
 */
public class Klijent implements GenericObject {

    private int klijentid;
    private String jmbg;
    private String imeprezime;

    public Klijent() {
    }

    public Klijent(int klijentid, String jmbg, String imeprezime) {
        this.klijentid = klijentid;
        this.jmbg = jmbg;
        this.imeprezime = imeprezime;
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
        final Klijent other = (Klijent) obj;
        if (!Objects.equals(this.jmbg, other.jmbg)) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String getTableName() {
        return "klijent";
    }

    @Override
    public String getColumnNamesForInsert() {
        return "jmbg, imeprezime";
    }

    @Override
    public String getInsertValues() {
        StringBuilder sb = new StringBuilder();
        sb.append("'").append(jmbg).append("', ")
          .append("'").append(imeprezime).append("'");
        return sb.toString();
    }

    @Override
    public void setID(int ID) {
        klijentid = ID;
    }

    @Override
    public GenericObject getObject(ResultSet rs) throws SQLException {
        if (rs.next()){
            return new Klijent(rs.getInt("klijentid"), rs.getString("jmbg"), rs.getString("imeprezime"));
        }
        throw new SQLException("Klijent nije pronadjen!");
    }

    @Override
    public String getWhereCase() {
        return "jmbg = '"+jmbg+"'";
    }

    @Override
    public String getUpdateValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<GenericObject> getList(ResultSet rs) throws SQLException {
        List<GenericObject> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new Klijent(rs.getInt("klijentid"), rs.getString("jmbg"), rs.getString("imeprezime")));
        }

        return list;
    }

    @Override
    public Integer getId() {
        return klijentid;
    }

    public int getKlijentid() {
        return klijentid;
    }

    public void setKlijentid(int klijentid) {
        this.klijentid = klijentid;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getImeprezime() {
        return imeprezime;
    }

    public void setImeprezime(String imeprezime) {
        this.imeprezime = imeprezime;
    }

}
