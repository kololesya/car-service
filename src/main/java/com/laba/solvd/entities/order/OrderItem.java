package com.laba.solvd.entities.order;

import java.util.Objects;

public class OrderItem {
    private String itemId;
    private String name;
    private double price;
    private int quantity;

    public OrderItem(String itemId, String name, double price, int quantity) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        OrderItem orderItem = (OrderItem) obj;
        return Double.compare(orderItem.price, price) == 0 && Objects.equals(name, orderItem.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }

    @Override
    public String toString() {
        return "OrderItem: " +
                "itemId: " + itemId +
                ", name: '" + name +
                ", price: " + price +
                ", quantity: " + quantity;
    }

}
