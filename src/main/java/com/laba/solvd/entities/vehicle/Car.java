package com.laba.solvd.entities.vehicle;

import com.laba.solvd.entities.exceptions.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Year;
import java.util.ArrayList;
import java.util.Objects;

import static com.laba.solvd.entities.Utils.isValidDate;

//import static com.laba.solvd.entities.Utils.isValidDate;

public class Car extends Vehicle implements Inspectable, Repairable, Paintable {
    private static final Logger logger = LoggerFactory.getLogger(Car.class);
    private String vinNumber;
    private String serviceDate;
    private int manufacturingYear;

    public Car(String producent, String model, String vinNumber, String serviceDate, int manufacturingYear) {
        super(producent, model);
        this.vinNumber = vinNumber;
        this.serviceDate = serviceDate;
        this.manufacturingYear = manufacturingYear;
        logger.info("Car created: {}, VIN: {}", model, vinNumber);
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        try {
            this.serviceDate = String.valueOf(isValidDate(serviceDate));
            logger.info("Service date updated to: {}", serviceDate);
        } catch (IllegalArgumentException e) {
            logger.error("Error setting service date: {}", e.getMessage());
            throw new InvalidDataException("Invalid service date: " + serviceDate, e);
        }
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
        logger.info("VIN number updated to: {}", vinNumber);
    }

    public int getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(int manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
        logger.info("Manufacturing year updated to: {}", manufacturingYear);
    }

    public static int getCarCount(ArrayList<Car> cars) {
        return cars.size();
    }

    public int calculateCarAge() {
        int currentYear = Year.now().getValue();
        return currentYear - manufacturingYear;
    }
    @Override
    public void inspect() {
        logger.info("The car is undergoing technical inspection.");
    }

    @Override
    public void diagnose() {
        logger.info("Diagnostics of faults is carried out.");
    }

    @Override
    public void repair(boolean isRepairNeeded) {
        if (isRepairNeeded) {
            logger.info("The car is being repaired.");
        } else {
            logger.info("The repair isn't needed.");
        }
    }

    @Override
    public void performRepair(String vinNumber, String repairDetails) {
        logger.info("Performing repair on car with VIN: {} | Repair Details: {}", vinNumber, repairDetails);
    }

    @Override
    public void paint(String color) {
        logger.info("The car is painted in {}.", color);
    }

    @Override
    public void polish() {
        logger.info("The car is polished.");
    }

    @Override
    public String toString() {
        return super.toString() + ", vinNumber is " + vinNumber + ", service date is " + serviceDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), vinNumber, serviceDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        Car other = (Car) obj;
        return vinNumber.equals(other.vinNumber) && serviceDate.equals(other.serviceDate);
    }
}
