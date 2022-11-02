package com.soongjamm.eventsourcing.domain;

public class CustomNotificationGateway {
    private final EventProcessor eventProcessor;
    private static boolean isActive = false;

    public CustomNotificationGateway(EventProcessor eventProcessor) {
        this.eventProcessor = eventProcessor;
    }

    public void notify(ArrivalEvent arrivalEvent) {
        if (isActive) {
            sendToCustoms(arrivalEvent);
        }
    }

    private void sendToCustoms(ArrivalEvent arrivalEvent) {
        eventProcessor.process(arrivalEvent);
    }

    public static void active() {
        isActive = true;
    }
}
