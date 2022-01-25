/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db;

import controller.Controller;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Gogic
 */
public class DbConnectionFactory {
    private Connection connection;
    private static DbConnectionFactory instance;

    private DbConnectionFactory() {
    }

    public static DbConnectionFactory getInstance() {
        if(instance == null){
            instance = new DbConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws Exception {
        if(connection == null || connection.isClosed()){
//            Properties properties = new Properties();
//            properties.load(new FileInputStream("config/dbconfig.properties"));
//            String url = properties.getProperty("url");
//            String username = properties.getProperty("username");
//            String password = properties.getProperty("password");
            
            String url = Controller.getInstance().getParam("db.url").toString();
            String username = Controller.getInstance().getParam("db.username").toString();
            String password = Controller.getInstance().getParam("db.password").toString();
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
            System.out.println("Otvorena konekcija sa bazom");
        }
        return connection;
    }
    
    
}
