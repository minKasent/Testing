package com.lab4.exception;

public class OrganizationNotFoundException extends RuntimeException {
    
    public OrganizationNotFoundException(Integer orgId) {
        super("Organization not found with ID: " + orgId);
    }
}
