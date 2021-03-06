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
package com.bittheory.stripes.ext.cdi;

import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import net.sourceforge.stripes.config.RuntimeConfiguration;
import net.sourceforge.stripes.controller.ObjectFactory;

/**
 * Allows the ObjectFactory instance to be CDI Managed so it can be injected 
 * and managed. This class is not managed by CDI itself, but just discovered
 * by Stripes' normal extension hooks.
 *
 * @author Nick Stuart
 */
public class CdiRuntimeConfiguration extends RuntimeConfiguration {

    /**
     * Provide our own, CDI managed, ObjectFactory. Almost all Stripes object
     * creation will flow through this supplied instance, so very little needs
     * to be done here besides this.
     * @return 
     */
    @Override
    protected ObjectFactory initObjectFactory() {
        try {
            BeanManager bm = (BeanManager) new InitialContext().lookup("java:comp/BeanManager");
            Set<Bean<?>> beans = bm.getBeans(CdiObjectFactory.class);
            Bean<CdiObjectFactory> beanType = (Bean<CdiObjectFactory>) beans.iterator().next();
            CreationalContext<CdiObjectFactory> ctx = bm.createCreationalContext(beanType);
            CdiObjectFactory objFactory = (CdiObjectFactory) bm.getReference(beanType, CdiObjectFactory.class, ctx);
            objFactory.init(this);
            return objFactory;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    
    
}
