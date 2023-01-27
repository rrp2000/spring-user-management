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
    private String fullName;
    @NotBlank(message = "Email is required.")
    @Email
    private String email;
    @NotBlank(message = "Address is required.")
    private String address;
    @NotBlank(message = "Mobile Number is required.")
    @Size(min = 10,max = 10,message = "Mobile Number should be of 10 digits.")
    private String mobileNumber;
    @NotBlank(message = "Organization is required.")
    private String currentOrganizations;

    public UserModel(String id, String userName, String fullName, String email, String address, String mobileNumber, String currentOrganizations) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.currentOrganizations = currentOrganizations;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCurrentOrganizations() {
        return currentOrganizations;
    }

    public void setCurrentOrganizations(String currentOrganizations) {
        this.currentOrganizations = currentOrganizations;
    }
}
