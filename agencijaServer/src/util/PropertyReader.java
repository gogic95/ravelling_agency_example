/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import controller.Controller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gogic
 */
public class PropertyReader {

        public static void read(){
            try {
                Properties prop = new Properties();
                
                //prop.load(new FileInputStream("src\\util\\dbconfig.properties"));
                prop.load(new FileInputStream("src/util/dbconfig.properties"));
                
                
                Controller.getInstance().addParameter("db.username", prop.getProperty("db.username"));
                Controller.getInstance().addParameter("db.password", prop.getProperty("db.password"));
                Controller.getInstance().addParameter("db.url", prop.getProperty("db.url"));
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PropertyReader.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PropertyReader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
}
