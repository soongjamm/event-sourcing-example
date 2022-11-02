package com.soongjamm.eventsourcing.domain;

import java.time.LocalDateTime;

public abstract class DomainEvent {

    private final LocalDateTime recorded;
    private final LocalDateTime occurred;

    protected DomainEvent(LocalDateTime occurred) {
        this.recorded = LocalDateTime.now();
        this.occurred = occurred;
    }

    public abstract void process();

    public LocalDateTime recorded() {
        return recorded;
    }

    public LocalDateTime occurred() {
        return occurred;
    }
}
