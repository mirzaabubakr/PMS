package com.example.registeration;

public class resourceItem extends ResourcesActivity{

    public String rHeader;
    private int rID;
    private String rName;
    private String rType;
    private String rStatus;
    private String rDesc;

    public resourceItem(int rID, String rName, String rType, String rStatus, String rDesc) {
        this.rHeader = "Resource";
        this.rID = rID;
        this.rName = rName;
        this.rType = rType;
        this.rStatus = rStatus;
        this.rDesc = rDesc;
    }

    public int getrID() {
        return rID;
    }

    public void setrID(int rID) {
        this.rID = rID;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getrType() {
        return rType;
    }

    public void setrType(String rType) {
        this.rType = rType;
    }

    public String getrStatus() {
        return rStatus;
    }

    public void setrStatus(String rStatus) {
        this.rStatus = rStatus;
    }

    public String getrDesc() {
        return rDesc;
    }

    public void setrDesc(String rDesc) {
        this.rDesc = rDesc;
    }

    @Override
    public String toString() {
        return rName + "/" +  rType + "/" + rStatus + "/" + rDesc;
    }
}
