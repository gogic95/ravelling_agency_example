/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package start;

import form.controller.MainFormController;
import form.view.MainForm;
import util.PropertyReader;

/**
 *
 * @author Gogic
 */
public class Main {
    public static void main(String[] args) {
        new MainFormController(new MainForm());
        PropertyReader.read();
    }
}
