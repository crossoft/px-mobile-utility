package com.pos.Models;

import java.io.Serializable;


public class TimeKeepingModel implements Serializable {


    private String accountId="" ;
    private String userId ="";
    private String lastName ="";
    private String firstName ="";
    private String clockInDateTime ="";

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getClockInDateTime() {
        return clockInDateTime;
    }

    public void setClockInDateTime(String clockInDateTime) {
        this.clockInDateTime = clockInDateTime;
    }
}
