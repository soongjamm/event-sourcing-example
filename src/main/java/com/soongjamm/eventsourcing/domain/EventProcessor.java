package com.soongjamm.eventsourcing.domain;

import java.util.ArrayList;
import java.util.List;

public class EventProcessor {

    private static final List<DomainEvent> logs = new ArrayList<>();

    public void process(DomainEvent e) {
        e.process();
        logs.add(e);
    }

    public static List<DomainEvent> logs() {
        return logs;
    }
}
