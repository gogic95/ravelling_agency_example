/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package operation;

import repository.Repository;
import repository.db.DbRepository;
import repository.db.impl.RepositoryDbGeneric;

/**
 *
 * @author Gogic
 */
public abstract class GenericOperation {
    
    protected final Repository repositoryDB;

    public GenericOperation() {
        this.repositoryDB = new RepositoryDbGeneric();
    }
    
    public final Object execute(Object param) throws Exception{
        try {
            preconditions(param);
            startTransaction();
            Object result = executeOperation(param);
            commitTransaction();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            rollbackTransaction();
            throw e;
        } finally{
            disconect();
        }
    }

    private void startTransaction() throws Exception {
        ((DbRepository) repositoryDB).connect();
    }

    protected abstract void preconditions(Object param) throws Exception;

    protected abstract Object executeOperation(Object param) throws Exception;
    

    private void commitTransaction() throws Exception {
        ((DbRepository) repositoryDB).commit();
    }

    private void rollbackTransaction() throws Exception {
        ((DbRepository) repositoryDB).rollback();
    }

    private void disconect() throws Exception {
        ((DbRepository) repositoryDB).disconnect();
    }
    
    
    
    
}
