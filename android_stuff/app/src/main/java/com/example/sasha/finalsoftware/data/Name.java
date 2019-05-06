package com.example.sasha.finalsoftware.data;


import org.json.JSONException;
import org.json.JSONObject;

public class Name {

    private String name;
    private int year;
    private double popularity;
    private Sex sex;
    private Rating rating;
    private TagStatus tagStatus;

    public Name() {
        this.setName("");
        this.setYear(0);
        this.setPopularity(0);
        this.setSex(Sex.NONE);
        this.setRating(Rating.NONE);
        this.setTagStatus(TagStatus.NOT_TAGGED);
    }

    public Name(Name otherName) {
        this.setSex(otherName.getSex());
        this.setYear(otherName.getYear());
        this.setPopularity(otherName.getPopularity());
        this.setRating(otherName.getRating());
        this.setTagStatus(otherName.getTagStatus());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPopularity() {
        return this.popularity;
    }

    public void setPopularity(double popularity) {
        if (popularity >= 0 && popularity <= 1) {
            this.popularity = popularity;
        } else {
            throw new IllegalArgumentException("Popularity must be within the range 0-1");
        }
    }

    public Sex getSex() {
        return this.sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Rating getRating() {
        return this.rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public TagStatus getTagStatus() {
        return this.tagStatus;
    }

    public void setTagStatus(TagStatus tagStatus) {
        this.tagStatus = tagStatus;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject nameJSON = new JSONObject();

        try {
            nameJSON.put("name", this.getName());
            nameJSON.put("year", this.getYear());
            nameJSON.put("popularity", this.getPopularity());
            nameJSON.put("sex", this.getSex());
        } catch (JSONException je) {
            throw new JSONException("Unexpected error in writing fields to JSONObject");
        }

        return nameJSON;
    }

    public void fromJSON(JSONObject res) throws JSONException {
        String fromJSONName;
        int fromJSONYear;
        double fromJSONPopularity;
        Sex fromJSONSex;

        try {
            fromJSONName = res.getString("name");
            fromJSONYear = res.getInt("year");
            fromJSONPopularity = res.getDouble("percent");
            fromJSONSex = Sex.fromString(res.getString("sex"));
        } catch (JSONException je) {
            throw new JSONException("The passed in JSONObject is not a valid JSON representation" +
                    " of a Name.");
        }

        if (res.length() == 4) {
            this.setName(fromJSONName);
            this.setYear(fromJSONYear);
            this.setPopularity(fromJSONPopularity);
            this.setSex(fromJSONSex);
        } else {
            throw new JSONException("The passed in JSONObject is not a valid JSON representation" +
                    " Name.");
        }
    }

    @Override
    public boolean equals(Object obj) {
        boolean ret = false;
        Name name = null;

        if (obj instanceof Name) {
            name = (Name) obj;
        } else {
            throw new IllegalArgumentException("The passed in value of obj is not an instance of" +
                    " Name");
        }
        if (name != null
                && (this.getName().equals(name.getName()))
                && (this.getYear() == name.getYear())
                && (this.getSex().equals(name.getSex()))) {
            ret = true;
        }

        return ret;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        hash = 83 * hash + this.getName().hashCode();
        hash = 83 * hash + this.getYear();
        hash = 83 * hash + this.getSex().hashCode();

        return hash;
    }

    public enum Sex {
        BOY("boy"),
        GIRL("girl"),
        NONE("none");

        private String sexString;

        Sex(String sexString) {
            this.sexString = sexString;
        }

        public static Sex fromString(String str) {
            Sex sex = NONE;

            switch (str) {
                case "male":
                    sex = BOY;
                    break;
                case "female":
                    sex = GIRL;
                    break;
                case "none":
                    sex = NONE;
                    break;
                default:
                    throw new IllegalArgumentException("Passed in value str must be boy, girl, or none.");
            }

            return sex;
        }

        @Override
        public String toString() {
            return sexString;
        }
    }


    public enum Rating {
        NONE,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE
    }

    public enum TagStatus {
        TAGGED,
        NOT_TAGGED
    }
}
