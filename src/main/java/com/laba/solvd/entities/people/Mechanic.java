package com.laba.solvd.entities.people;

import java.time.LocalDate;
import java.util.Objects;

public class Mechanic extends Employee {
    private double overtimeHours;
    private double overtimeRate;
    private LocalDate hireDate;

    public Mechanic(String name, double baseSalary, double overtimeHours, double overtimeRate, String specialty, LocalDate hireDate) {
        super(name, specialty, baseSalary);
        this.overtimeHours = overtimeHours;
        this.overtimeRate = overtimeRate;
        this.hireDate = hireDate;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    @Override
    public double calculateTotalSalary() {
        double overtimePay = overtimeHours * overtimeRate;
        return baseSalary + overtimePay;
    }

    @Override
    public String toString() {
        return super.toString() + ", specialty: " + getSpecialty() + ", hire date: " + hireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mechanic mechanic)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(mechanic.overtimeHours, overtimeHours) == 0 && Double.compare(mechanic.overtimeRate, overtimeRate) == 0 && Objects.equals(getHireDate(), mechanic.getHireDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(overtimeHours, overtimeRate, getHireDate());
    }
}
