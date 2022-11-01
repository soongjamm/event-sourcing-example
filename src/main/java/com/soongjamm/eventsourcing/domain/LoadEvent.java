package com.soongjamm.eventsourcing.domain;

import java.time.LocalDateTime;

public class LoadEvent extends DomainEvent {

    private final Cargo cargo;
    private final Ship ship;

    public LoadEvent(LocalDateTime time, Cargo cargo, Ship ship) {
        super(time);
        this.cargo = cargo;
        this.ship = ship;
    }

    @Override
    public void process() {
        ship.handle(this);
    }

    public Cargo cargo() {
        return cargo;
    }

    public Ship ship() {
        return ship;
    }
}
