/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.stripes.ext.cdi;

import java.lang.reflect.Method;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.controller.AnnotatedClassActionResolver;
import net.sourceforge.stripes.exception.StripesServletException;

/**
 * Helps to 'unwrap' proxied classes so that the real bean class can be found
 * and referenced in the handler mappings.
 * <p/>
 * If the CDI beans are non-scoped (Dependent) then the classes are not proxied
 * at all and are used directly. Otherwise we have to try and figure out the
 * actual class, as Stripes stores the real class name when it does the initial
 * classpath scanning and lookups for URLs and Events.
 *
 * @author Nick Stuart
 */
public class CdiAnnotationActionResolver extends AnnotatedClassActionResolver {

    @Override
    public Method getDefaultHandler(Class<? extends ActionBean> bean) throws StripesServletException {
        return super.getDefaultHandler(getRealBeanClass(bean));
    }

    @Override
    public String getEventName(Class<? extends ActionBean> bean, ActionBeanContext context) {
        return super.getEventName(getRealBeanClass(bean), context);
    }

    @Override
    protected String getEventNameFromPath(Class<? extends ActionBean> bean, ActionBeanContext context) {
        return super.getEventNameFromPath(getRealBeanClass(bean), context);
    }

    @Override
    protected String getEventNameFromEventNameParam(Class<? extends ActionBean> bean, ActionBeanContext context) {
        return super.getEventNameFromEventNameParam(getRealBeanClass(bean), context);
    }

    @Override
    protected String getEventNameFromRequestAttribute(Class<? extends ActionBean> bean, ActionBeanContext context) {
        return super.getEventNameFromRequestAttribute(getRealBeanClass(bean), context);
    }

    @Override
    protected String getEventNameFromRequestParams(Class<? extends ActionBean> bean, ActionBeanContext context) {
        return super.getEventNameFromRequestParams(getRealBeanClass(bean), context);
    }

    @Override
    public Method getHandler(Class<? extends ActionBean> bean, String eventName) throws StripesServletException {
        return super.getHandler(getRealBeanClass(bean), eventName);
    }

    @Override
    public String getUrlBinding(Class<? extends ActionBean> clazz) {
        return super.getUrlBinding(getRealBeanClass(clazz));
    }

    /**
     * Currently a naive approach to getting the real class as it relies on the
     * WeldClientProx to be a part of the classname. Obviously not portable or
     * robust.
     *
     * @param bean
     * @return
     */
    private Class<? extends ActionBean> getRealBeanClass(Class<? extends ActionBean> bean) {
        Class<? extends ActionBean> beanClass;
        if (bean.getName().contains("$_WeldClientProx")) {
            beanClass = (Class<? extends ActionBean>) bean.getSuperclass();
        } else {
            beanClass = bean;
        }
        return beanClass;
    }
}
