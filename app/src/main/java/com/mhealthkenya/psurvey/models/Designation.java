package com.mhealthkenya.psurvey.models;

public class Designation {

    private int id;
    private String name;


    public Designation(int id,  String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public boolean equals(Object anotherObject) {
        if (!(anotherObject instanceof Designation)) {
            return false;
        }
        Designation d = (Designation) anotherObject;
        return (this.id == d.id);
    }


}
