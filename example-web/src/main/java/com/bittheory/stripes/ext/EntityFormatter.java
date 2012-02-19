/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.stripes.ext;

import com.bittheory.domain.Base;
import java.util.Locale;
import net.sourceforge.stripes.format.Formatter;

/**
 *
 * @author nick
 */
public class EntityFormatter implements Formatter<Base> {

    @Override
    public void setFormatType(String formatType) {
    }

    @Override
    public void setFormatPattern(String formatPattern) {
    }

    @Override
    public void setLocale(Locale locale) {
    }

    @Override
    public void init() {
    }

    @Override
    public String format(Base input) {
        if(input == null){
            return "0";
        }
        return String.valueOf(input.getId());
    }
    
}
