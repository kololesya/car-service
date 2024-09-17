package com.laba.solvd.entities.people;

public enum CustomerType {
    REGULAR("Regular", 0.00),
    PREMIUM("Premium", 0.10),
    VIP("VIP", 0.15),
    CORPORATE("Corporate", 0.20);

    private final String typeName;
    private final double discountRate;

    CustomerType(String typeName, double discountRate) {
        this.typeName = typeName;
        this.discountRate = discountRate;
    }

    public String getTypeName() {
        return typeName;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    public static CustomerType fromTypeName(String typeName) {
        for (CustomerType type : values()) {
            if (type.getTypeName().equalsIgnoreCase(typeName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown type name: " + typeName);
    }
}
