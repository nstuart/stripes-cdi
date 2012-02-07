/*
 * Copyright 2012 Nick Stuart
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
