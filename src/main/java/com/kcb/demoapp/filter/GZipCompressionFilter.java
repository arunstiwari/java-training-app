package com.kcb.demoapp.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class GZipCompressionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (acceptsGZipEncoding(request)) {
            GZipServletResponseWrapper gzipResponse = new GZipServletResponseWrapper(res);
            res.setHeader("Content-Encoding", "gzip");
            try {
                chain.doFilter(request, gzipResponse);
                gzipResponse.finish();
            } finally {
                // No-op
            }
        } else {
            chain.doFilter(request, response);
        }

    }

    private boolean acceptsGZipEncoding(ServletRequest request) {
        String acceptEncoding = ((jakarta.servlet.http.HttpServletRequest) request).getHeader("Accept-Encoding");
        return acceptEncoding != null && acceptEncoding.contains("gzip");
    }
}

