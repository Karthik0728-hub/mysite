package com.mysite.core.models;

import org.apache.sling.api.resource.Resource;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class})
public class HeroBannerModel {
    @ValueMapValue
    private String title;

    @ValueMapValue
    private String backgroundImage;

    @ValueMapValue
    private String description;

    @ValueMapValue
    private String theme;


    public String getTitle() {
        return title;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public String getDescription() {
        return description;
    }

    public String getTheme() {
        return theme != null ? theme : "light";
    }


}
