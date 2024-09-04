package com.laba.solvd.entities.service;

import com.laba.solvd.entities.exceptions.InvalidCarException;
import com.laba.solvd.entities.order.Order;
import com.laba.solvd.entities.vehicle.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class InspectionServiceCost extends ServiceCost{
    private static final Logger logger = LoggerFactory.getLogger(InspectionServiceCost.class);

    private int ageOfCar;
    public InspectionServiceCost(Car car, String serviceName, double baseCost, LocalDate serviceDate,
                                 Order order) {
        super(serviceName, serviceDate, car, baseCost, order);

        if (car == null) {
            logger.error("Invalid car: {}", car);
            throw new InvalidCarException("Car cannot be null.");
        }
        if (baseCost < 0) {
            logger.error("Invalid baseCost: {}", baseCost);
            throw new IllegalArgumentException("Base cost cannot be negative.");
        }

        this.ageOfCar = car.calculateCarAge();

        logger.info("InspectionServiceCost created with Car: {}, AgeOfCar: {}, BaseCost: {}",
                car, ageOfCar, baseCost);
    }

    @Override
    public double calculateCost() {
        double cost;
        if (ageOfCar <= 5) {
            cost = baseCost;
        } else if (ageOfCar > 5 && ageOfCar <= 10) {
            cost = baseCost * 0.1 + baseCost;
        } else {
            cost = baseCost * 0.2 + baseCost;
        }

        logger.info("Calculated cost for Car (Age: {}): {}", ageOfCar, cost);
        return cost;
    }

    public void performInspection(Car car) {
        if (car == null) {
            logger.error("Car for inspection is null.");
            throw new InvalidCarException("Car cannot be null.");
        }

        try {
            car.inspect();
            car.diagnose();
            String message = "Inspection Service for " + car.getProducent() + " " +
                    car.getModel() + " " + car.getVinNumber() +
                    " is completed. The cost is: " + calculateCost();
            logger.info(message);
            System.out.println(message);
        } catch (Exception e) {
            logger.error("Error performing inspection for Car: {}", car, e);
            throw new RuntimeException("Failed to perform inspection.", e);
        }
    }
}
