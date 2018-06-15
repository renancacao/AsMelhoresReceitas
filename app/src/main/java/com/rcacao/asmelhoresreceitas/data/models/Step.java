package com.rcacao.asmelhoresreceitas.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements ListItem, Parcelable {

    private int id;
    private String shortDescription;
    private String description;
    private String videoURL;
    private String thumbnailURL;

    private Step(Parcel in) {
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
        return ListItem.TYPE_STEP;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return shortDescription;
    }

    @Override
    public String getAuxText1() {
        return videoURL;
    }

    @Override
    public String getAuxText2() {
        return thumbnailURL;
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
