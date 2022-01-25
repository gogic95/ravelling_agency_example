/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communication;

import java.io.Serializable;

/**
 *
 * @author Gogic
 */
public enum Operations implements Serializable{
    INSERT_DESTINACIJA,
    INSERT_ARANZMAN,
    INSERT_UGOVOR,
    LOGIN,
    GET_ALL_DESTINACIJA,
    UPDATE_DESTINACIJA,
    UPDATE_ARANZMAN,
    DELETE_ARANZMAN,
    UPDATE_UGOVOR,
    GET_UGOVOR_BY_ARANZMANID,
    GET_UGOVOR_BY_ID,
    GET_KLIJENT_BY_JMBG
    
    
}
