package com.example.pizzarecipes;

public class PizzaRecyclerItem {
    private String title;
    private String shortDescription;
    private String longDescription;
    private String imageURI;


    public PizzaRecyclerItem(String title, String shortDescription, String imageURI) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.imageURI = imageURI;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getImageURI() {
        return imageURI;
    }
}
