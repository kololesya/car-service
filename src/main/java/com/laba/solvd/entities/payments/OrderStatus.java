package com.laba.solvd.entities.payments;

import com.laba.solvd.entities.exceptions.OrderProcessingException;

public enum OrderStatus {
    PENDING("Pending", 1),
    PAID("Paid", 2),
    RETURNED("Returned", 3),
    CANCELED("Cancelled", 4);

    private final String displayName;
    private final int statusCode;

    OrderStatus(String displayName, int statusCode) {
        this.displayName = displayName;
        this.statusCode = statusCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public static OrderStatus fromStatusCode(int code) {
        for (OrderStatus status : values()) {
            if (status.getStatusCode() == code) {
                return status;
            }
        }
        throw new OrderProcessingException("Invalid status code: " + code);
    }
}
