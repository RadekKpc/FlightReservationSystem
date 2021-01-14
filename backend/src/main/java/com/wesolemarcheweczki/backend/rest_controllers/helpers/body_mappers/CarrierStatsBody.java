package com.wesolemarcheweczki.backend.rest_controllers.helpers.body_mappers;

import java.time.LocalDateTime;

public class CarrierStatsBody {
    private final int carrierId;
    private final LocalDateTime from;
    private final LocalDateTime to;

    public CarrierStatsBody(int carrierId, LocalDateTime from, LocalDateTime to) {
        this.carrierId = carrierId;
        this.from = from;
        this.to = to;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }
}
