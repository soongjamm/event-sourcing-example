package com.soongjamm.eventsourcing.domain;

import java.util.HashSet;
import java.util.Set;

public class Cargo {

    private final String name;
    private final Set<Country> visit = new HashSet<>();

    public Cargo(String name) {
        this.name = name;
    }

    public String name() {
        return name;
    }

    public void handle(ArrivalEvent arrivalEvent) {
        visit.add(arrivalEvent.port().country());
    }

    public boolean hasBeenIn(Country country) {
        return visit.contains(country);
    }
}
