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

import com.bittheory.domain.User;
import com.bittheory.stripes.util.PasswordHasher;
import com.google.common.base.Strings;
import java.util.Arrays;
import java.util.Date;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;

/**
 *
 * @author nick
 */
@Stateless
public class UserService {

    @Inject
    private DomainDao<User> userDao;
    @PersistenceContext
    private EntityManager em;
    @Inject
    private PasswordHasher hasher;
    @Inject
    private Logger log;

    public void createUser(User user) {
        setPassword(user);
        userDao.create(user);
    }

    /**
     *
     */
    @TransactionAttribute(TransactionAttributeType.MANDATORY)
    public void update(User user) {
        setPassword(user);
    }

    public void setLastLogin(User user) {
        em.createQuery("UPDATE User u SET u.lastLogin = :lastLogin WHERE u = :user").
                setParameter("user", user).
                setParameter("lastLogin", new Date()).
                executeUpdate();
    }

    public boolean validPassword(String userName, String password) {
        try {
            Object[] info = (Object[]) em.createNamedQuery(User.QRY_LOGIN_INFO).
                    setParameter("userName", userName).
                    getSingleResult();
            String givenHash = hasher.encrypt(password, String.valueOf(info[1]));
            final boolean matches = givenHash.equals(info[0]);
            if (matches) {
                log.debug("User supplied correct login credientials for [{}]", userName);
            } else {
                log.debug("User supplied INCORRECT login credientials for [{}]", userName);
            }
            return matches;
        } catch (NoResultException nre) {
            log.info("User supplied invalid username [{}]", userName);
            return false;
        }
    }

    public User loadByUserName(String userName) {
        return em.createNamedQuery(User.QRY_BY_USER_NAME, User.class).
                setParameter("userName", userName).
                getSingleResult();
    }

    /**
     * Generates a new password and salt only if the uesr.password value is not
     * empty.
     *
     * @param user
     */
    private void setPassword(User user) {
        if (user != null && !Strings.isNullOrEmpty(user.getPassword())) {
            String salt = hasher.getRandomSalt();
            user.setHashedPassword(hasher.encrypt(user.getPassword(), salt));
            user.setSalt(salt);
        }
    }
}
