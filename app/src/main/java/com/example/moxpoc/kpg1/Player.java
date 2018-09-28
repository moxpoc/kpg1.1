package com.example.moxpoc.kpg1;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {
    private String firstName;
    private String secondName;

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel source) {
            String firstName = source.readString();
            String secondname = source.readString();
            return new Player(firstName,secondname);

        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public Player(String firstName, String secondName){
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeString(firstName);
        parcel.writeString(secondName);
    }
}
