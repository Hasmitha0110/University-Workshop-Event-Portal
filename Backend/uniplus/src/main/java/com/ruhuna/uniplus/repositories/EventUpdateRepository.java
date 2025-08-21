package com.ruhuna.uniplus.repositories;

import com.ruhuna.uniplus.models.EventUpdate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventUpdateRepository extends JpaRepository<EventUpdate, Long> {
    List<EventUpdate> findByEvent_EventId(Long eventId);
}
