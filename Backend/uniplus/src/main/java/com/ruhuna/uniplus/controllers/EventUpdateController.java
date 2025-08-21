package com.ruhuna.uniplus.controllers;


import com.ruhuna.uniplus.dto.EventUpdateRequest;
import com.ruhuna.uniplus.models.Admin;
import com.ruhuna.uniplus.models.Event;
import com.ruhuna.uniplus.models.EventUpdate;
import com.ruhuna.uniplus.repositories.AdminRepository;
import com.ruhuna.uniplus.repositories.EventRepository;
import com.ruhuna.uniplus.repositories.EventUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event-updates")
public class EventUpdateController {

    @Autowired
    private EventUpdateRepository updateRepo;

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private AdminRepository adminRepo;

    @GetMapping("/event/{eventId}")
    public List<EventUpdate> getUpdatesByEvent(@PathVariable Long eventId) {
        return updateRepo.findByEvent_EventId(eventId);
    }

    @PostMapping("/event/{eventId}")
    public EventUpdate addUpdate(@PathVariable Long eventId, @RequestBody EventUpdateRequest req, Authentication auth) {

        if (auth == null || auth.getName() == null) {
            throw new RuntimeException("Unauthenticated - please login");
        }

        Event event = eventRepo.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found!"));
        Admin admin = adminRepo.findByEmail(auth.getName()).orElseThrow(() -> new RuntimeException("Logged in admin not found!"));

        EventUpdate update = new EventUpdate();
        update.setTitle(req.getTitle());
        update.setContent(req.getContent());
        update.setImageUrl(req.getImageUrl());
        update.setLink(req.getLink());
        update.setEvent(event);
        update.setCreatedBy(admin);

        return updateRepo.save(update);
    }

    @PutMapping("/{updateId}")
    public EventUpdate updateUpdate(@PathVariable Long updateId, @RequestBody EventUpdateRequest req, Authentication auth) {

        if (auth == null || auth.getName() == null) {
            throw new RuntimeException("Unauthenticated - please login");
        }

        EventUpdate existing = updateRepo.findById(updateId).orElseThrow(() -> new RuntimeException("Update not found"));
        Admin admin = adminRepo.findByEmail(auth.getName()).orElseThrow(() -> new RuntimeException("Logged in admin not found"));

        if (!existing.getCreatedBy().getAdminId().equals(admin.getAdminId())) {
            throw new RuntimeException("You are not allowed to edit this update");
        }

        existing.setTitle(req.getTitle());
        existing.setContent(req.getContent());
        existing.setImageUrl(req.getImageUrl());
        existing.setLink(req.getLink());

        return updateRepo.save(existing);
    }

    @DeleteMapping("/{updateId}")
    public void deleteUpdate(@PathVariable Long updateId, Authentication auth) {

        if (auth == null || auth.getName() == null) {
            throw new RuntimeException("Unauthenticated - please login");
        }

        Admin admin = adminRepo.findByEmail(auth.getName()).orElseThrow(() -> new RuntimeException("Logged in admin not found"));
        EventUpdate update = updateRepo.findById(updateId).orElseThrow(() -> new RuntimeException("Update not found"));

        if (!update.getCreatedBy().getAdminId().equals(admin.getAdminId())) {
            throw new RuntimeException("You are not allowed to delete this update");
        }

        updateRepo.deleteById(updateId);
    }

}
