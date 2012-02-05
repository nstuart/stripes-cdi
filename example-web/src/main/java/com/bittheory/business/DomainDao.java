/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.business;

import com.bittheory.domain.Base;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nick
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class DomainDao<T extends Base> {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public void create(T object){
        entityManager.persist(object);
    }
    
    public T load(Class<T> type, int id){
        return entityManager.find(type, id);
    }
    
}
