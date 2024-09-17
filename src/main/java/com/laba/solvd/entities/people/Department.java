package com.laba.solvd.entities.people;

import com.laba.solvd.entities.Named;
import com.laba.solvd.entities.exceptions.DepartmentException;
import com.laba.solvd.entities.exceptions.NullEntitySetException;
import com.laba.solvd.entities.service.ServiceCost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.InvalidNameException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.laba.solvd.entities.Utils.*;

public class Department implements SalaryCalculable, Named {

    private static final Logger logger = LoggerFactory.getLogger(Department.class);
    private String departmentName;
    private Set<Employee> employees;
    private Set<ServiceCost> serviceCosts;

    public Department(String departmentName) {
        if (departmentName == null || departmentName.isEmpty()) {
            logger.error("Invalid department name provided.");
            throw new DepartmentException("Department name cannot be null or empty.");
        }
        this.departmentName = departmentName;
        this.employees = new HashSet<>();
        this.serviceCosts = new HashSet<>();
        logger.info("Department '{}' created.", departmentName);
    }

    public Department(String departmentName, Set<Employee> employees) {
        this(departmentName);
        this.employees = employees != null ? employees : new HashSet<>();
    }

    public void setDepartmentName(String departmentName) {
        if (departmentName == null || departmentName.isEmpty()) {
            logger.error("Attempt to set invalid department name.");
            throw new DepartmentException("Department name cannot be null or empty.");
        }
        this.departmentName = departmentName;
        logger.info("Department name set to '{}'.", departmentName);
    }
    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (employees == null) {
            logger.error("Attempt to set null employees.");
            throw new DepartmentException("Employees set cannot be null.");
        }
        this.employees = employees;
        logger.info("Employees list updated for department '{}'.", departmentName);
    }

    public Set<ServiceCost> getServiceCosts() {
        return serviceCosts;
    }

    public void setServiceCosts(Set<ServiceCost> serviceCosts) {
        if (serviceCosts == null) {
            logger.error("Attempt to set null service costs.");
            throw new DepartmentException("Service costs set cannot be null.");
        }
        this.serviceCosts = serviceCosts;
        logger.info("Service costs updated for department '{}'.", departmentName);
    }

    @Override
    public double calculateTotalSalary() {
        return employees.stream()
                .mapToDouble(Employee::calculateTotalSalary)
                .sum();
    }

    public double calculateTotalCost() {
        return serviceCosts.stream()
                .mapToDouble(ServiceCost::calculateCost)
                .sum();
    }

    @Override
    public String getName() {
        return departmentName;
    }

    public void printEmployees() {
        if (employees.isEmpty()) {
            logger.warn("No employees to print in department '{}'.", departmentName);
        } else {
            logger.info("Printing employees of department '{}'.", departmentName);
            employees.stream()
                    .forEach(System.out::println);
        }
    }

    public static Optional<Department> findDepartmentByName(Set<Department> departments, String name)
            throws InvalidNameException, NullEntitySetException {
        return findByName(departments, name);
    }

    public void removeEmployee(Employee employee) {
        setEmployees(removeElementFromSet(employees, employee));
    }

    public void addEmployee(Employee employee){
        addElementToSet(employees, employee);
    }

    public void printInvoice() {
        serviceCosts.stream()
                .forEach(System.out::println);
    }

    @Override
    public String toString() {
        return "Department: " +
                departmentName +
                ", employees: " + employees +
                ", serviceCosts: " + serviceCosts;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Department other)) {
            return false;
        }

        if (!Objects.equals(this.departmentName, other.departmentName)) {
            return false;
        }

        return Objects.equals(this.employees, other.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentName, employees);
    }
}
