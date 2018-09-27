package com.example.dharmaniz.posmobileutilityapp.Models;

import java.io.Serializable;

/**
 * Created by dharmaniz on 5/9/18.
 */

public class SaleProductReciepeModel implements Serializable {


    private String inventoryProductName ="";
    private String qty ="";
    private String unitName ="";
    private String instructions ="";
    private String isCommon ="";
    private String product_id ="";

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getInventoryProductName() {
        return inventoryProductName;
    }

    public void setInventoryProductName(String inventoryProductName) {
        this.inventoryProductName = inventoryProductName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getIsCommon() {
        return isCommon;
    }

    public void setIsCommon(String isCommon) {
        this.isCommon = isCommon;
    }
}
