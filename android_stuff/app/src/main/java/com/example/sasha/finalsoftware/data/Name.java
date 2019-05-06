package com.example.sasha.finalsoftware.data;

import java.util.ArrayList;
import java.util.List;

public class Name {
    private String name;
    private String sex;
    private List<Double> popularityList;

    public Name(String name, String sex, ArrayList<Double> popularityList) {
        this.name = name;
        this.sex = sex;
        this.popularityList = popularityList;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public List<Double> getPopularityList() {
        return popularityList;
    }
}

