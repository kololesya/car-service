package com.laba.solvd.entities;

import com.laba.solvd.entities.exceptions.DepartmentException;
import com.laba.solvd.entities.people.Department;
import com.laba.solvd.entities.people.Employee;
import com.laba.solvd.entities.people.SalaryCalculable;
import com.laba.solvd.entities.service.ServiceCost;
import com.laba.solvd.entities.vehicle.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CarService implements SalaryCalculable {

    private static final Logger logger = LoggerFactory.getLogger(CarService.class);
    private Map<Integer, Department> departments;

    static {
        logger.info("Welcome to the Car Service System");
    }

    public CarService(Map<Integer, Department> departments) {
        if (departments == null) {
            logger.error("Error: Departments map cannot be null.");
            throw new IllegalArgumentException("Departments map cannot be null.");
        }
        this.departments = departments;
        logger.info("CarService initialized with {} departments.", departments.size());
    }

    public Map<Integer, Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Map<Integer, Department> departments) {
        if (departments == null) {
            logger.error("Error: Departments map cannot be null.");
            throw new IllegalArgumentException("Departments map cannot be null.");
        }
        this.departments = departments;
        logger.info("Departments map updated. Total departments: {}", departments.size());
    }

    @Override
    public double calculateTotalSalary() {
        double totalSalary = 0;
        for (Department department : departments.values()) {
            totalSalary += department.calculateTotalSalary();
        }
        return totalSalary;
    }

    public void printPayroll() {
        for (Department department : departments.values()) {
            for (Employee employee : department.getEmployees()) {
                System.out.println(employee.getName() + " - " + employee.getSalary());
            }
        }
        System.out.println("Total Salary: $" + String.format("%.2f", calculateTotalSalary()));
    }

    public void carOnTheService() {
        int totalCarsServiced = 0;
        Set<String> uniqueCarIds = new HashSet<>();

        for (Department department : departments.values()) {
            for (ServiceCost serviceCost : department.getServiceCosts()) {
                if (serviceCost.getOrder() != null && serviceCost.getOrder().getVehicle() instanceof Car) {
                    Car car = (Car) serviceCost.getOrder().getVehicle();
                    if (car != null && uniqueCarIds.add(car.getVinNumber())) {
                        totalCarsServiced++;
                    }
                }
            }
        }
    }

    public void removeDepartment(String departmentName) {
        if (departments == null || departments.isEmpty()) {
            logger.error("The departments map is null or empty.");
            throw new NullPointerException("The departments map is null or empty.");
        }

        int departmentIdToRemove = -1;
        try {
            for (Map.Entry<Integer, Department> entry : departments.entrySet()) {
                Department department = entry.getValue();
                if (department != null && department.getName().equals(departmentName)) {
                    departmentIdToRemove = entry.getKey();
                    break;
                }
            }

            if (departmentIdToRemove >= 0) {
                try {
                    departments.remove(departmentIdToRemove);
                    logger.info("Department with name '{}' and ID '{}' removed successfully.", departmentName, departmentIdToRemove);
                } catch (Exception e) {
                    logger.error("Failed to remove department with name '{}' and ID '{}'.", departmentName, departmentIdToRemove, e);
                    throw new DepartmentException("Failed to remove department with name '" + departmentName + "'.", e);
                }
            } else {
                logger.warn("Department with name '{}' not found for removal.", departmentName);
                throw new DepartmentException("Department with name '" + departmentName + "' not found.");
            }
        } catch (Exception e) {
            logger.error("An error occurred while attempting to remove department '{}': {}", departmentName, e.getMessage());
            throw e;
        }
    }

    public void addDepartment(int departmentId, Department department) {
        if (departmentId <= 0 || department == null) {
            logger.error("Error: Department ID or Department cannot be null.");
            throw new IllegalArgumentException("Department ID or Department cannot be null.");
        }
        departments.put(departmentId, department);
        logger.info("Department with ID '{}' added successfully.", departmentId);
    }
}