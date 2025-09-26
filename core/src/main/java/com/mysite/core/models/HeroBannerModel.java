package com.mysite.core.models;

import org.apache.sling.api.resource.Resource;


import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ExporterConstants;

@Model(adaptables = {Resource.class, SlingHttpServletRequest.class},
resourceType = {HeroBannerModel.RESOURCE_TYPE }
)
@Exporter(name=ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class HeroBannerModel {

    public static final String RESOURCE_TYPE = "mysite/components/herobanner";

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
