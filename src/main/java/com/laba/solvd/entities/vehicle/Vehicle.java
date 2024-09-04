package com.laba.solvd.entities.vehicle;

import java.util.Objects;

public abstract class Vehicle {
    private String producent;
    private String model;

    protected Vehicle(String producent, String model) {
        this.producent = producent;
        this.model = model;
    }

    public String getProducent() {
        return producent;
    }

    public void setProducent(String producent) {
        this.producent = producent;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "A vehicle is made by " + producent + ", model is " + model;
    }

    @Override
    public int hashCode() {
        return Objects.hash(producent, model);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vehicle vehicle = (Vehicle) obj;
        return producent.equals(vehicle.producent) && model.equals(vehicle.model);
    }
}
