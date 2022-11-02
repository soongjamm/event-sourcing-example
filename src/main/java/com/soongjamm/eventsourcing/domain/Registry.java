package com.soongjamm.eventsourcing.domain;

public class Registry {
    private static final CustomNotificationGateway CUSTOM_NOTIFICATION_GATEWAY;

    static {
        CUSTOM_NOTIFICATION_GATEWAY = new CustomNotificationGateway(new EventProcessor());
    }

    private Registry() {
    }

    public static CustomNotificationGateway customNotificationGateway() {
        return CUSTOM_NOTIFICATION_GATEWAY;
    }
}
