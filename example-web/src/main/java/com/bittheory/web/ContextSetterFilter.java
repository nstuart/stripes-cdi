/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bittheory.web;

import java.io.IOException;
import javax.servlet.*;

/**
 *
 * @author nick
 */
public class ContextSetterFilter implements Filter{

    private String context;
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext().getContextPath();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute("ctx", context);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
    
}
