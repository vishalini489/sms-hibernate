package com.sms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sms")
public class Student {
    @Id
    @Column(name = "rollNumber")
    private int id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "courseName", length = 100)
    private String courseName;

    public Student() {
    }

    public Student(int id, String name, int age, String courseName) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.courseName = courseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void displayStudent() {
        System.out.println("------------------------------------");
        System.out.println("  Roll Number : " + id);
        System.out.println("  Name        : " + name);
        System.out.println("  Age         : " + age);
        System.out.println("  Course      : " + courseName);
        System.out.println("------------------------------------");
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', age=" + age
                + ", courseName='" + courseName + "'}";
    }
}
