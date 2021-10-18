package com.example.material_recyclerview;

public class RecyclerItem {
    private int imageResource;
    private String title;
    private String text;

    public RecyclerItem(int imageResource, String title, String text) {
        this.imageResource = imageResource;
        this.title = title;
        this.text = text;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
}
