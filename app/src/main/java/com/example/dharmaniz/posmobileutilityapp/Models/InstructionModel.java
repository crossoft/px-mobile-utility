package com.example.dharmaniz.posmobileutilityapp.Models;

import java.io.Serializable;

/**
 * Created by dharmaniz on 4/9/18.
 */

public class InstructionModel implements Serializable {
    private  String receivingInstructions="";
    private  String name="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReceivingInstructions() {
        return receivingInstructions;
    }

    public void setReceivingInstructions(String receivingInstructions) {
        this.receivingInstructions = receivingInstructions;
    }
}
