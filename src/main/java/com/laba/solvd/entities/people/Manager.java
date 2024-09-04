package com.laba.solvd.entities.people;

import java.time.LocalDate;
import java.util.Objects;

public class Manager extends Employee {
    private double bonus;
    private LocalDate hireDate;

    public Manager(String name, String specialty, double baseSalary, double bonus, LocalDate hireDate) {
        super(name, specialty, baseSalary);
        this.bonus = bonus;
        this.hireDate = hireDate;
    }

    @Override
    public double calculateSalary() {
        return baseSalary + bonus;
    }

    @Override
    public String toString() {
        return super.toString() + ", hire date: " + hireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manager manager)) return false;
        if (!super.equals(o)) return false;
        return Double.compare(manager.bonus, bonus) == 0 && Objects.equals(hireDate, manager.hireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bonus, hireDate);
    }
}
