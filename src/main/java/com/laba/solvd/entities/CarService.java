package com.laba.solvd.entities;

import com.laba.solvd.entities.exceptions.DepartmentException;
import com.laba.solvd.entities.exceptions.InvalidDataException;
import com.laba.solvd.entities.people.Department;
import com.laba.solvd.entities.people.SalaryCalculable;
import com.laba.solvd.entities.vehicle.Car;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static com.laba.solvd.entities.Utils.addElementToSet;
import static com.laba.solvd.entities.Utils.removeElementFromSet;

public class CarService implements SalaryCalculable {

    private static final Logger logger = LoggerFactory.getLogger(CarService.class);
    private Set<Department> departments;

    static {
        logger.info("Welcome to the Car Service System");
    }

    public CarService(Set<Department> departments) {
        this.departments = departments;
        logger.info("CarService initialized with {} departments.", departments.size());
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        if (departments == null) {
            logger.error("Error: Departments set cannot be null.");
            throw new IllegalArgumentException("Departments set cannot be null.");
        }
        this.departments = departments;
        logger.info("Departments set updated. Total departments: {}", departments.size());
    }
    @Override
    public double calculateTotalSalary() {
        return departments.stream()
                .mapToDouble(Department::calculateTotalSalary)
                .sum();
    }

    public void printPayroll() {
        departments.stream()
                .flatMap(department -> department.getEmployees().stream())
                .forEach(employee -> System.out.println(employee.getName() + " - " + employee.getSalary()));

        System.out.println("Total Salary: $" + String.format("%.2f", calculateTotalSalary()));
    }

    public void carOnTheService() {
        Set<String> uniqueCarIds = departments.stream()
                .flatMap(department -> department.getServiceCosts().stream())
                .map(serviceCost -> (Car) serviceCost.getOrder().getVehicle())
                .map(Car::getVinNumber)
                .collect(HashSet::new, Set::add, Set::addAll);

        int totalCarsServiced = uniqueCarIds.size();
        logger.info(StringUtils.join("Total number of cars serviced: ", totalCarsServiced));
    }

    public void removeDepartment(String departmentName) {
        if (departments == null || departments.isEmpty()) {
            logger.error("The departments set is null or empty.");
            throw new InvalidDataException("The departments set is null or empty.");
        }

        Department departmentToRemove = null;
        try {
            for (Department department : departments) {
                if (StringUtils.equals(department.getName(), departmentName)) {
                    departmentToRemove = department;
                    break;
                }
            }

            if (departmentToRemove != null) {
                departments = removeElementFromSet(departments, departmentToRemove);
                logger.info("Department with name '{}' removed successfully.", departmentName);
            } else {
                logger.warn("Department with name '{}' not found for removal.", departmentName);
                throw new DepartmentException("Department with name '" + departmentName + "' not found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while attempting to remove department '{}': {}", departmentName, e.getMessage());
            throw e;
        }
    }

    public void addDepartment(Department department) {
        if (StringUtils.isBlank(department.getName())) {
            logger.error("Error: Department name is blank or null.");
            throw new InvalidDataException("Department name cannot be blank or null.");
        }
        addElementToSet(departments, department);
        logger.info("Department with name '{}' added successfully.", department.getName());
    }

    @Override
    public String toString() {
        String departmentNames = StringUtils.join(departments.stream()
                .map(Department::getName)
                .toArray(), ", ");
        return "CarService has departments: " + departmentNames;
    }
}