package com.example.test1212.RequestedValues;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class meta {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("last_report_at")
    @Expose
    private String lastReportAt;
    @SerializedName("last_energy_at")
    @Expose
    private String lastEnergyAt;
    @SerializedName("operational_at")
    @Expose
    private String operationalAt;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastReportAt() {
        return lastReportAt;
    }

    public void setLastReportAt(String lastReportAt) {
        this.lastReportAt = lastReportAt;
    }

    public String getLastEnergyAt() {
        return lastEnergyAt;
    }

    public void setLastEnergyAt(String lastEnergyAt) {
        this.lastEnergyAt = lastEnergyAt;
    }

    public String getOperationalAt() {
        return operationalAt;
    }

    public void setOperationalAt(String operationalAt) {
        this.operationalAt = operationalAt;
    }

}
