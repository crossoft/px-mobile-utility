package com.example.dharmaniz.posmobileutilityapp.Models;

import java.io.Serializable;

/**
 * Created by dharmaniz on 14/9/18.
 */

public class ShowRecipeDetailModel implements Serializable {
    private String salesProductId="";
    private String sizeName="";
    private String inventoryProductName="";
    private String unitName="";
    private String qty="";
    private String instructions="";

    public String getSalesProductId() {
        return salesProductId;
    }

    public void setSalesProductId(String salesProductId) {
        this.salesProductId = salesProductId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getInventoryProductName() {
        return inventoryProductName;
    }

    public void setInventoryProductName(String inventoryProductName) {
        this.inventoryProductName = inventoryProductName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
