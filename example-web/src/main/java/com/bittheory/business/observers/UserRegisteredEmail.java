/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.business.observers;

import com.bittheory.business.qualifiers.UserRegistered;
import com.bittheory.domain.User;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import org.slf4j.Logger;

/**
 *
 * @author nick
 */
public class UserRegisteredEmail {
    
    @Inject
    private Logger log;
    
    public void sendEmail(@Observes @UserRegistered User user){
        log.info("User {} successfully registered.");
        log.info("Sent email.");
    }
    
}
