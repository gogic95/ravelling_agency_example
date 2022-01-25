/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;

/**
 *
 * @author Gogic
 * @param <T>
 */
public interface Repository <T>{
    
    public T insert(T parameter) throws Exception;
    public T update(T parameter) throws Exception;
    public boolean delete(T parameter) throws Exception;
    
    public List<T> getAll(T paremeter) throws Exception;
    public List<T> getBy(T parameter, String column, String value) throws Exception;
    
    public T get(T paremeter) throws Exception;
}
