package com.lab4.exception;

public class OrganizationNameExistsException extends RuntimeException {
    
    public OrganizationNameExistsException(String orgName) {
        super("Organization Name already exists: " + orgName);
    }
}
