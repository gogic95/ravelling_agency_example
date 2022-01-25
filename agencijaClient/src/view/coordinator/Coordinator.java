/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.coordinator;

import domain.Aranzman;
import domain.Destinacija;
import domain.Ugovor;
import java.util.HashMap;
import java.util.Map;
import view.controller.AranzmanAddEditController;
import view.controller.AranzmanMainController;
import view.controller.DestinacijaController;
import view.controller.LoginController;
import view.controller.MainController;
import view.controller.UgovorController;
import view.form.FrmAranzmanAddEdit;
import view.form.FrmAranzmanMain;
import view.form.FrmDestinacija;
import view.form.FrmLogin;
import view.form.FrmMain;
import view.form.FrmUgovor;
import view.form.mode.FormMode;

/**
 *
 * @author Gogic
 */
public class Coordinator {
    
    private static Coordinator instance;
    private final MainController mainController;
    
    
    private final Map <String, Object> parameters;

    private Coordinator() {
        parameters = new HashMap<>();
        mainController = new MainController(new FrmMain());
    }

    public static Coordinator getInstance() {
        if(instance == null){
            instance = new Coordinator();
        }
        return instance;
    }
    
    public void addParameter(String s, Object o){
        parameters.put(s, o);
    }

    public Object getParam(String name){
        return parameters.get(name);
    }
    
    

    public void openLoginForm() {
        LoginController loginController = new LoginController(new FrmLogin());
        loginController.openForm();
    }

    public void openMainForm() {
        mainController.openForm();
    }

    public void openInsertDestinacijaForm() {
        DestinacijaController destinacijaController = new DestinacijaController(new FrmDestinacija(mainController.getFrmMain(), true));
        destinacijaController.openForm(FormMode.FORM_ADD);
    }

    public MainController getMainController() {
        return mainController;
    }

    public void openUpdateDestinacijaForm() {
        DestinacijaController destinacijaController = new DestinacijaController(new FrmDestinacija(mainController.getFrmMain(), true));
        destinacijaController.openForm(FormMode.FORM_EDIT);
    }

    public void openAranzmanMainForm() {
        AranzmanMainController aranzmanMainController = new AranzmanMainController(new FrmAranzmanMain(mainController.getFrmMain(), true));
        aranzmanMainController.openForm();
    }

    public void openInsertAranzmanAddEditForm() {
        AranzmanAddEditController aranzmanAddEditController = new AranzmanAddEditController(new FrmAranzmanAddEdit(mainController.getFrmMain(), true));
        aranzmanAddEditController.openForm(FormMode.FORM_ADD);
    }

    public void openAranzmanMainForm(Destinacija selectedDestinacija) {
        AranzmanMainController aranzmanMainController = new AranzmanMainController(new FrmAranzmanMain(mainController.getFrmMain(), true),selectedDestinacija);
        aranzmanMainController.openForm();
    }

    public void openInsertAranzmanAddEditForm(Destinacija destinacija) {
        AranzmanAddEditController aranzmanAddEditController = new AranzmanAddEditController(new FrmAranzmanAddEdit(mainController.getFrmMain(), true),destinacija);
        aranzmanAddEditController.openForm(FormMode.FORM_ADD);
    }

    public void openUpdateAranzmanAddEditForm(Aranzman selectedAranzman) {
        AranzmanAddEditController aranzmanAddEditController = new AranzmanAddEditController(new FrmAranzmanAddEdit(mainController.getFrmMain(), true), selectedAranzman);
        aranzmanAddEditController.openForm(FormMode.FORM_EDIT);
    }

    public void openCreateUgovorForm(Aranzman selectedAranzman) {
        UgovorController ugovorController = new UgovorController(new FrmUgovor(mainController.getFrmMain(), true), selectedAranzman);
        ugovorController.openForm(FormMode.FORM_ADD);
    }

    public void openViewUgovorForm(Aranzman selectedAranzman) {
        UgovorController ugovorController = new UgovorController(new FrmUgovor(mainController.getFrmMain(), true), selectedAranzman);
        ugovorController.openForm(FormMode.FORM_VIEW);
    }

    public void openViewUgovorFormBySearch(Ugovor ugovor) {
        UgovorController ugovorController = new UgovorController(new FrmUgovor(mainController.getFrmMain(), true), ugovor);
        ugovorController.openForm(FormMode.FORM_VIEW);
    }
    
    
}
