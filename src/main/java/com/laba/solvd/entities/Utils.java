package com.laba.solvd.entities;

import com.laba.solvd.entities.exceptions.CarServiceException;
import com.laba.solvd.entities.exceptions.InvalidDataException;
import com.laba.solvd.entities.exceptions.NullEntitySetException;
import com.laba.solvd.entities.vehicle.Car;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InvalidNameException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

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

    public static <T extends Named> Optional<T> findByName(Set<T> entities, String name) throws InvalidNameException {
        if (entities == null) {
            logger.error("Error: The entity set is null.");
            throw new NullEntitySetException("The entity set is null.");
        }

        if (name == null || name.trim().isEmpty()) {
            logger.error("Error: The name cannot be null or empty.");
            throw new InvalidNameException("The name cannot be null or empty.");
        }

        return entities.stream()
                .filter(e -> e != null && e.getName().equals(name))
                .findFirst();
    }

    public static <T> Set<T> addElementToSet(Set<T> set, T element, Predicate<T> validator) {
        try {
            if (element == null) {
                throw new InvalidDataException("The element to add cannot be null.");
            }

            if (!validator.test(element)) {
                throw new CarServiceException("Cannot add element with invalid data: " + element);
            }

            set.add(element);

        } catch (CarServiceException e) {
            logger.error("Error adding element: " + e.getMessage());
        } finally {
            logger.info("Attempted to add a new element.");
        }
        return set;
    }

    public static <T> Set<T> removeElementFromSet(Set<T> set, T element, Predicate<T> validator) {
        Set<T> newSet = new HashSet<>();

        try {
            if (set == null) {
                throw new NullPointerException("The set cannot be null.");
            }

            if (element == null) {
                throw new InvalidDataException("The element to remove cannot be null.");
            }

            if (!validator.test(element)) {
                throw new CarServiceException("Cannot remove element with invalid data: " + element);
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
}
