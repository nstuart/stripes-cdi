/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.stripes.ext.cdi;

import com.bittheory.stripes.util.QualifierLookup;
import java.lang.reflect.Constructor;
import java.util.Set;
import javax.annotation.Resource;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.inject.Singleton;
import javax.naming.InitialContext;
import net.sourceforge.stripes.config.ConfigurableComponent;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.DefaultObjectFactory;
import net.sourceforge.stripes.controller.ObjectFactory;
import org.slf4j.Logger;

/**
 *
 * @author nick
 */
@Singleton
public class CdiObjectFactory implements ObjectFactory, ConfigurableComponent {

    private DefaultObjectFactory defaultObjectFactory;
    @Resource(name = "java:comp/BeanManager")
    private BeanManager beanManager;
    @Inject
    private Logger log;

    @Override
    public void init(Configuration configuration) throws Exception {
        defaultObjectFactory = new DefaultObjectFactory();
        defaultObjectFactory.init(configuration);
    }

    @Override
    public <T> T newInstance(Class<T> clazz) {
        T obj = null;
        if (clazz.isInterface()) {
            //delegate to the default object factory for predefined interfaces.
            obj = defaultObjectFactory.newInstance(clazz);
        } else {
            Set<Bean<?>> availableBeans = beanManager.getBeans(clazz, QualifierLookup.getQualifiers(clazz));
            if (availableBeans.isEmpty()) {
                log.debug("Could not find a valid bean for given type {}. "
                        + "Dropping to DefaultObjectFactory", clazz.getCanonicalName());
                obj = defaultObjectFactory.newInstance(clazz);
            } else if (availableBeans.size() > 1) {
                throw new RuntimeException("Multiple beans found that could be "
                        + "instantiated for the given class [" + clazz.getCanonicalName() + "].");
            } else {
                Bean<T> beanType = (Bean<T>) availableBeans.iterator().next();
                CreationalContext<T> ctx = beanManager.createCreationalContext(beanType);
                obj = (T) beanManager.getReference(beanType, clazz, ctx);
            }
        }
        return obj;
    }

    @Override
    public <T> T newInstance(Constructor<T> constructor, Object... args) {
        throw new UnsupportedOperationException("CDI Based object creation with"
                + "constructor arguments is not supported.");
    }

    @Override
    public <T> T newInstance(Class<T> clazz, Class<?>[] constructorArgTypes, Object[] constructorArgs) {
        throw new UnsupportedOperationException("CDI Based object creation with"
                + "constructor arguments is not supported.");
    }

    @Override
    public <T> ConstructorWrapper<T> constructor(Class<T> clazz, Class<?>... parameterTypes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
