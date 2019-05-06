package com.example.sasha.finalsoftware.data;

import java.util.ArrayList;
import java.util.List;

public class Name {
    private String name;
    private String sex;
    private String id;
    private List<Double> popularityList;

    public Name() {
        //Default Constructor
    }

    public Name(String name, String sex, String id, ArrayList<Double> popularityList) {
        this.name = name;
        this.sex = sex;
        this.popularityList = popularityList;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String id() {
        return id;
    }

    public List<Double> getPopularityList() {
        return popularityList;
    }

    public void setId(String id) {
        this.id = id;
    }
}

