package com.example.dharmaniz.posmobileutilityapp.Models;

import java.io.Serializable;

/**
 * Created by dharmaniz on 5/9/18.
 */

public class InformationModel implements Serializable {

    private String salesProductName="";
    private String sizeName="";
    private String price="";
    private String description="";
    private String salesProductId="";

    public String getSalesProductId() {
        return salesProductId;
    }

    public void setSalesProductId(String salesProductId) {
        this.salesProductId = salesProductId;
    }

    public String getSalesProductName() {
        return salesProductName;
    }

    public void setSalesProductName(String salesProductName) {
        this.salesProductName = salesProductName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
