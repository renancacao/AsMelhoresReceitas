package com.rcacao.asmelhoresreceitas.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements RecipeItem, Parcelable{

    private double quantity;
    private String measure;
    private String ingredient;

    private Ingredient(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public String getType() {
        return RecipeItem.TYPE_INGREDIENT;
    }

    @Override
    public int getId() {
        return -1;
    }

    @Override
    public String getText() {
        return ingredient;
    }

    @Override
    public String getTextAux1() {
        return String.valueOf(quantity);
    }

    @Override
    public String getTextAux2() {
        return measure;
    }


    @Override
    public String getImageUrl() {
        return null;
    }

    @Override
    public String getVideoUrl() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }
}
