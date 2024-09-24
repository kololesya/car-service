package com.laba.solvd;

import com.laba.solvd.entities.CarService;
import com.laba.solvd.entities.exceptions.CarServiceException;
import com.laba.solvd.entities.exceptions.NullEntitySetException;
import com.laba.solvd.entities.order.Order;
import com.laba.solvd.entities.order.OrderItem;
import com.laba.solvd.entities.order.Warehouse;
import com.laba.solvd.entities.payments.PaymentProcessor;
import com.laba.solvd.entities.people.*;
import com.laba.solvd.entities.service.InspectionServiceCost;
import com.laba.solvd.entities.service.RepairServiceCost;
import com.laba.solvd.entities.service.ServiceCost;
import com.laba.solvd.entities.vehicle.Car;
import com.laba.solvd.entities.vehicle.VehicleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InvalidNameException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.*;

import static com.laba.solvd.entities.Utils.calculateCarAge;
import static com.laba.solvd.entities.Utils.findByName;
import static com.laba.solvd.entities.people.Department.findDepartmentByName;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws CarServiceException {
        logger.info("Starting Car Service application.");

        try {
            Set<Employee> employees = new HashSet<>();
            Set<Employee> employees1 = new HashSet<>();
            Set<Department> departments = new HashSet<>();
            Set<ServiceCost> serviceCosts = new HashSet<>();
            CarService carService = new CarService(departments);

            Car car1 = new Car("Toyota", "Corolla", "VIN12345", "2024-08-21", 2015, VehicleType.CAR);
            Car car2 = new Car("Honda", "Civic", "VIN54321", "2024-06-21", 2018, VehicleType.CAR);

            logger.info("Car {} age: {}", car2.getModel(), calculateCarAge(car2));

            Mechanic mechanic1 = new Mechanic("Adam Mickiewicz", 1600, 10, 20, "Engine Specialist", LocalDate.of(2020, 1, 15));
            Mechanic mechanic2 = new Mechanic("Ivan Petrov", 1600, 15, 20, "Engine Specialist", LocalDate.of(2019, 2, 1));
            Manager manager1 = new Manager("John Smith", "manager", 1000, 200, LocalDate.of(2022, 12, 5));
            Manager manager2 = new Manager("Anna Szultz", "manager", 1100, 200, LocalDate.of(2021, 12, 5));
            Mechanic mechanic3 = new Mechanic(null, 0, 0, 0, null, null);

            Warehouse warehouse = new Warehouse();

            warehouse.addParts("Brake Pads", 10);
            warehouse.addParts("Oil Filter", 5);
            warehouse.addParts("Engine Belt", 3);
            warehouse.addParts("Engine Belt1", 4);

            warehouse.printInventory();

            Department repairAndInspectionDepartment = new Department("Repair and Inspection", employees);
            repairAndInspectionDepartment.addEmployee(mechanic1);
            repairAndInspectionDepartment.addEmployee(mechanic2);
            repairAndInspectionDepartment.addEmployee(manager1);
            repairAndInspectionDepartment.removeEmployee(manager2);
            repairAndInspectionDepartment.setServiceCosts(serviceCosts);

            Department tireDepartment = new Department("Tire department", employees1);
            tireDepartment.addEmployee(mechanic1);
            tireDepartment.addEmployee(manager2);

            carService.addDepartment(repairAndInspectionDepartment);
            carService.addDepartment(tireDepartment);

            carService.removeDepartment(repairAndInspectionDepartment);
            System.out.println(findByName(carService.getDepartments(), "Tire department"));
            System.out.println(findByName(tireDepartment.getEmployees(), "Adam Mickiewicz"));

            carService.printPayroll();

            Customer customer = new Customer("John Doe", 555123478, CustomerType.VIP);
            Order order1 = new Order("ORD001", LocalDate.now(), customer, car1);

            OrderItem item1 = new OrderItem("0001", "Oil Filter", 150, 2);
            OrderItem item2 = new OrderItem("0002", "Brake Pads", 150, 1);
            OrderItem item3 = new OrderItem("0003", "Engine Belt", 300, 1);

            order1.addOrderItem(item1);
            order1.addOrderItem(item2);
            order1.addOrderItem(item3);

            warehouse.processOrder(order1);

            Customer customer2 = new Customer("Max True", 456789123, CustomerType.CORPORATE);
            Order order2 = new Order("ORD002", LocalDate.now(), customer2, car2);

            OrderItem item4 = new OrderItem("0001", "Oil Filter", 150, 2);
            OrderItem item5 = new OrderItem("0002", "Brake Pads", 150, 1);
            OrderItem item6 = new OrderItem("0003", "Engine Belt", 300, 1);

            order1.addOrderItem(item4);
            order1.addOrderItem(item5);
            order1.addOrderItem(item6);

            warehouse.processOrder(order2);

            InspectionServiceCost inspectionToyota = new InspectionServiceCost(car1, "Whole inspection", 60, order1.getOrderDate(), order1);
            inspectionToyota.performInspection(car1);

            ServiceCost serviceCost = new InspectionServiceCost(car1, "1", 1000, LocalDate.now(), order2);
            ServiceCost serviceCost1 = new RepairServiceCost(car2, "2", 1200, LocalDate.now(), order1);
            serviceCosts.add(serviceCost);
            serviceCosts.add(serviceCost1);

            System.out.println(repairAndInspectionDepartment.calculateTotalCost());

            System.out.println(repairAndInspectionDepartment.calculateTotalSalary());
            System.out.println(carService.calculateTotalSalary());

            PaymentProcessor paymentProcessor = new PaymentProcessor(serviceCost1);
            paymentProcessor.processPayment();


            PaymentProcessor paymentProcessor1 = new PaymentProcessor(serviceCost);
            paymentProcessor.processPayment();

            try {
                Optional<Department> department = findDepartmentByName(departments, "IT");

                if (department.isPresent()) {
                    logger.info("Department found: " + department.get().getName());
                } else {
                    logger.warn("Department not found.");
                }

            } catch (InvalidNameException | NullEntitySetException e) {
                logger.error("Error: " + e.getMessage());
            }


            try {
                Class<?> clazz = Warehouse.class;

                System.out.println("Fields:");
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    System.out.println("Field: " + field.getName());
                    System.out.println("Type: " + field.getType().getName());
                    System.out.println("Modifiers: " + Modifier.toString(field.getModifiers()));
                    System.out.println();
                }

                System.out.println("Constructors:");
                Constructor<?>[] constructors = clazz.getDeclaredConstructors();
                for (Constructor<?> constructor : constructors) {
                    System.out.println("Constructor: " + constructor.getName());
                    System.out.println("Parameter types: ");
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    for (Class<?> paramType : parameterTypes) {
                        System.out.println(" - " + paramType.getName());
                    }
                    System.out.println("Modifiers: " + Modifier.toString(constructor.getModifiers()));
                    System.out.println();
                }

                Constructor<?> constructor = clazz.getConstructor();
                Object warehouseInstance = constructor.newInstance();

                Method addPartsMethod = clazz.getMethod("addParts", String.class, int.class);
                addPartsMethod.invoke(warehouseInstance, "Brake Pads", 50);

                Method printInventoryMethod = clazz.getMethod("printInventory");
                printInventoryMethod.invoke(warehouseInstance);

                System.out.println("Methods:");
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    System.out.println("Method: " + method.getName());
                    System.out.println("Return type: " + method.getReturnType().getName());
                    System.out.println("Parameter types: ");
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    for (Class<?> paramType : parameterTypes) {
                        System.out.println(" - " + paramType.getName());
                    }
                    System.out.println("Modifiers: " + Modifier.toString(method.getModifiers()));
                    System.out.println();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        } catch (CarServiceException e) {
            logger.error("CarServiceException occurred: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred: {}", e.getMessage());
        } finally {
            logger.info("Application finished.");
        }

    }
}