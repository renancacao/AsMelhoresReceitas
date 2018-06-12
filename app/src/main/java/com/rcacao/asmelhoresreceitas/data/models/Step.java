package com.rcacao.asmelhoresreceitas.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements RecipeItem, Parcelable {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    protected Step(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    @Override
    public String getType() {
        return RecipeItem.TYPE_STEP;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getText() {
        return shortDescription;
    }

    @Override
    public String getTextAux1() {
        return String.valueOf(id);
    }

    @Override
    public String getTextAux2() {
        return null;
    }


    @Override
    public String getImageUrl() {
        return thumbnailURL;
    }

    @Override
    public String getVideoUrl() {
        return videoURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(shortDescription);
        parcel.writeString(description);
        parcel.writeString(videoURL);
        parcel.writeString(thumbnailURL);
    }
}
