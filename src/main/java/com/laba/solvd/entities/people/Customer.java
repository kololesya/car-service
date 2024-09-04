package com.laba.solvd.entities.people;

public class Customer extends Person {
    private long phoneNumber;

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Customer(String name) {
        super(name);
    }

    public Customer(String name, long phoneNumber){
        super(name);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Customer: " +
                "by name " + getName() +
                " with phone number=" + phoneNumber;
    }
}
