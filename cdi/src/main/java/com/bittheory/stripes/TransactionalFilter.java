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
package com.bittheory.stripes;

import com.bittheory.stripes.util.TransactionRequired;
import java.io.IOException;
import java.lang.reflect.Method;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Status;
import javax.transaction.UserTransaction;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.config.Configuration;
import net.sourceforge.stripes.controller.ActionResolver;
import net.sourceforge.stripes.controller.DispatcherServlet;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.exception.StripesServletException;
import net.sourceforge.stripes.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Allows for an entire Stripes request to be run in a transaction if the
 * resolved ActionBean Handler has the {@link TransactionRequired} attribute
 * supplied.
 * <p/>
 * All this class does is a 'premature' resultion (outside of the normal Stripes
 * LifeCycle of the ActionBean and Handler, relying on the CDI Driven beans to
 * get the information.
 *
 * @author Nick Stuart
 */
public class TransactionalFilter implements Filter {

    @Resource
    private UserTransaction utx;
    @Inject
    private ActionResolver actionResolver;
    /**
     * Will only work if the user has a managed ActionBeanContext class that is
     * request scoped.
     */
    @Inject
    private ActionBeanContext context;
    
    private Logger log = LoggerFactory.getLogger(TransactionalFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final Configuration config = StripesFilter.getConfiguration();

        ActionBeanContext actionContext = config.getActionBeanContextFactory().getContextInstance((HttpServletRequest) request,
                (HttpServletResponse) response);

        Class<? extends ActionBean> actionBean = actionResolver.getActionBeanType(HttpUtil.getRequestedPath((HttpServletRequest) request));
        if (actionBean != null) {
            String eventName = actionResolver.getEventName(actionBean, actionContext);
            Method handler;
            if (eventName == null) {
                handler = actionResolver.getDefaultHandler(actionBean);
            } else {
                handler = actionResolver.getHandler(actionBean, actionResolver.getEventName(actionBean, actionContext));
            }
            if (handler == null) {
                throw new StripesServletException("Could not find handler for URL.");
            }
            if (handler.getAnnotation(TransactionRequired.class) != null) {
                boolean startedTransaction = false;
                try {
                    if (utx.getStatus() != Status.STATUS_ACTIVE) {
                        utx.begin();
                        startedTransaction = true;
                    }
                    chain.doFilter(request, response);
                    if (startedTransaction) {
                        if (context.getValidationErrors().isEmpty()) {
                            utx.commit();
                        } else {
                            log.debug("Context has validation errors. Performing rollback.");
                            utx.rollback();
                        }
                    }
                    return;
                } catch (Throwable t) {
                    if (startedTransaction) {
                        try {
                            utx.rollback();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    throw new RuntimeException(t);
                }
            }
        }
        chain.doFilter(request, response);

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
