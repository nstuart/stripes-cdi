/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.business;

import com.bittheory.business.qualifiers.UserRegistered;
import com.bittheory.domain.User;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nick
 */
@Stateless
public class Registration {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Inject
    @UserRegistered
    private Event<User> registeredEvent;
    
    public void register(User user){
        entityManager.persist(user);
        registeredEvent.fire(user);
    }
    
    public void resetPassword(String userName){
        
    }
    
    public void updateUser(){
        
    }
}
