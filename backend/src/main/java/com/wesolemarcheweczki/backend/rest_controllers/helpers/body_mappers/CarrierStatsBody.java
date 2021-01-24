package com.wesolemarcheweczki.backend.rest_controllers.helpers.body_mappers;

import java.time.LocalDateTime;

public class CarrierStatsBody {
    private final LocalDateTime from;
    private final LocalDateTime to;

    public CarrierStatsBody(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }
}
