package com.example.melody.application.AutoTest;

public class Animal {
    private String name;
    private String type;
    private String favoriteFood;

    public Animal() {
        this.name = "";
        this.type = "";
        this.favoriteFood = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFavoriteFood() {
        return favoriteFood;
    }

    public void setFavoriteFood(String favoriteFood) {
        this.favoriteFood = favoriteFood;
    }

    public String testPublicFunction() {
        return "testPublicFunction";
    }

    public String testProtectedFunction() {
        return "testProtectedFunction";
    }

    private String testPrivateFunction() {
        return "testPrivateFunc";
    }

    public boolean testReturnBooleanFunction() {
        return true;
    }
}
