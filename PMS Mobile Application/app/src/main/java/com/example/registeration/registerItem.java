package com.example.registeration;

public class registerItem extends ListActivity {
    public String Header;
    private int ID;
    private String Name;
    private String Email;
    private String phNum;
    private String Password;
    private String Address;

    public registerItem(int id,String name, String email, String phNum, String password, String address) {
        Header = "Register";
        ID = id;
        Name = name;
        Email = email;
        this.phNum = phNum;
        Password = password;
        Address = address;
    }

    public registerItem(String str) {
        super();
        String[] infos = str.split("/");

        Name = infos[0];
        Email = infos[1];
        phNum = infos[2];
        Password = infos[3];
        Address = infos[4];

    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhNum() {
        return phNum;
    }

    public void setPhNum(String phNum) {
        this.phNum = phNum;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }


    @Override
    public String toString() {
        return Name + "/" +  Email + "/" + phNum + "/" + Password + "/" + Address;

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
