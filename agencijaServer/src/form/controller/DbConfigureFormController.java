/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form.controller;

import controller.Controller;
import form.view.DbConfigureForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Gogic
 */
public class DbConfigureFormController {

    private final DbConfigureForm dbConfigureForm;

    public DbConfigureFormController(DbConfigureForm dbConfigureForm) {
        this.dbConfigureForm = dbConfigureForm;
        openForm();
    }

    public DbConfigureForm getDbConfigureForm() {
        return dbConfigureForm;
    }

    private void openForm() {
        dbConfigureForm.setLocationRelativeTo(null);
        addAcitionListeners();
        dbConfigureForm.setVisible(true);
    }

    private void addAcitionListeners() {
        dbConfigureForm.addBtnConfigureActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = dbConfigureForm.getTxtUsername().getText();
                String password = new String(dbConfigureForm.getTxtPassword().getPassword());
                String url = dbConfigureForm.getTxtURL().getText();
                Controller.getInstance().addParameter("db.username", username);
                Controller.getInstance().addParameter("db.password", password);
                Controller.getInstance().addParameter("db.url", url);
                dbConfigureForm.dispose();
            }
        });
    }

}
