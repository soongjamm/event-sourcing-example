package com.soongjamm.eventsourcing.domain;

import java.util.ArrayList;
import java.util.List;

public class Ship {

    private final String name;
    private final List<Cargo> cargos = new ArrayList<>();
    private Port port;

    public Ship(String name) {
        this.name = name;
    }

    public void handle(ArrivalEvent arrivalEvent) {
        port = arrivalEvent.port();
        for (Cargo cargo : cargos) {
            cargo.handle(arrivalEvent);
        }
    }

    public void handle(DepartureEvent departureEvent) {
        port = new Port("At Sea", departureEvent.port().country());
    }

    public void handle(LoadEvent loadEvent) {
        cargos.add(loadEvent.cargo());
    }

    public void handle(UnloadEvent unloadEvent) {
        cargos.remove(unloadEvent.cargo());
    }

    public Port port() {
        return port;
    }

    public String name() {
        return name;
    }
}
