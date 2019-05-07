package com.example.sasha.finalsoftware.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Name {
    private String name;
    private String sex;
    private String id;
    private List<Double> popularity;

    public Name() {
        //Default Constructor
    }

    public Name(String name, String sex, String id, ArrayList<Double> popularity) {
        this.name = name;
        this.sex = sex;
        this.popularity = popularity;
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

    public List<Double> getPopularity() {
        return popularity;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", this.id);
            obj.put("name", this.name);
            obj.put("sex", this.sex);
            JSONArray popularityArray = new JSONArray(this.popularity);
            obj.put("popularity", popularityArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}

