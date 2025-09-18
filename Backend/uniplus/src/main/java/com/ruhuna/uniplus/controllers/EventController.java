package com.ruhuna.uniplus.controllers;

import com.ruhuna.uniplus.dto.EventCreateRequest;
import com.ruhuna.uniplus.dto.EventResponse;
import com.ruhuna.uniplus.models.Admin;
import com.ruhuna.uniplus.models.Event;
import com.ruhuna.uniplus.repositories.AdminRepository;
import com.ruhuna.uniplus.repositories.EventRepository;
import com.ruhuna.uniplus.services.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventRepository eventRepo;

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<EventResponse> getAll(@RequestParam(name = "search", required = false) String search) {
        List<Event> events;
        if (search != null && !search.isEmpty()) {
            events = eventRepo.findByTitleContainingIgnoreCase(search);
        } else {
            events = eventRepo.findAll();
        }
        return events.stream().map(ev ->
                new EventResponse(
                        ev.getEventId(),
                        ev.getTitle(),
                        ev.getDescription(),
                        ev.getVenue(),
                        ev.getEventDate(),
                        ev.getImageUrl(),
                        eventService.computeStatus(ev.getEventDate()),
                        ev.getCreatedBy() != null ? ev.getCreatedBy().getAdminId() : null,
                        ev.getCreatedBy() != null ? ev.getCreatedBy().getName() : null
                )
        ).collect(Collectors.toList());
    }

    @GetMapping("/{eventId}")
    public EventResponse getOne(@PathVariable Long eventId) {
        Event ev = eventRepo.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"));

        return new EventResponse(
                ev.getEventId(),
                ev.getTitle(),
                ev.getDescription(),
                ev.getVenue(),
                ev.getEventDate(),
                ev.getImageUrl(),
                eventService.computeStatus(ev.getEventDate()),
                ev.getCreatedBy() != null ? ev.getCreatedBy().getAdminId() : null,
                ev.getCreatedBy() != null ? ev.getCreatedBy().getName() : null
        );
    }

    @PostMapping
    public Event create(@RequestBody @Valid EventCreateRequest req, Authentication auth) {

        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Unauthenticated - please login");
        }

        Admin admin = adminRepo.findByEmail(auth.getName()).orElseThrow(() -> new RuntimeException("Logged in admin not found"));
        Event ev = new Event();
        ev.setTitle(req.getTitle());
        ev.setDescription(req.getDescription());
        ev.setVenue(req.getVenue());
        ev.setEventDate(req.getEventDate());
        ev.setImageUrl(req.getImageUrl());
        ev.setCreatedBy(admin);
        return eventRepo.save(ev);
    }

    @PutMapping("/{eventId}")
    public Event update(@PathVariable Long eventId, @RequestBody @Valid EventCreateRequest req, Authentication auth) {
        Event ev = eventRepo.findById(eventId).orElseThrow();
        Admin admin = adminRepo.findByEmail(auth.getName()).orElseThrow();

        if(ev.getCreatedBy() == null || !ev.getCreatedBy().getAdminId().equals(admin.getAdminId())){
            throw new RuntimeException("You are not allowed to edit this event!");
        }

        ev.setTitle(req.getTitle());
        ev.setDescription(req.getDescription());
        ev.setVenue(req.getVenue());
        ev.setEventDate(req.getEventDate());
        ev.setImageUrl(req.getImageUrl());
        return eventRepo.save(ev);
    }

    @DeleteMapping("/{eventId}")
    public void delete(@PathVariable Long eventId, Authentication auth) {
        Event ev = eventRepo.findById(eventId).orElseThrow();
        Admin admin = adminRepo.findByEmail(auth.getName()).orElseThrow();

        if(ev.getCreatedBy() == null || !ev.getCreatedBy().getAdminId().equals(admin.getAdminId())){
            throw new RuntimeException("You are not allowed to edit this event!");
        }
        eventRepo.deleteById(eventId);
    }

}
