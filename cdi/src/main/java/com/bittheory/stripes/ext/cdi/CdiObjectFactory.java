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

import com.bittheory.stripes.util.QualifierLookup;
import java.lang.reflect.Constructor;
import java.util.Set;
import javax.annotation.Resource;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.AmbiguousResolutionException;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.sourceforge.stripes.config.ConfigurableComponent;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.DefaultObjectFactory;
import net.sourceforge.stripes.controller.ObjectFactory;
import org.slf4j.Logger;

/**
 * Allows for the majority of the integration with CDI. Because Stripes relies
 * on the ObjectFactory for almost all Object creation, as long as items are
 * funneled through this class everything should be magically 'CDI' integrated.
 *
 * <p/>
 * For default factories and formatters that are included with Stripes, and not
 * managed, this will fall back to thew regular DefaultObjectFactory to create
 * new instance of the classes.
 *
 * <p/>
 * <b>NOTE:</b> Because the objects are CDI managed, currently this class with
 * through an {@link UnsupportedOperationException} on the following methods:
 * <ul> <li>{@link CdiObjectFactory#newInstance(java.lang.reflect.Constructor, java.lang.Object[])}</li> <li>{@link CdiObjectFactory#newInstance(java.lang.Class, java.lang.Class<?>[], java.lang.Object[])}
 * </li> </ul>
 *
 * @author Nick Stuart
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

    /**
     * Checks the BeanManager first to see if the requested type is a
     * ManagedBean, otherwise it will fallback to the DefaultObjectFactory for
     * instantiation.
     * <p/>
     * If there is more then one instance of a Bean available, will through a 
     * {@link AmbiguousResolutionException}.
     * @param <T>
     * @param clazz
     * @return
     */
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
                throw new AmbiguousResolutionException("Multiple beans found that could be "
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
