package com.laba.solvd.entities.people;

import com.laba.solvd.entities.Named;

public abstract class Person implements Named {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(String name) {
        this.name = name;
    }
}
