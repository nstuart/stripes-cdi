/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.stripes.ext;

import com.google.common.base.Strings;
import java.util.Collection;
import java.util.Locale;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.sourceforge.stripes.validation.TypeConverter;

/**
 *
 * @author nick
 */
public class EntityConverter implements TypeConverter<Object> {

    @PersistenceContext
    private EntityManager em;
    
    private Locale locale;

    @Override
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public Object convert(String input, Class targetType, Collection errors) {
        if (Strings.isNullOrEmpty(input)) {
            return null;
        } else {
            Long idL = Long.parseLong(input);
            return em.find(targetType, idL);
        }

    }
}
