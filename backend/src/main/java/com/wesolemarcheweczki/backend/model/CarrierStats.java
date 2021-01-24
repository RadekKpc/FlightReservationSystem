package com.wesolemarcheweczki.backend.model;

import java.time.LocalDateTime;

public class CarrierStats {
    private int carrierId;
    private long flightsAmount;
    private long totalCost;
    private LocalDateTime from;
    private LocalDateTime to;

    public void setCarrierId(int carrierId) {
        this.carrierId = carrierId;
    }

    public void setFlightsAmount(int flightsAmount) {
        this.flightsAmount = flightsAmount;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getCarrierId() {
        return carrierId;
    }

    public long getFlightsAmount() {
        return flightsAmount;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public CarrierStats(int carrierId, long flightsAmount, long totalCost) {
        this.carrierId = carrierId;
        this.flightsAmount = flightsAmount;
        this.totalCost = totalCost;
    }
}
