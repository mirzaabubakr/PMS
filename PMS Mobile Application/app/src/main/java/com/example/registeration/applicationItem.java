package com.example.registeration;

public class applicationItem extends  ChangePasswordActivity{
    private int Id;
    private String Name;
    private String Request;
    private String date;
    private String reqName;
    private String desc;
    private String Reason;
    private String impact;

    public applicationItem(int id, String name, String request, String date, String reqName, String desc, String reason, String impact) {
        Id = id;
        Name = name;
        Request = request;
        this.date = date;
        this.reqName = reqName;
        this.desc = desc;
        Reason = reason;
        this.impact = impact;
    }




    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRequest() {
        return Request;
    }

    public void setRequest(String request) {
        Request = request;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String toString() {
        return Name + "/" +  Request + "/" + date + "/" + reqName + "/" + desc + "/" + Reason + "/" + impact;
    }

}
