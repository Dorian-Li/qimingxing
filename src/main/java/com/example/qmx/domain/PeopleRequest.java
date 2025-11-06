package com.example.qmx.domain;

// PeopleRequest.java
import java.util.List;

public class PeopleRequest {
    private int numberOfPeople;
    private List<Person> people;

    // 构造函数
    public PeopleRequest() {}

    // Getter 和 Setter
    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }
}
