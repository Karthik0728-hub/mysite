/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.mysite.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class DemoCarouselModel {

    @Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
    public static class SlideItem {
        @Inject @Named("itemTitle")
        private String title;

        @Inject @Named("itemSubtitle")
        private String subtitle;

        @Inject @Named("itemDescription")
        private String description;

        @Inject @Named("image")
        private String image;

        public String getTitle() { return title; }
        public String getSubtitle() { return subtitle; }
        public String getDescription() { return description; }
        public String getImage() { return image; }
    }

    @ChildResource(name = "slides")
    private List<SlideItem> slides;

    public List<SlideItem> getSlides() {
        return slides != null ? slides : Collections.emptyList();
    }
}
