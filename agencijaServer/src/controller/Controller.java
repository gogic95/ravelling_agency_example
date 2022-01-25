/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import domain.Aranzman;
import domain.Destinacija;
import domain.Klijent;
import domain.Komercijalista;
import domain.Ugovor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import operation.aranzman.ObrisiAranzman;
import operation.aranzman.KreirajAranzman;
import operation.aranzman.ZapamtiAranzman;
import operation.destinacija.UcitajListuDestinacija;
import operation.destinacija.KreirajDestinaciju;
import operation.destinacija.ZapamtiDestinaciju;
import operation.klijent.PretraziKlijente;
import operation.komercijalista.LoginKomercijalista;
import operation.ugovor.GetUgovorByAranzmanID;
import operation.ugovor.PretraziUgovore;
import operation.ugovor.ObradiUgovor;
import operation.ugovor.StornirajUgovor;

/**
 *
 * @author Gogic
 */
public class Controller {

    private static Controller instance;
    //private final Repository repository;

    private final Map<String, Object> parameters;

    private Controller() {
        parameters = new HashMap<>();
        // this.repository = new RepositoryDbGeneric();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;

    }

    public void addParameter(String s, Object o) {
        parameters.put(s, o);
    }

    public Object getParam(String name) {
        return parameters.get(name);
    }

    public Destinacija kreirajDestinaciju(Destinacija destinacija) throws Exception {
        try {
            return (Destinacija) new KreirajDestinaciju().execute(destinacija);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Neuspesan INSERT destinacije!");
        }
    }

    public Aranzman kreirajAranzman(Aranzman aranzman) throws Exception {
        try {
            return (Aranzman) new KreirajAranzman().execute(aranzman);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Neuspesan INSERT aranzmana!");
        }
    }

    public Komercijalista login(Komercijalista user) throws Exception {
        user = (Komercijalista) new LoginKomercijalista().execute(user);
        //addParameter(ServerConstants.LOGGED_USER, user);
        return user;
    }

    public Object ucitajListuDestinacija() throws Exception {
        try {
            return (ArrayList<Destinacija>) new UcitajListuDestinacija().execute(new Destinacija());
        } catch (Exception ex) {
            throw new Exception("Neuspesno ucitavanje destinacija!");
        }
    }

    public void zapamtiDestinaciju(Destinacija updateDestinacija) throws Exception {
        try {
            new ZapamtiDestinaciju().execute(updateDestinacija);
        } catch (Exception ex) {
            throw new Exception("Neuspesna promena podataka destinacije!");
        }
    }

    public void zapamtiAranzman(Aranzman updateAranzman) throws Exception {
        try {
            new ZapamtiAranzman().execute(updateAranzman);
        } catch (Exception ex) {
            throw new Exception("Neuspesna promena podataka aranzmana na serveru!");
        }
    }

    public void obrisiAranzman(Aranzman deleteAranzman) throws Exception {
        try {
            new ObrisiAranzman().execute(deleteAranzman);
        } catch (Exception ex) {
            throw new Exception("Neuspesno brisanje aranzmana iz baze!\nAranzman je ranije bio pod ugovorom!");
        }
    }

    public Ugovor obradiUgovor(Ugovor ugovor) throws Exception {
        try {
            return (Ugovor) new ObradiUgovor().execute(ugovor);
        } catch (Exception ex) {
            throw new Exception("Neuspesna obrada ugovora na serverskoj strani!");
        }
    }

    public void stornirajUgovor(Ugovor ugovor) throws Exception {
        try {
            new StornirajUgovor().execute(ugovor);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Neuspesno storniranje ugovora na serverskoj strani!");
        }
    }

    public Ugovor getUgovorByAranzmanID(Aranzman aranzman) throws Exception {
         try {
            return (Ugovor) new GetUgovorByAranzmanID().execute(aranzman);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception("Ugovor nije pronadjen ili je storniran!\n"+ex.getMessage());
        }
    }
    
    //getUgovorByID
    public Ugovor pretraziUgovore(int ugovorID) throws Exception {
         try {
            return (Ugovor) new PretraziUgovore().execute(ugovorID);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
    }
    
    public Klijent pretraziKlijente(String JMBG) throws Exception {
         try {
            return (Klijent) new PretraziKlijente().execute(JMBG);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            throw new Exception(ex.getMessage());
        }
    }

}
