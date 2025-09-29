package com.mysite.core.models;

import org.apache.sling.api.resource.Resource;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
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

    @ChildResource(name ="heroButtons" )
    private List<HeroButton> heroButtons;

    public List<HeroButton> getHeroButtons() {
        return heroButtons;
    }

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

    @Model(adaptables = {Resource.class, SlingHttpServletRequest.class})
    @Exporter(name=ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
    public static class HeroButton{


        @ValueMapValue(optional = true)
        private String buttonType; // "primary" or "secondary"

        @ValueMapValue(optional = true)
        private String ctaLabelPrimary;

        @ValueMapValue(optional = true)
        private String ctaUrlPrimary;

        @ValueMapValue(optional = true)
        private String ctaLabelSecondary;

        @ValueMapValue(optional = true)
        private String ctaUrlSecondary;

        @ValueMapValue(optional = true)
        private String ctaTarget;


        public String getButtonType() {
            return buttonType != null ? buttonType : "primary";
        }

        public String getCtaLabel() {
            if ("secondary".equalsIgnoreCase(getButtonType()) && ctaLabelSecondary != null) {
                return ctaLabelSecondary;
            }
            return ctaLabelPrimary != null ? ctaLabelPrimary : "";
        }
        
        public String getCtaUrl() {
            if ("secondary".equalsIgnoreCase(getButtonType()) && ctaUrlSecondary != null) {
                return ctaUrlSecondary;
            }
            return ctaUrlPrimary != null ? ctaUrlPrimary : "#";
        }
        
        public String getCtaTarget() {
            return ctaTarget != null ? ctaTarget : "_self";
        }
            

        
    }

}
