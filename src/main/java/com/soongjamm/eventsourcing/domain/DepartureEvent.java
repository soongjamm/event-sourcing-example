package com.soongjamm.eventsourcing.domain;

import java.time.LocalDateTime;

public class DepartureEvent extends DomainEvent {

    private final Port port;
    private final Ship ship;

    public DepartureEvent(LocalDateTime time, Port port, Ship ship) {
        super(time);
        this.port = port;
        this.ship = ship;
    }

    public void process() {
        ship().handle(this);
    }

    public Port port() {
        return port;
    }

    public Ship ship() {
        return ship;
    }
}
