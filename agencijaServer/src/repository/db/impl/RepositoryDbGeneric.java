/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db.impl;

import domain.GenericObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import repository.db.DbConnectionFactory;
import repository.db.DbRepository;

/**
 *
 * @author Gogic
 */
public class RepositoryDbGeneric implements DbRepository<GenericObject> {

    @Override
    public GenericObject insert(GenericObject parameter) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ")
                    .append(parameter.getTableName())
                    .append("(").append(parameter.getColumnNamesForInsert()).append(")")
                    .append(" VALUES ")
                    .append("(").append(parameter.getInsertValues()).append(")");

            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);

            ResultSet rs = statement.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                parameter.setID(id);
            }

            statement.close();
            rs.close();
            return parameter;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public GenericObject update(GenericObject parameter) throws Exception {
        try {
            Connection connection = DbConnectionFactory.getInstance().getConnection();

            String query = new StringBuilder()
                    .append("UPDATE ")
                    .append(parameter.getTableName())
                    .append(" SET ")
                    .append(parameter.getUpdateValues())
                    .append(" WHERE ")
                    .append(parameter.getWhereCase())
                    .toString();

            Statement statement = connection.createStatement();
            System.out.println(query);
            statement.executeUpdate(query);

            statement.close();
            return parameter;
        } catch (SQLException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public boolean delete(GenericObject parameter) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        
        String query = new StringBuilder()
                .append("DELETE FROM ")
                .append(parameter.getTableName())
                .append(" WHERE ")
                .append(parameter.getWhereCase())
                .toString();
        
        System.out.println(query);
        if(connection.createStatement().executeUpdate(query) > 0)
            return true;
        throw new Exception(parameter.getTableName() + " ne moze biti obrisan");
    }

    @Override
    public List<GenericObject> getAll(GenericObject parameter) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();
        
        String query = "SELECT * FROM " + parameter.getTableName();
        Statement s = connection.createStatement();
        System.out.println(query);
        return parameter.getList(s.executeQuery(query));
    }

    @Override
    public GenericObject get(GenericObject parameter) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();

        String query = new StringBuilder()
                .append("SELECT * FROM ")
                .append(parameter.getTableName())
                .append(" WHERE ")
                .append(parameter.getWhereCase())
                .toString();
        
        System.out.println(query);
        Statement s = connection.createStatement();

        return parameter.getObject(s.executeQuery(query));
    }

    @Override
    public List<GenericObject> getBy(GenericObject parameter, String column, String value) throws Exception {
        Connection connection = DbConnectionFactory.getInstance().getConnection();

        String query = new StringBuilder()
                .append("SELECT * FROM ")
                .append(parameter.getTableName())
                .append(" WHERE ").append(column).append(" = '").append(value).append("'")
                .toString();
        
        System.out.println(query);
        Statement s = connection.createStatement();

        return parameter.getList(s.executeQuery(query));
    }

}
