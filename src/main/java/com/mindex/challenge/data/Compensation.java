package com.mindex.challenge.data;

import java.time.Instant;

public class Compensation {
    private Employee employee;
    private int salary;
    private Instant effectiveDate;

    public Compensation() {
    }

    public Compensation(Employee employee, int salary, Instant effectiveDate) {
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Instant getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Instant effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
