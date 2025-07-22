package com.kcb.demoapp.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RequestLoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        long startTime = System.currentTimeMillis();
        logger.info("Incoming Request: {} {}", req.getMethod(), req.getRequestURI());
        chain.doFilter(request, response);
        long duration = System.currentTimeMillis() - startTime;
        HttpServletResponse res = (HttpServletResponse) response;
        logger.info("Response Status: {} for {} {} (Processed in {} ms)", res.getStatus(), req.getMethod(), req.getRequestURI(), duration);
        res.addHeader("X-Custom-Header", "Injected by Middleware");
    }
}
