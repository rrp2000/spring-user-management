package com.example.saveUser.model;

import jakarta.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


//username, full name, email, address, mobile number, current organization
@Document(collection = "User")
public class UserModel {
    @Id
    private String id;
    @NotBlank(message = "User Name is required.")
    private String userName;
    @NotBlank(message = "Full Name is required.")
    private String full_name;
    @NotBlank(message = "Email is required.")
    @Email
    private String email;
    @NotBlank(message = "Address is required.")
    private String address;
    @NotBlank(message = "Mobile Number is required.")
    @Size(min = 10,max = 10,message = "Mobile Number should be of 10 digits.")
    private String mobile_number;
    @NotBlank(message = "Organization is required.")
    private String current_organizations;

    public UserModel(String id, String userName, String full_name, String email, String address, String mobile_number, String current_organizations) {
        this.id = id;
        this.userName = userName;
        this.full_name = full_name;
        this.email = email;
        this.address = address;
        this.mobile_number = mobile_number;
        this.current_organizations = current_organizations;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getCurrent_organizations() {
        return current_organizations;
    }

    public void setCurrent_organizations(String current_organizations) {
        this.current_organizations = current_organizations;
    }
}
