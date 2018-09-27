package com.example.dharmaniz.posmobileutilityapp.Models;

import java.io.Serializable;

/**
 * Created by dharmaniz on 5/9/18.
 */

public class SalesProductModel implements Serializable {

    private String accountId ="";
    private String salesProductId ="";
    private String name ="";
    private String description ="";

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSalesProductId() {
        return salesProductId;
    }

    public void setSalesProductId(String salesProductId) {
        this.salesProductId = salesProductId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
