package com.ruhuna.uniplus.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class EventCreateRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void valid_request_has_no_violations() {
        EventCreateRequest req = new EventCreateRequest();
        set(req, "Tech Talk", "About testing", "Main Hall", "", LocalDate.now().plusDays(1));

        Set<ConstraintViolation<EventCreateRequest>> violations = validator.validate(req);

        assertThat(violations).isEmpty();
    }

    @Test
    void blank_title_and_past_date_produce_violations() {
        EventCreateRequest req = new EventCreateRequest();
        set(req, "", "desc", "Main Hall", "", LocalDate.now().minusDays(1));

        Set<ConstraintViolation<EventCreateRequest>> violations = validator.validate(req);

        assertThat(violations.stream().map(v -> v.getPropertyPath() + ":" + v.getMessage()))
                .anyMatch(s -> s.contains("title") && s.contains("Title is required"))
                .anyMatch(s -> s.contains("eventDate") && (s.contains("Event date cannot be in the past") || s.contains("must be a date in the present or in the future")));
    }

    private void set(EventCreateRequest r, String title, String description, String venue, String imageUrl, LocalDate date) {
        // use setters if available; otherwise set via constructor if you have one
        // assuming setters exist on your DTO (add them if missing)
        r.setTitle(title);
        r.setDescription(description);
        r.setVenue(venue);
        r.setImageUrl(imageUrl);
        r.setEventDate(date);
    }
}
