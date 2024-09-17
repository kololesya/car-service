package com.laba.solvd.entities.service;

import com.laba.solvd.entities.exceptions.InvalidDataException;
import com.laba.solvd.entities.order.Order;
import com.laba.solvd.entities.people.CustomerType;
import com.laba.solvd.entities.vehicle.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class RepairServiceCost extends ServiceCost{
    private static final Logger logger = LoggerFactory.getLogger(RepairServiceCost.class);

    private double partsCost;

    public RepairServiceCost(Car car, String serviceName, double baseCost, LocalDate serviceDate,
                             Order order) {
        super(serviceName, serviceDate, car, baseCost, order);
        if (order == null) {
            throw new InvalidDataException("Order cannot be null.");
        }
        this.partsCost = order.calculateTotalItemsCost();

        logger.info("RepairServiceCost created with PartsCost: {}, LaborHours: {}, LaborRate: {}",
                partsCost);
    }

    public double getPartsCost() {
        return partsCost;
    }

    public void setPartsCost(double partsCost) {
        if (partsCost < 0) {
            logger.error("Attempted to set invalid partsCost: {}", partsCost);
            throw new IllegalArgumentException("Parts cost cannot be negative.");
        }
        this.partsCost = partsCost;
        logger.info("PartsCost updated to: {}", partsCost);
    }

    @Override
    public double calculateCost() {
        double totalCost = baseCost + partsCost;
        CustomerType customerType = getOrder().getCustomer().getCustomerType();
        double discountRate = customerType.getDiscountRate();
        double discountedCost = totalCost * (1 - discountRate);
        logger.info("Calculated total cost with discount: {}", discountedCost);
        return discountedCost;
    }
}
