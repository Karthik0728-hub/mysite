package com.mysite.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;
import com.adobe.cq.dam.cfm.ContentFragment;

@Model(
    adaptables = Resource.class,
    resourceType = TestimonialsCarouselModel.RESOURCE_TYPE
)
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION
)
public class TestimonialsCarouselModel {

    public static final String RESOURCE_TYPE = "mysite/components/testimonials";

    @ValueMapValue
    private String title;

    @ValueMapValue
    @Default(values = "/content/dam/mysite/testimonials")
    private String testimonialFolder;

    @Self
    private Resource resource;

    private List<TestimonialModel> testimonials = new ArrayList<>();

    @PostConstruct
    protected void init() {
        Resource folderResource = resource.getResourceResolver().getResource(testimonialFolder);
        if (folderResource != null) {
            for (Resource child : folderResource.getChildren()) {
                ContentFragment cf = child.adaptTo(ContentFragment.class);
                if (cf != null) {
                    testimonials.add(new TestimonialModel(cf));
                }
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public List<TestimonialModel> getTestimonials() {
        return testimonials;
    }

    public static class TestimonialModel {

        private String name;
        private String role;
        private String quote;
        private String profileImage;

        public TestimonialModel(ContentFragment fragment) {
            this.name = getElementContent(fragment, "name");
            this.role = getElementContent(fragment, "role");
            this.quote = getElementContent(fragment, "quote");
        }

        private String getElementContent(ContentFragment cf, String elementName) {
            try {
                if (cf.hasElement(elementName) && cf.getElement(elementName) != null) {
                    return cf.getElement(elementName).getContent();
                }
            } catch (Exception e) {
                // optional logging
            }
            return "";
        }
        public String getName() { return name; }
        public String getRole() { return role; }
        public String getQuote() { return quote; }
        public String getProfileImage() { return profileImage; }
    }
}
