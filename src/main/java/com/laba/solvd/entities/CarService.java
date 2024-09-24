package com.laba.solvd.entities;

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

    public void removeDepartment(Department department) {
        setDepartments(removeElementFromSet(departments, department, CarService::isValidDepartment));
    }

    public void addDepartment(Department department) {

        addElementToSet(departments, department, CarService::isValidDepartment);
        logger.info("Department with name '{}' added successfully.", department.getName());
    }

    private static boolean isValidDepartment(Department department) {
        return department != null && department.getName() != null && !department.getName().isEmpty();
    }

    @Override
    public String toString() {
        String departmentNames = StringUtils.join(departments.stream()
                .map(Department::getName)
                .toArray(), ", ");
        return "CarService has departments: " + departmentNames;
    }
}