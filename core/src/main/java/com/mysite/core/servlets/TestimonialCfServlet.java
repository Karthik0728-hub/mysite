package com.mysite.core.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import javax.servlet.Servlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import com.adobe.cq.dam.cfm.ContentFragment;
import com.google.gson.Gson;
import com.mysite.core.models.pojo.Testimonial;


@Component(
    service = Servlet.class,
    property = {
        "sling.servlet.paths=/bin/testimonial",
        "sling.servlet.methods=GET"
    }
)

public class TestimonialCfServlet extends SlingSafeMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException{
        String cfPath = request.getParameter("path");
        
        if (cfPath == null || cfPath.isEmpty()) {
            cfPath = "/content/dam/mysite/testimonials";
        }

        String filterRole = request.getParameter("role");

        ResourceResolver resolver = request.getResourceResolver();
        Resource root = resolver.getResource(cfPath);

         List<Testimonial> testimonials = new ArrayList<>();

        if (root != null) {
            for (Resource child : root.getChildren()) {
                ContentFragment cf = child.adaptTo(ContentFragment.class);
                if (cf != null) {
                    String name = cf.getElement("name") != null ? cf.getElement("name").getContent() : "";
                    String role = cf.getElement("role") != null ? cf.getElement("role").getContent() : "";
                    String quote = cf.getElement("quote") != null ? cf.getElement("quote").getContent() : "";
                    String profileImage = cf.getElement("profileImage") != null ? cf.getElement("profileImage").getContent() : "";
        
                   if (filterRole == null || filterRole.isEmpty() || role.equalsIgnoreCase(filterRole)) {
                        testimonials.add(new Testimonial(name, role, quote, profileImage));
                    }
                }
            }
        }

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(testimonials));
        
    }


}
