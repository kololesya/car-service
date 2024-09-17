package com.laba.solvd.entities.people;

public class Customer extends Person {
    private long phoneNumber;
    private CustomerType customerType;

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public Customer(String name) {
        super(name);
    }

    public Customer(String name, long phoneNumber, CustomerType customerType){
        super(name);
        this.phoneNumber = phoneNumber;
        this.customerType = customerType;
    }

    @Override
    public String toString() {
        return "Customer: " +
                "by name " + getName() +
                " with phone number: " + phoneNumber +
                " has status " + customerType;
    }
}
