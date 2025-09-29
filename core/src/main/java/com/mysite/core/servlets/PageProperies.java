package com.mysite.core.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import javax.servlet.Servlet; 

@Component(service=Servlet.class,
property={
    "sling.servlet.paths=/bin/pageproperties",
    "sling.servlet.methods=GET"
})
public class PageProperies extends SlingSafeMethodsServlet{

    @Override
    protected void doGet(SlingHttpServletRequest request,
                         SlingHttpServletResponse response)
            throws IOException {
        
        // Your logic to fetch and return page properties as JSON
        ResourceResolver ResourceResolver = request.getResourceResolver();
        PageManager PageManager = ResourceResolver.adaptTo(PageManager.class);
        Page page = PageManager.getPage("/content/mysite/us/en/demo");

        String title =page.getTitle();
        String description = page.getDescription();
        if(description == null){
            description="Demo Page";
        }

        String path = page.getPath();

        String name = page.getName();

        Calendar lastModifiedCal = page.getLastModified();

        String modified = "";
        if (lastModifiedCal != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            modified = sdf.format(lastModifiedCal.getTime());
        }

        String template= page.getTemplate().getTitle();
        
        response.setContentType("application/json");
        response.getWriter().write("{ \"title\": \"" + title + "\", \"description\": \"" + description + "\", \"path\": \"" + path + "\", \"name\": \"" + name + "\", \"modified\": \"" + modified + "\", \"template\": \"" + template + "\" }");

    }

    
}
