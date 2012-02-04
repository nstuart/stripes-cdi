/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * Allows the ObjectFactory instance to be CDI Managed so it can be inject
 *
 * @author nick
 */
public class CdiRuntimeConfiguration extends RuntimeConfiguration {

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
