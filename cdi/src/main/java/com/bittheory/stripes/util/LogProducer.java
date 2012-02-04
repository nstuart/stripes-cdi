/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.stripes.util;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author nick
 */
public class LogProducer {
    
    @Produces
    public Logger log(InjectionPoint ip){
        return LoggerFactory.getLogger(ip.getBean().getBeanClass());
    }
    
}
