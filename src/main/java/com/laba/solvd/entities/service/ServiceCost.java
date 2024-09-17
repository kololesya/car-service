package com.laba.solvd.entities.service;

import com.laba.solvd.entities.order.Order;
import com.laba.solvd.entities.payments.OrderStatus;
import com.laba.solvd.entities.vehicle.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public abstract class ServiceCost extends Service{
    private static final Logger logger = LoggerFactory.getLogger(ServiceCost.class);

    protected double baseCost;
    private Order order;

    public ServiceCost(String serviceId, LocalDate serviceDate, Car car, double baseCost) {
        super(serviceId, serviceDate, car);
        if (baseCost < 0) {
            logger.error("Invalid baseCost: {}", baseCost);
            throw new IllegalArgumentException("Base cost cannot be negative.");
        }
        this.baseCost = baseCost;
        logger.info("ServiceCost created with BaseCost: {}", baseCost);
    }

    public ServiceCost(String serviceId, LocalDate serviceDate, Car car, double baseCost, Order order) {
        this(serviceId, serviceDate, car, baseCost);
        this.order = order;
        logger.info("ServiceCost created with Order: {}", order);
    }

    public double getBaseCost() {
        return baseCost;
    }

    public void setBaseCost(double baseCost) {
        if (baseCost < 0) {
            logger.error("Attempted to set invalid baseCost: {}", baseCost);
            throw new IllegalArgumentException("Base cost cannot be negative.");
        }
        this.baseCost = baseCost;
        logger.info("BaseCost updated to: {}", baseCost);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        logger.info("Order updated to: {}", order);
    }

    public abstract double calculateCost();

    @Override
    public String toString() {
        return getServiceName() + " for the car with " + getCar().getVinNumber() + " has cost: $" + String.format("%.2f", calculateCost());
    }
}
