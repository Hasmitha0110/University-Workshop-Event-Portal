package com.ruhuna.uniplus.dto;

import java.time.LocalDate;

public class EventResponse {
    private Long eventId;
    private String title;
    private String description;
    private String venue;
    private LocalDate eventDate;
    private String imageUrl;
    private String status;
    private Long createdById;
    private String createdByName;

    public EventResponse() {}

    public EventResponse(Long eventId, String title, String description, String venue,
                         LocalDate eventDate, String imageUrl,
                         String status, Long createdById, String createdByName) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.venue = venue;
        this.eventDate = eventDate;
        this.imageUrl = imageUrl;
        this.status = status;
        this.createdById = createdById;
        this.createdByName = createdByName;
    }

   //getters

    public Long getEventId() {
        return eventId;
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

    public LocalDate getEventDate() {
        return eventDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    //setters

    public void setEventId(Long eventId) {
        this.eventId = eventId;
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

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }
}
