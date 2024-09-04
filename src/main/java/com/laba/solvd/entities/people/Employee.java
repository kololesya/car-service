package com.laba.solvd.entities.people;

public abstract class Employee extends Person {
    double baseSalary;
    private String specialty;

    protected Employee(String name, String specialty, double salary) {
        super(name);
        this.specialty = specialty;
        this.baseSalary = salary;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public double getSalary() {return baseSalary;}

    public void setSalary(double salary) {
        this.baseSalary = salary;
    }

    public abstract double calculateSalary();

    @Override
    public String toString() {

        return "Employee by name: " + getName() +
                " has salary: " + baseSalary;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return Double.compare(employee.baseSalary, baseSalary) == 0 && getName().equals(employee.getName());
    }
}
