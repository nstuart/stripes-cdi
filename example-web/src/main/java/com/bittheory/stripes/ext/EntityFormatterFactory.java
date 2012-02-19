/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.stripes.ext;

import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.format.DefaultFormatterFactory;
import org.slf4j.Logger;

/**
 *
 * @author nick
 */
@ApplicationScoped
public class EntityFormatterFactory extends DefaultFormatterFactory {

    @PersistenceContext
    private EntityManager em;
    @Inject
    private Logger log;

    @Override
    public void init(Configuration configuration) throws Exception {
        super.init(configuration);
        Set<EntityType<?>> entities = em.getMetamodel().getEntities();
        for (EntityType<?> entityType : entities) {
            final Class<?> bindableJavaType = entityType.getBindableJavaType();
            log.debug("Adding class [{}] to be formatted by EntityFormatter.", bindableJavaType.getCanonicalName());
            add(bindableJavaType, EntityFormatter.class);
        }

    }
}
