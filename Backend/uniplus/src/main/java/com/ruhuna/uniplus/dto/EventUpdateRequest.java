package com.ruhuna.uniplus.dto;

public class EventUpdateRequest {
    private String title;
    private String content;
    private String imageUrl;
    private String link;

    public EventUpdateRequest() {
    }

    public EventUpdateRequest(String title, String content, String imageUrl, String link) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.link = link;
    }

    //getters

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLink() {
        return link;
    }

    //setters

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
