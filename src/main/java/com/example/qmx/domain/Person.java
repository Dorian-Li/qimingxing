package com.example.qmx.domain;

// Person.java
public class Person {
    private int age;
    private int gender;
    private double PMV;

    // 构造函数
    public Person() {}

    // Getter 和 Setter
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public double getPMV() {
        return PMV;
    }

    public void setPMV(double PMV) {
        this.PMV = PMV;
    }
}
