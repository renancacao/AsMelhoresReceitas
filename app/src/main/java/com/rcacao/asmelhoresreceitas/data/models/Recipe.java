package com.rcacao.asmelhoresreceitas.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable{

    private int id;
    private String name;
    private Ingredient ingredients[];
    private Step steps[];
    private Integer servings;
    private String image;

    public Recipe() {
    }

    private Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArray(Ingredient.CREATOR);
        steps = in.createTypedArray(Step.CREATOR);
        if (in.readByte() == 0) {
            servings = null;
        } else {
            servings = in.readInt();
        }
        image = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Ingredient[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(Ingredient[] ingredients) {
        this.ingredients = ingredients;
    }

    public void setSteps(Step[] steps) {
        this.steps = steps;
    }

    public Step[] getSteps() {
        return steps;
    }

    public Integer getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedArray(ingredients, i);
        parcel.writeTypedArray(steps, i);
        if (servings == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(servings);
        }
        parcel.writeString(image);
    }


}
