package com.rcacao.asmelhoresreceitas.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient implements ListItem, Parcelable{

    private double quantity;
    private String measure;
    private String ingredient;

    public void setQuantity(String quantity) {
        this.quantity = Double.parseDouble(quantity);
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public Ingredient() {
    }

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
        return ListItem.TYPE_INGREDIENT;
    }

    @Override
    public int getId() {
        return -1;
    }

    @Override
    public String getTitle() {
        return getIngredient();
    }

    @Override
    public String getAuxText1() {
        return String.valueOf(getQuantity());
    }

    @Override
    public String getAuxText2() {
        return getMeasure();
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
