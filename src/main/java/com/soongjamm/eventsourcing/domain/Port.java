package com.soongjamm.eventsourcing.domain;

import java.util.Objects;

public class Port {

    public static final Port AT_SEA = new Port("At Sea", null);

    private final String name;
    private final Country country;

    public Port(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public String name() {
        return name;
    }

    public Country country() {
        return country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Port)) return false;
        Port port = (Port) o;
        return Objects.equals(name, port.name) && country == port.country;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, country);
    }
}
