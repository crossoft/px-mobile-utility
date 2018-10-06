package com.pos.Models;

import java.io.Serializable;

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
