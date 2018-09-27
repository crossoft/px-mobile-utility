package com.example.dharmaniz.posmobileutilityapp.Models;

import java.io.Serializable;

/**
 * Created by dharmaniz on 4/9/18.
 */

public class LocationModel implements Serializable {

    private String primaryZone="" ;
    private String primaryBin = "";
    private String secondaryZone="";
    private String secondaryBin ="";
    private String name ="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrimaryZone() {
        return primaryZone;
    }

    public void setPrimaryZone(String primaryZone) {
        this.primaryZone = primaryZone;
    }

    public String getPrimaryBin() {
        return primaryBin;
    }

    public void setPrimaryBin(String primaryBin) {
        this.primaryBin = primaryBin;
    }

    public String getSecondaryZone() {
        return secondaryZone;
    }

    public void setSecondaryZone(String secondaryZone) {
        this.secondaryZone = secondaryZone;
    }

    public String getSecondaryBin() {
        return secondaryBin;
    }

    public void setSecondaryBin(String secondaryBin) {
        this.secondaryBin = secondaryBin;
    }
}
