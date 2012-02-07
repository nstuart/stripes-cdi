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

import com.bittheory.domain.Base;
import com.bittheory.domain.User;
import com.bittheory.business.qualifiers.DomainObject;
import com.google.common.base.Strings;
import java.lang.annotation.Annotation;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author nick
 */
@RequestScoped
public class DomainProducer {

    @PersistenceContext
    private EntityManager em;
    /**
     * Assuming we will always hit this after the stripes initialization of the
     * context. stripes needs to hit it first because it sets the Http specific
     * fields on the instance.
     */
    @Inject
    private CdiActionBeanContext cdiActionBeanContext;

    @Produces
//    @DomainObject
    public Base domainObject(InjectionPoint ip) throws InstantiationException, IllegalAccessException {
        Class entityClass = ip.getMember().getDeclaringClass();
        Annotated annotated = ip.getAnnotated();
        HttpServletRequest request = cdiActionBeanContext.getRequest();
        for (Annotation annotation : ip.getQualifiers()) {
            final DomainObject objAnnotations = annotation.annotationType().getAnnotation(DomainObject.class);
            if (objAnnotations != null) {
                String requestParam = objAnnotations.idFieldName();
                String[] requestValues = request.getParameterValues(requestParam);
                if (requestValues == null || requestValues.length == 0) {
                    return (Base) entityClass.newInstance();
                } else {
                    return (Base) em.find(entityClass, requestValues[0]);
                }
            }
        }
        return null;
    }

    @Produces
    @DomainObject
    public User userDomainObject(InjectionPoint ip) throws InstantiationException, IllegalAccessException {
        Annotated annotated = ip.getAnnotated();
        HttpServletRequest request = cdiActionBeanContext.getRequest();
        for (Annotation annotation : ip.getQualifiers()) {
            if (DomainObject.class.isAssignableFrom(annotation.annotationType())) {
                final DomainObject objAnnotations = (DomainObject) annotation;
                String requestParam = objAnnotations.idFieldName();
                String[] requestValues = request.getParameterValues(requestParam);
                if (requestValues == null || requestValues.length == 0 || Strings.isNullOrEmpty(requestValues[0])) {
                    return new User();
                } else {
                    return em.find(User.class, Integer.parseInt(requestValues[0]));
                }
            }
        }
        return null;
    }
}
