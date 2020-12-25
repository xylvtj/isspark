package com.demo.poi;

public class EnergyConsumptionGrade {
    public String firstGrade = "";
    public String secondGrade = "";
    public String thirdGrade = "";
    public String fourthGrade = "";
    public String finalGrade = "";//末级分项
    public String deviceId = "";



    public String getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(String finalGrade) {
        this.finalGrade = finalGrade;
    }

    public String getFirstGrade() {
        return firstGrade;
    }

    public void setFirstGrade(String firstGrade) {
        this.firstGrade = firstGrade;
    }

    public String getSecondGrade() {
        return secondGrade;
    }

    public void setSecondGrade(String secondGrade) {
        this.secondGrade = secondGrade;
    }

    public String getThirdGrade() {
        return thirdGrade;
    }

    public void setThirdGrade(String thirdGrade) {
        this.thirdGrade = thirdGrade;
    }

    public String getFourthGrade() {
        return fourthGrade;
    }

    public void setFourthGrade(String fourthGrade) {
        this.fourthGrade = fourthGrade;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
