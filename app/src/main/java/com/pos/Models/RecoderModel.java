package com.pos.Models;

import java.io.Serializable;
import java.security.SecureRandom;


public class RecoderModel  implements Serializable{


    private String name="" ;
    private String minLevel="" ;
    private String maxLevel ="";
    private String calculatedQty="" ;
    private String reorderQty ="";
    private String vendorName ="";
    private String inventoryProductId ="";
    private String accountId ="";
    private String reorderState ="";
    private String receivingInstructions ="";
    private String ingredientInstructions ="";
    private String vendorSku ="";
    private String maxDaysBetweenLogging ="";
    private String logDueDateOverride ="";
    private String primaryZone ="";
    private String primaryBin ="";
    private String secondaryZone ="";
    private String secondaryBin ="";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(String minLevel) {
        this.minLevel = minLevel;
    }

    public String getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(String maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getCalculatedQty() {
        return calculatedQty;
    }

    public void setCalculatedQty(String calculatedQty) {
        this.calculatedQty = calculatedQty;
    }

    public String getReorderQty() {
        return reorderQty;
    }

    public void setReorderQty(String reorderQty) {
        this.reorderQty = reorderQty;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getInventoryProductId() {
        return inventoryProductId;
    }

    public void setInventoryProductId(String inventoryProductId) {
        this.inventoryProductId = inventoryProductId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getReorderState() {
        return reorderState;
    }

    public void setReorderState(String reorderState) {
        this.reorderState = reorderState;
    }

    public String getReceivingInstructions() {
        return receivingInstructions;
    }

    public void setReceivingInstructions(String receivingInstructions) {
        this.receivingInstructions = receivingInstructions;
    }

    public String getIngredientInstructions() {
        return ingredientInstructions;
    }

    public void setIngredientInstructions(String ingredientInstructions) {
        this.ingredientInstructions = ingredientInstructions;
    }

    public String getVendorSku() {
        return vendorSku;
    }

    public void setVendorSku(String vendorSku) {
        this.vendorSku = vendorSku;
    }

    public String getMaxDaysBetweenLogging() {
        return maxDaysBetweenLogging;
    }

    public void setMaxDaysBetweenLogging(String maxDaysBetweenLogging) {
        this.maxDaysBetweenLogging = maxDaysBetweenLogging;
    }

    public String getLogDueDateOverride() {
        return logDueDateOverride;
    }

    public void setLogDueDateOverride(String logDueDateOverride) {
        this.logDueDateOverride = logDueDateOverride;
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
