package com.soongjamm.eventsourcing.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static com.soongjamm.eventsourcing.domain.Country.CANADA;
import static org.assertj.core.api.Assertions.assertThat;

class EventSourcingTest {

    Ship shipKR;
    Port portSF, portLA, portVancouver;
    Cargo cargo;
    EventProcessor eventProcessor;

    @BeforeEach
    void setUp() {
        eventProcessor = new EventProcessor();
        cargo = new Cargo ("Refactoring");
        shipKR = new Ship("King Roy");
        portSF = new Port("San Francisco", Country.US);
        portLA = new Port("Los Angeles", Country.US);
        portVancouver = new Port("Vancouver", CANADA) ;
    }

    @Nested
    @DisplayName("단순히 이벤트 리플레이한다")
    class TrackingShipsTest {

        @Test
        void arrival_sets_ships_location() {
            ArrivalEvent ev = new ArrivalEvent(LocalDateTime.of(2005, 11, 1, 0, 0, 0), portSF, shipKR);
            eventProcessor.process(ev);
            assertThat(shipKR.port()).isEqualTo(portSF);
        }

        @Test
        void departure_puts_ship_out_to_sea() {
            eventProcessor.process(new ArrivalEvent(LocalDateTime.of(2005, 10, 1, 0, 0, 0), portLA, shipKR));
            eventProcessor.process(new ArrivalEvent(LocalDateTime.of(2005, 11, 1, 0, 0, 0), portSF, shipKR));
            eventProcessor.process(new DepartureEvent(LocalDateTime.of(2005, 11, 1, 0, 0, 0), portSF, shipKR));
            assertThat(shipKR.port()).isEqualTo(new Port("At Sea", portSF.country()));
        }

        @Test
        void visiting_canada_marks_cargo() {
            eventProcessor.process(new LoadEvent(LocalDateTime.of(2005, 11, 1, 0, 0, 0), cargo, shipKR));
            eventProcessor.process(new ArrivalEvent(LocalDateTime.of(2005, 11, 2, 0, 0, 0), portVancouver, shipKR));
            eventProcessor.process(new DepartureEvent(LocalDateTime.of(2005, 11, 3, 0, 0, 0), portVancouver, shipKR));
            eventProcessor.process(new ArrivalEvent(LocalDateTime.of(2005, 11, 4, 0, 0, 0), portVancouver, shipKR));
            eventProcessor.process(new UnloadEvent(LocalDateTime.of(2005, 11, 5, 0, 0, 0), cargo, shipKR));
            assertThat(cargo.hasBeenIn(CANADA)).isTrue();
        }
    }


    @Nested
    @DisplayName("외부 호출은 이벤트 처리를 할 때만 게이트웨이를 통해 일어난다")
    class UpdatingAnExternalSystemTest {
        @Test
        void if_processor_inactive__when_arrival_when_not_notify_customs() {
            portLA.handle(new ArrivalEvent(LocalDateTime.of(2005, 11, 2, 0, 0, 0), portLA, shipKR));
            List<DomainEvent> logs = EventProcessor.logs();
            assertThat(logs).isEmpty();
        }

        @Test
        void if_processor_active__when_arrival_then_notify_customs() {
            CustomNotificationGateway.active();
            portLA.handle(new ArrivalEvent(LocalDateTime.of(2005, 11, 2, 0, 0, 0), portLA, shipKR));
            List<DomainEvent> logs = EventProcessor.logs();
            assertThat(logs).hasSize(1);
            assertThat(logs.get(0)).isInstanceOf(ArrivalEvent.class);
            assertThat(logs.get(0).occurred()).isEqualTo(LocalDateTime.of(2005, 11, 2, 0, 0, 0));
        }
    }

}