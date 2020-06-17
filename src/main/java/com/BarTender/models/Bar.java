package com.BarTender.models;

import java.util.Objects;

public class Bar {
    private String id;
    private String name;
    private String image;
    private String address;
    private String userId;

    public Bar() {

    }

    public Bar(String id, String name, String image, String userId, String address) {
        this.id = id;
        this.name =  name;
        this.image =  image;
        this.userId =  userId;
        this.address =  address;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bar bar = (Bar) o;
        return id.equals(bar.id) &&
                name.equals(bar.name) &&
                image.equals(bar.image) &&
                userId.equals(bar.userId) &&
                address.equals(bar.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, userId, address);
    }

    @Override
    public String toString() {
        return "Bar{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
