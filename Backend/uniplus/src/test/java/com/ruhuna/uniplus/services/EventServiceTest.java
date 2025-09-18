package com.ruhuna.uniplus.services;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventServiceTest {

    @Test
    void returnsUpcoming_whenEventIsAfterToday() {
        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 14).atStartOfDay().toInstant(ZoneOffset.UTC),
                ZoneId.of("UTC")
        );
        EventService service = new EventService(fixedClock);

        String status = service.computeStatus(LocalDate.of(2025, 9, 15));
        assertEquals("Upcoming", status);
    }

    @Test
    void returnsOngoing_whenEventIsToday() {
        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 14).atStartOfDay().toInstant(ZoneOffset.UTC),
                ZoneId.of("UTC")
        );
        EventService service = new EventService(fixedClock);

        String status = service.computeStatus(LocalDate.of(2025, 9, 14));
        assertEquals("Ongoing", status);
    }

    @Test
    void returnsConcluded_whenEventIsBeforeToday() {
        Clock fixedClock = Clock.fixed(
                LocalDate.of(2025, 9, 14).atStartOfDay().toInstant(ZoneOffset.UTC),
                ZoneId.of("UTC")
        );
        EventService service = new EventService(fixedClock);

        String status = service.computeStatus(LocalDate.of(2025, 9, 13));
        assertEquals("Concluded", status);
    }
}
