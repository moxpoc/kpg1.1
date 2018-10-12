package com.example.moxpoc.kpg1;

import android.os.Parcel;
import android.os.Parcelable;

public class Player  {
    private long id;
    private String firstName;
    private String secondName;

   /* public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel source) {
            long id = source.readLong();
            String firstName = source.readString();
            String secondName = source.readString();
            return new Player(id,firstName,secondName);

        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };*/

    Player(long id, String firstName, String secondName){
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public long getId() {
        return id;
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
    public String toString(){
        return this.firstName + " " + this.secondName;
    }
  /*  @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel parcel, int flags){
        parcel.writeString(firstName);
        parcel.writeString(secondName);
    }*/
}
