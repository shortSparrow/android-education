package com.example.pizzarecipes;

import android.os.Parcel;
import android.os.Parcelable;

public class PizzaRecyclerItem implements Parcelable {
    private String title;
    private String shortDescription;
    private String imageURI;
    private Integer price;


    public PizzaRecyclerItem(String title, String shortDescription, String imageURI, Integer price) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.imageURI = imageURI;
        this.price = price;
    }

    protected PizzaRecyclerItem(Parcel in) {
        title = in.readString();
        shortDescription = in.readString();
        imageURI = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readInt();
        }
    }

    public static final Creator<PizzaRecyclerItem> CREATOR = new Creator<PizzaRecyclerItem>() {
        @Override
        public PizzaRecyclerItem createFromParcel(Parcel in) {
            return new PizzaRecyclerItem(in);
        }

        @Override
        public PizzaRecyclerItem[] newArray(int size) {
            return new PizzaRecyclerItem[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getImageURI() {
        return imageURI;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(shortDescription);
        dest.writeString(imageURI);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(price);
        }
    }
}
