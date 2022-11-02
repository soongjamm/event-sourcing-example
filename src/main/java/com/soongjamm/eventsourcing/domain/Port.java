package com.soongjamm.eventsourcing.domain;

import java.util.Objects;

public record Port(String name, Country country) {

    public void handle(ArrivalEvent arrivalEvent) {
        arrivalEvent.ship().port(this);
        Registry.customNotificationGateway().notify(arrivalEvent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Port port)) return false;
        return Objects.equals(name, port.name) && country == port.country;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }
}
