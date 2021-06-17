package com.example.registeration;

public class projectItem extends ProjectActivity{
    public String pHeader;
    private int pID;
    private String pName;
    private String pType;
    private String pStatus;
    private String pDesc;
    private String status;

    public projectItem(int pID, String pName, String pType, String pStatus, String pDesc, String status) {
        this.status = status;
        this.pHeader = "Project";
        this.pID = pID;
        this.pName = pName;
        this.pType = pType;
        this.pStatus = pStatus;
        this.pDesc = pDesc;
    }

    public int getpID() {
        return pID;
    }

    public void setpID(int pID) {
        this.pID = pID;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    @Override
    public String toString() {
        return pName + "/" +  pType + "/" + pStatus + "/" + pDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
