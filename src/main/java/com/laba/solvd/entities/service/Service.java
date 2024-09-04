package com.laba.solvd.entities.service;

import com.laba.solvd.entities.exceptions.InvalidCarException;
import com.laba.solvd.entities.exceptions.InvalidServiceException;
import com.laba.solvd.entities.people.Department;
import com.laba.solvd.entities.vehicle.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public abstract class Service {

    private static final Logger logger = LoggerFactory.getLogger(Department.class);
    private String serviceName;
    private LocalDate serviceDate;
    private Car car;

    public Service(String serviceName, LocalDate serviceDate, Car car) {
        if (serviceName == null || serviceName.trim().isEmpty()) {
            logger.error("Invalid serviceName: {}", serviceName);
            throw new InvalidServiceException("Service name cannot be null or empty.");
        }
        if (serviceDate == null) {
            logger.error("Invalid serviceDate: {}", serviceDate);
            throw new InvalidServiceException("Service date cannot be null.");
        }
        if (car == null) {
            logger.error("Invalid car: {}", car);
            throw new InvalidCarException("Car cannot be null.");
        }

        this.serviceName = serviceName;
        this.serviceDate = serviceDate;
        this.car = car;

        logger.info("Service created with name: {}, Date: {}, Car: {}", serviceName, serviceDate, car);
    }

    public abstract double calculateCost();

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        if (serviceName == null || serviceName.trim().isEmpty()) {
            logger.error("Attempted to set invalid serviceId: {}", serviceName);
            throw new InvalidServiceException("Service ID cannot be null or empty.");
        }
        this.serviceName = serviceName;
        logger.info("Service ID updated to: {}", serviceName);
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        if (serviceDate == null) {
            logger.error("Attempted to set invalid serviceDate: {}", serviceDate);
            throw new InvalidServiceException("Service date cannot be null.");
        }
        this.serviceDate = serviceDate;
        logger.info("Service date updated to: {}", serviceDate);
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        if (car == null) {
            logger.error("Attempted to set invalid car: {}", car);
            throw new InvalidCarException("Car cannot be null.");
        }
        this.car = car;
        logger.info("Car updated to: {}", car);
    }

}
