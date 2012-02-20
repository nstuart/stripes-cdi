/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.business.observers;

import com.bittheory.business.UserService;
import com.bittheory.domain.User;
import com.bittheory.stripes.model.LoginRequest;
import com.bittheory.stripes.util.LoginFailure;
import com.bittheory.stripes.util.LoginSuccess;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;

/**
 *
 * @author nick
 */
public class SimpleLoginWatcher {

    @Inject
    private Logger log;
    
    @Inject
    private UserService userService;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void logSuccess(@Observes @LoginSuccess User user) {
        log.debug("User {} logged in at {}", user.getUserName(), dateString());
        userService.setLastLogin(user);
    }

    public void logFailer(@Observes @LoginFailure LoginRequest request) {
        log.warn("User failed to login with userName of {} at {}.", request.getUserName(), dateString());
    }

    private String dateString() {
        return new SimpleDateFormat().format(new Date());
    }
}
