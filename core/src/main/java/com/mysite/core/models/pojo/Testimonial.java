package com.mysite.core.models.pojo;

public class Testimonial {
    private String name;
    private String role;
    private String quote;
    private String profileImage;


    public Testimonial(String name, String role, String quote, String profileImage) {
        this.name = name;
        this.role = role;
        this.quote = quote;
        this.profileImage = profileImage;
    }

    public String getName(){
        return name;
    }

    public String getRole() {
         return role; 
    }
    public String getQuote() {
         return quote; 
    }
    public String getProfileImage() {
         return profileImage;
    }
}
