package com.laba.solvd.entities.service;

import com.laba.solvd.entities.exceptions.InvalidOrderException;
import com.laba.solvd.entities.order.Order;
import com.laba.solvd.entities.vehicle.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class RepairServiceCost extends ServiceCost{
    private static final Logger logger = LoggerFactory.getLogger(RepairServiceCost.class);

    private double partsCost;
    private double laborHours;
    private double laborRate;

    public RepairServiceCost(Car car, String serviceName, double baseCost, LocalDate serviceDate,
                             Order order, double laborHours, double laborRate) {
        super(serviceName, serviceDate, car, baseCost, order);

        if (laborHours < 0) {
            logger.error("Invalid laborHours: {}", laborHours);
            throw new IllegalArgumentException("Labor hours cannot be negative.");
        }
        if (laborRate < 0) {
            logger.error("Invalid laborRate: {}", laborRate);
            throw new IllegalArgumentException("Labor rate cannot be negative.");
        }

        if (order == null) {
            logger.error("Invalid order: {}", order);
            throw new InvalidOrderException("Order cannot be null.");
        }

        this.partsCost = order.calculateTotalItemsCost();
        this.laborHours = laborHours;
        this.laborRate = laborRate;

        logger.info("RepairServiceCost created with PartsCost: {}, LaborHours: {}, LaborRate: {}",
                partsCost, laborHours, laborRate);
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

    public double getLaborHours() {
        return laborHours;
    }

    public void setLaborHours(double laborHours) {
        if (laborHours < 0) {
            logger.error("Attempted to set invalid laborHours: {}", laborHours);
            throw new IllegalArgumentException("Labor hours cannot be negative.");
        }
        this.laborHours = laborHours;
        logger.info("LaborHours updated to: {}", laborHours);
    }

    public double getLaborRate() {
        return laborRate;
    }

    public void setLaborRate(double laborRate) {
        if (laborRate < 0) {
            logger.error("Attempted to set invalid laborRate: {}", laborRate);
            throw new IllegalArgumentException("Labor rate cannot be negative.");
        }
        this.laborRate = laborRate;
        logger.info("LaborRate updated to: {}", laborRate);
    }

    @Override
    public double calculateCost() {
        double totalCost = baseCost + partsCost + (laborHours * laborRate);
        logger.info("Calculated total cost: {}", totalCost);
        return totalCost;
    }
}
