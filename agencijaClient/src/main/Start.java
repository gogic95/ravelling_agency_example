/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import view.coordinator.Coordinator;

/**
 *
 * @author Gogic
 */
public class Start {
    public static void main(String[] args) {
        Coordinator.getInstance().openLoginForm();
    }
}
