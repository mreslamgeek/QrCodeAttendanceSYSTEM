package com.eslam.qrcodeattendancesystem.models;

public class UserModel {
    String firstname, lastname, accountType, email, user_ID;

    public UserModel(String firstname, String lastname, String accountType, String email, String user_ID) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.accountType = accountType;
        this.email = email;
        this.user_ID = user_ID;
    }

    public UserModel() {
    }

    public String getUser_ID() { return user_ID; }

    public void setUser_ID(String user_ID) {this.user_ID = user_ID;}

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
