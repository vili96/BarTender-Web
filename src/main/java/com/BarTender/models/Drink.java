package com.BarTender.models;

import java.util.Objects;

public class Drink {
    private String id;
    private String name;
    private double alcVolume;
    // Amount will be save in ml
    private double amount;
    private String description;
    private double price;
    private String barId;
    private int ordersCount;
    private String image;

    public Drink() {

    }

    public Drink (String id, String name, double alcVolume, double amount, String description,
                  double price, String barId, int ordersCount, String image) {
        this.id = id;
        this.name = name;
        this.alcVolume = alcVolume;
        this.amount = amount;
        this.description = description;
        this.price = price;
        this.barId = barId;
        this.ordersCount = ordersCount;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAlcVolume() {
        return alcVolume;
    }

    public void setAlcVolume(double alcVolume) {
        this.alcVolume = alcVolume;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBarId() {
        return barId;
    }

    public void setBarId(String barId) {
        this.barId = barId;
    }

    public int getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(int ordersCount) {
        this.ordersCount = ordersCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return Double.compare(drink.alcVolume, alcVolume) == 0 &&
                Double.compare(drink.amount, amount) == 0 &&
                Double.compare(drink.price, price) == 0 &&
                ordersCount == drink.ordersCount &&
                id.equals(drink.id) &&
                name.equals(drink.name) &&
                Objects.equals(description, drink.description) &&
                barId.equals(drink.barId) &&
                Objects.equals(image, drink.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, alcVolume, amount, description, price, barId, ordersCount, image);
    }

    @Override
    public String toString() {
        return "Drink{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", alcVolume=" + alcVolume +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", barId='" + barId + '\'' +
                ", ordersCount=" + ordersCount +
                ", image='" + image + '\'' +
                '}';
    }
}
