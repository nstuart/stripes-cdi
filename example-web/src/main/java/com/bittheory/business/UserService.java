/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.business;

import com.bittheory.domain.User;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author nick
 */
@Stateless
public class UserService {

    @Inject
    private DomainDao<User> userDao;

    public void createUser(User user) {
        userDao.create(user);
    }

    /**
     * Transaction will flush all changes....
     */
    public void update(){
        
    }
}
