package com.pos.Models;

import java.io.Serializable;


public class MetricModel implements Serializable {

    private String accountId ="";
    private String metric ="";
    private String result="" ;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
