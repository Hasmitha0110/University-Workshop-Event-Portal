package com.ruhuna.uniplus.services;

import org.springframework.stereotype.Service;
import java.time.Clock;
import java.time.LocalDate;

@Service
public class EventService {
    private final Clock clock;

    public EventService(Clock fixedClock) {
        this.clock = fixedClock;
    }

    public String computeStatus(LocalDate eventDate) {
        LocalDate today = LocalDate.now(clock);
        if(eventDate.isAfter(today)){
            return "Upcoming";
        } else if (eventDate.isEqual(today)) {
            return "Ongoing";
        }else {
            return "Concluded";
        }
    }


}
