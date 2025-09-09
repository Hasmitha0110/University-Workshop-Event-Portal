package com.ruhuna.uniplus.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "eventupdates")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EventUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long updateId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventid")
    @JsonBackReference
    private Event event;
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdby")
    @JsonIgnoreProperties({"events", "password"})
    private Admin createdBy;

    @Column(columnDefinition = "TEXT")
    private String content;
    private String imageUrl;
    private String link;

    private Instant createdAt = Instant.now();

    public EventUpdate() {}


    public EventUpdate(String title, Admin createdBy, String content, String imageUrl, String link, Event event) {
        this.title = title;
        this.createdBy = createdBy;
        this.content = content;
        this.imageUrl = imageUrl;
        this.link = link;
        this.event = event;
    }


    public Long getUpdateId() {
        return updateId;
    }

    public Event getEvent() {
        return event;
    }

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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Admin getCreatedBy() {
        return createdBy;
    }


    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

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

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBy(Admin createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String toString() {
        return "EventUpdate{" +
                "updateId=" + updateId +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
