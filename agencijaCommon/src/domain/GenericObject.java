/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Gogic
 */
public interface GenericObject extends Serializable{
    
    public String getTableName();
    public String getColumnNamesForInsert();
    public String getInsertValues();
    public void setID(int ID);
    
    public GenericObject getObject(ResultSet rs) throws SQLException;
    public String getWhereCase();
    public String getUpdateValues();
    
    public List<GenericObject> getList(ResultSet rs) throws SQLException;
    
    public Integer getId();
}
