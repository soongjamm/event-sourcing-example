package com.soongjamm.eventsourcing.domain;

import java.util.ArrayList;
import java.util.List;

public class EventProcessor {

    private final List<DomainEvent> log = new ArrayList<>();

    public void process(DomainEvent e) {
        e.process();
        log.add(e);
    }
}
