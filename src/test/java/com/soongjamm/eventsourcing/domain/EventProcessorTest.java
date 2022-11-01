package com.soongjamm.eventsourcing.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.soongjamm.eventsourcing.domain.Country.CANADA;
import static org.assertj.core.api.Assertions.assertThat;

class EventProcessorTest {

    Ship shipKR;
    Port portSF, portLA, portVancouver;
    Cargo cargo;
    EventProcessor sut;

    @BeforeEach
    void setUp() {
        sut = new EventProcessor();
        cargo = new Cargo ("Refactoring");
        shipKR = new Ship("King Roy");
        portSF = new Port("San Francisco", Country.US);
        portLA = new Port("Los Angeles", Country.US);
        portVancouver = new Port("Vancouver", CANADA) ;
    }

    @Nested
    class TrackingShipsTest {

        @Test
        void arrival_sets_ships_location() {
            ArrivalEvent ev = new ArrivalEvent(LocalDateTime.of(2005, 11, 1, 0, 0, 0), portSF, shipKR);
            sut.process(ev);
            assertThat(shipKR.port()).isEqualTo(portSF);
        }

        @Test
        void departure_puts_ship_out_to_sea() {
            sut.process(new ArrivalEvent(LocalDateTime.of(2005, 10, 1, 0, 0, 0), portLA, shipKR));
            sut.process(new ArrivalEvent(LocalDateTime.of(2005, 11, 1, 0, 0, 0), portSF, shipKR));
            sut.process(new DepartureEvent(LocalDateTime.of(2005, 11, 1, 0, 0, 0), portSF, shipKR));
            assertThat(shipKR.port()).isEqualTo(new Port("At Sea", portSF.country()));
        }

        @Test
        void visiting_canada_marks_cargo() {
            sut.process(new LoadEvent(LocalDateTime.of(2005, 11, 1, 0, 0, 0), cargo, shipKR));
            sut.process(new ArrivalEvent(LocalDateTime.of(2005, 11, 2, 0, 0, 0), portVancouver, shipKR));
            sut.process(new DepartureEvent(LocalDateTime.of(2005, 11, 3, 0, 0, 0), portVancouver, shipKR));
            sut.process(new ArrivalEvent(LocalDateTime.of(2005, 11, 4, 0, 0, 0), portVancouver, shipKR));
            sut.process(new UnloadEvent(LocalDateTime.of(2005, 11, 5, 0, 0, 0), cargo, shipKR));
            assertThat(cargo.hasBeenIn(CANADA)).isTrue();
        }
    }

}