package com.BarTender.models;

import java.util.Date;
import java.util.Objects;

public class Consumption {
    private String id;
    private String userId;
    private String drinkId;
    private Date date;
    private int quantity;
    private double currentPrice;

    public Consumption() {

    }

    public Consumption(String id, String userId, String drinkId, Date date, int quantity, double currentPrice) {
        this.id = id;
        this.userId = userId;
        this.drinkId = drinkId;
        this.date = date;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDrinkId() {
        return drinkId;
    }

    public void setDrinkId(String drinkId) {
        this.drinkId = drinkId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consumption that = (Consumption) o;
        return quantity == that.quantity &&
                Double.compare(that.currentPrice, currentPrice) == 0 &&
                id.equals(that.id) &&
                userId.equals(that.userId) &&
                drinkId.equals(that.drinkId) &&
                date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, drinkId, date, quantity, currentPrice);
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", drinkId='" + drinkId + '\'' +
                ", date=" + date +
                ", quantity=" + quantity +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
