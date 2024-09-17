package com.laba.solvd.entities.vehicle;

public enum VehicleType {
    CAR("Car", 4),
    MOTORCYCLE("Motorcycle", 2),
    TRUCK("Truck", 6),
    BUS("Bus", 8);

    private final String displayName;
    private final int numberOfWheels;

    VehicleType(String displayName, int numberOfWheels) {
        this.displayName = displayName;
        this.numberOfWheels = numberOfWheels;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getNumberOfWheels() {
        return numberOfWheels;
    }

    public static VehicleType fromDisplayName(String displayName) {
        for (VehicleType type : values()) {
            if (type.getDisplayName().equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown display name: " + displayName);
    }
}
