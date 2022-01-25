/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gogic
 */
public class Komercijalista implements GenericObject{

    private int komercijalistaID=-1;
    private String ime;
    private String prezime;
    private String username;
    private String password;

    public Komercijalista() {
    }

    public Komercijalista(int komercijalistaID, String ime, String prezime, String username, String password) {
        this.komercijalistaID = komercijalistaID;
        this.ime = ime;
        this.prezime = prezime;
        this.username = username;
        this.password = password;
    }

    

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Komercijalista other = (Komercijalista) obj;
        if (this.komercijalistaID != other.komercijalistaID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ime+" "+prezime;
    }
    

    public int getKomercijalistaID() {
        return komercijalistaID;
    }

    public void setKomercijalistaID(int komercijalistaID) {
        this.komercijalistaID = komercijalistaID;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


     @Override
    public String getTableName() {
        return "komercijalista";
    }

    @Override
    public String getColumnNamesForInsert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getInsertValues() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setID(int ID) {
        komercijalistaID = ID;
    }

    @Override
    public GenericObject getObject(ResultSet rs) throws SQLException {
        if(rs.next()){
            return new Komercijalista(
                    rs.getInt("komercijalistaid"), 
                    rs.getString("ime"), 
                    rs.getString("prezime"),  
                    rs.getString("username"), 
                    rs.getString("password"));
        }
        
        throw new SQLException("Neispravni podaci za login");
    }

    @Override
    public String getWhereCase() {
        //return "username = '"+this.getUsername()+"' AND "+"password = '"+this.getPassword()+"'";
        return "(username = '"+this.getUsername()+"' AND "+"password = '"+this.getPassword()+"') OR komercijalistaid = "+this.getKomercijalistaID();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
