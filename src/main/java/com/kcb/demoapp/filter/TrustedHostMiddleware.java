package com.kcb.demoapp.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TrustedHostMiddleware implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(TrustedHostMiddleware.class);
    private static final Set<String> TRUSTED_HOSTS = Set.of("localhost", "tekmentors.com");
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         HttpServletRequest req = (HttpServletRequest)  request;
         String hostHeader = req.getHeader("Host");
         logger.info("Checking Host header: {}", hostHeader);
         if (hostHeader != null && TRUSTED_HOSTS.stream().noneMatch(hostHeader::contains)) {
             logger.warn("Untrusted Host detected: {}", hostHeader);
             ((HttpServletResponse)response).sendError(HttpServletResponse.SC_FORBIDDEN, "Untrusted Host");
             return;
         }
         logger.info("Host trusted: {}", hostHeader);
         chain.doFilter(req, response);
    }
}
