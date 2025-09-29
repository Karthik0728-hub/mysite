package com.mysite.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.util.Enumeration;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.paths=/bin/hello",
                "sling.servlet.methods=GET"
        })
public class HelloServlet extends SlingSafeMethodsServlet {

    private static final Logger LOG = LoggerFactory.getLogger(HelloServlet.class);

    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response)
            throws java.io.IOException {

        // Log request path
        LOG.info("Request Path: {}", request.getRequestURI());

        // Log query parameters
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String param = paramNames.nextElement();
            String value = request.getParameter(param);
            LOG.info("Request Parameter: {} = {}", param, value);
        }

        // Log headers
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            String value = request.getHeader(header);
            LOG.info("Request Header: {} = {}", header, value);
        }

        // Get 'name' parameter
        String name = request.getParameter("name");
        if (name == null || name.isEmpty()) {
            name = "AEM World";
        }

        // Return JSON response
        response.setContentType("application/json");
        response.getWriter().write("{ \"message\": \"Hello " + name + "\" }");
    }
}
