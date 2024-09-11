package com.laba.solvd;

import com.laba.solvd.entities.CarService;
import com.laba.solvd.entities.exceptions.CarServiceException;
import com.laba.solvd.entities.order.Order;
import com.laba.solvd.entities.order.OrderItem;
import com.laba.solvd.entities.people.*;
import com.laba.solvd.entities.service.InspectionServiceCost;
import com.laba.solvd.entities.vehicle.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;

import static com.laba.solvd.entities.Utils.calculateCarAge;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws CarServiceException {
        logger.info("Starting Car Service application.");

        try {
            Set<Employee> employees = new HashSet<>();
            Set<Employee> employees1 = new HashSet<>();
            Set<Department> departments = new HashSet<>();
            CarService carService = new CarService(departments);

            Car car1 = new Car("Toyota", "Corolla", "VIN12345", "2024-08-21", 2015);
            Car car2 = new Car("Honda", "Civic", "VIN54321", "2024-06-21", 2018);

            logger.info("Car {} age: {}", car2.getModel(), calculateCarAge(car2));

            Mechanic mechanic1 = new Mechanic("Adam Mickiewicz", 1600, 10, 20, "Engine Specialist", LocalDate.of(2020, 1, 15));
            Mechanic mechanic2 = new Mechanic("Ivan Petrov", 1600, 15, 20, "Engine Specialist", LocalDate.of(2019, 2, 01));
            Manager manager1 = new Manager("John Smith", "manager", 1000, 200, LocalDate.of(2022, 12, 05));
            Manager manager2 = new Manager("Anna Szultz", "manager", 1100, 200, LocalDate.of(2021, 12, 05));
            Mechanic mechanic3 = new Mechanic(null, 0, 0, 0, null, null);

            Department repairAndInspectionDepartment = new Department("Repair and Inspection", employees);
            repairAndInspectionDepartment.addEmployee(mechanic1);
            repairAndInspectionDepartment.addEmployee(mechanic2);
            repairAndInspectionDepartment.addEmployee(manager1);
            repairAndInspectionDepartment.removeEmployee(manager2);

            Department tireDepartment = new Department("Tire department", employees1);
            tireDepartment.addEmployee(mechanic1);
            tireDepartment.addEmployee(manager2);

            carService.addDepartment(repairAndInspectionDepartment);
            carService.addDepartment(tireDepartment);
            System.out.println(carService);

           carService.removeDepartment("Repair and Inspection");

            carService.printPayroll();

            Customer customer = new Customer("John Doe", 555123478);
            Order order1 = new Order("ORD001", LocalDate.now(), customer, car1);

            OrderItem item1 = new OrderItem("0001", "Oil Change", 150);
            OrderItem item2 = new OrderItem("0002", "Oil Change", 150);
            OrderItem item3 = new OrderItem("0003", "Disk replacement", 300);

            order1.addOrderItem(item1);
            order1.addOrderItem(item2);
            order1.addOrderItem(item3);

            InspectionServiceCost inspectionToyota = new InspectionServiceCost(car1, "Whole inspection", 60, order1.getOrderDate(), order1);
            inspectionToyota.performInspection(car1);
            double x = inspectionToyota.calculateCost();

        } catch (CarServiceException e) {
            logger.error("CarServiceException occurred: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
        } finally {
            logger.info("Application finished.");
        }

    }
}