package com.ruhuna.uniplus.dto;

import java.time.LocalDate;

public class EventCreateRequest {
    private String title;
    private String description;
    private String venue;
    private String imageUrl;
    private LocalDate eventDate;

    public EventCreateRequest() {}

    public EventCreateRequest(String title, String description, String venue, String imageUrl, LocalDate eventDate, Long createdById) {
        this.title = title;
        this.description = description;
        this.venue = venue;
        this.imageUrl = imageUrl;
        this.eventDate = eventDate;

    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getVenue() {
        return venue;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }


}