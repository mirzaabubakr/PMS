package com.example.registeration;

public class changeprojectItem extends  ChangeProjectTAActivity{

    public String Header;
    private int ID;
    private String cpName;
    private String requestBy;
    private String date;
    private String cpReqName;
    private String cpDesc;
    private String cpReason;
    private String cpImpact;

    public changeprojectItem(int id, String cpName, String requestBy, String date, String cpReqName, String cpDesc, String cpReason, String cpImpact) {
        this.Header = "CPTA";
        ID = id;
        this.cpName = cpName;
        this.requestBy = requestBy;
        this.date = date;
        this.cpReqName = cpReqName;
        this.cpDesc = cpDesc;
        this.cpReason = cpReason;
        this.cpImpact = cpImpact;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCpName() {
        return cpName;
    }

    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    public String getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(String requestBy) {
        this.requestBy = requestBy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCpReqName() {
        return cpReqName;
    }

    public void setCpReqName(String cpReqName) {
        this.cpReqName = cpReqName;
    }

    public String getCpDesc() {
        return cpDesc;
    }

    public void setCpDesc(String cpDesc) {
        this.cpDesc = cpDesc;
    }

    public String getCpReason() {
        return cpReason;
    }

    public void setCpReason(String cpReason) {
        this.cpReason = cpReason;
    }

    public String getCpImpact() {
        return cpImpact;
    }

    public void setCpImpact(String cpImpact) {
        this.cpImpact = cpImpact;
    }

    @Override
    public String toString() {
        return cpName + "/" +  requestBy + "/" + date + "/" + cpReqName + "/" + cpDesc + "/" + cpReason +"/"+ cpImpact;
    }

}
