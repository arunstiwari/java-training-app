package com.kcb.demoapp.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import org.slf4j.LoggerFactory;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RequestLoggingFilterTest {

    @BeforeEach
    void clearLogs() {
        TestLoggerFactory.clear();
    }

    @Test
    void testFilterLogsRequestAndInjectsHeader() throws Exception {
        RequestLoggingFilter filter = new RequestLoggingFilter();
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/test");
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain chain = new MockFilterChain();

        filter.doFilter(request, response, chain);

        // Assert custom header is present
        assert response.getHeader("X-Custom-Header").equals("Injected by Middleware");

        // Verify logs
        TestLogger logger = TestLoggerFactory.getTestLogger(RequestLoggingFilter.class);
        List<LoggingEvent> events = logger.getLoggingEvents();
        assert events.stream().anyMatch(e -> e.getMessage().contains("Incoming Request: GET /test"));
        assert events.stream().anyMatch(e -> e.getMessage().contains("Response Status: 200 for GET /test"));
    }

}