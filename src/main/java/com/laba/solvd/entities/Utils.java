package com.laba.solvd.entities;

import com.laba.solvd.entities.exceptions.CarServiceException;
import com.laba.solvd.entities.exceptions.InvalidDataException;
import com.laba.solvd.entities.exceptions.NullEntitySetException;
import com.laba.solvd.entities.people.Department;
import com.laba.solvd.entities.people.Employee;
import com.laba.solvd.entities.vehicle.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InvalidNameException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static boolean isValidDate(String dateStr) throws InvalidDataException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(dateStr, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            throw new InvalidDataException("Invalid date format: " + dateStr + " with format: " + dateFormatter, e);
        }
    }

    public static int calculateCarAge(Car car) {
        int currentYear = LocalDate.now().getYear();
        int manufacturingYear = car.getManufacturingYear();
        int age = currentYear - manufacturingYear;
        logger.info("Calculated age for car with VIN " + car.getVinNumber() + " is " + age + " years");
        return age;
    }

    public static <T extends Named> T findByName(Set<T> entities, String name) throws InvalidNameException {
        try{
            if (entities == null) {
                logger.error("Error: The entity set is null.");
                throw new NullEntitySetException("The entity set is null.");
            }

            if (name == null || name.trim().isEmpty()) {
                logger.error("Error: The name cannot be null or empty.");
                throw new InvalidNameException("The name cannot be null or empty.");
            }

            for (T entity : entities) {
                if (entity != null && entity.getName().equals(name)) {
                    logger.info("Entity found: {}", entity);
                    return entity;
                }
            }

            logger.info("Entity with name '{}' not found.", name);
        } catch (NullEntitySetException | InvalidNameException e){
            System.err.println("Error: " + e.getMessage());
        }

        return null;
    }

    public static <T> Set<T> addElementToSet(Set<T> set, T element) {
        try {
            if (element == null) {
                throw new InvalidDataException("The element to add cannot be null.");
            }

            if (element instanceof Employee) {
                Employee employee = (Employee) element;
                if (!isValidEmployee(employee)) {
                    throw new CarServiceException("Cannot add an employee with invalid data: " + employee);
                }
            }

            if (element instanceof Department) {
                Department department = (Department) element;
                if (!isValidDepartment(department)) {
                    throw new CarServiceException("Cannot add a department with invalid data: " + department);
                }
            }

            set.add(element);

        } catch (CarServiceException e) {
            logger.error("Error adding element: " + e.getMessage());
        } finally {
            logger.info("Attempted to add a new element.");
        }
        return set;
    }
    public static <T> Set<T> removeElementFromSet(Set<T> set, T element) {
        Set<T> newSet = new HashSet<>();

        try {
            if (set == null) {
                throw new NullPointerException("The set cannot be null.");
            }

            if (element == null) {
                throw new InvalidDataException("The element to remove cannot be null.");
            }

            if (element instanceof Employee) {
                Employee employee = (Employee) element;
                if (!isValidEmployee(employee)) {
                    throw new CarServiceException("Cannot remove an employee with invalid data: " + employee);
                }
            }

            if (element instanceof Department) {
                Department department = (Department) element;
                if (!isValidDepartment(department)) {
                    throw new CarServiceException("Cannot remove a department with invalid data: " + department);
                }
            }

            newSet.addAll(set);

            if (!newSet.remove(element)) {
                throw new InvalidDataException("The element to remove is not found in the set.");
            }

        } catch (NullPointerException | InvalidDataException | CarServiceException e) {
            logger.error("Error removing element from set: {}", e.getMessage());
            return set;
        } finally {
            logger.info("Attempted to remove an element from the set.");
        }

        return newSet;
    }

    private static boolean isValidEmployee(Employee employee) {
        return employee != null && !employee.getName().isEmpty();
    }

    private static boolean isValidDepartment(Department department) {
        return department != null && department.getName() != null && !department.getName().isEmpty();
    }
}
