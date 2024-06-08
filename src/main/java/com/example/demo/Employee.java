package com.example.demo;

import java.text.SimpleDateFormat;

public  class Employee {
    private String firstName;
    private String lastName;
    private String Position;
   // SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
    private int Salary;
    private int age;

    public Employee(String firstName, String lastName,
                    String position,int Salary, int age) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.Position = position;
        this.Salary = Salary;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public int getAge() {
        return age;
    }

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    public void setAge(int age) {
        this.age = age;
    }
}