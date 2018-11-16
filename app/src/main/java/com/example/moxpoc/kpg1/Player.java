package com.example.moxpoc.kpg1;


public class Player  {
    private long id;
    private String firstName;
    private String secondName;

    public Player(long id, String firstName, String secondName){
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
}
