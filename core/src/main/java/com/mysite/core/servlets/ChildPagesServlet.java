package com.mysite.core.servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import com.day.cq.wcm.api.Page;

import com.day.cq.wcm.api.PageManager;

@Component(service=Servlet.class,
property={
    "sling.servlet.paths=/bin/childpages",
    "sling.servlet.methods=GET"
})
public class ChildPagesServlet extends SlingSafeMethodsServlet{ 

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException
    {

        ResourceResolver resourceResolver = request.getResourceResolver();
        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

        Page Parentpagepath = pageManager.getPage("/content/mysite/us/en/demo");

        Iterator<Page> childpages = Parentpagepath.listChildren();

        while(childpages.hasNext()){
            Page childpage = childpages.next();
            String title = childpage.getTitle();
            String path = childpage.getPath();
            String template = childpage.getTemplate().getTitle();

            response.setContentType("application/json");
            response.getWriter().write("{ \"title\": \"" + title + "\", \"path\": \"" + path + "\", \"template\": \"" + template + "\" }\n");
        }


    }    
}
