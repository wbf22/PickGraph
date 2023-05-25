package org.pickgraph.annotations.testObjects.fromDemo;

import org.pickgraph.annotations.PickGraphObject;

@PickGraphObject
public class Vendor{
    String vendorIdentifier;
    public String name;
    public String description;
    public Address address;
    public String phoneNumber;
    public String email;

    public Vendor() {

    }

    public Vendor(String vendorIdentifier, String name, String description, Address address, String phoneNumber, String email) {
        this.vendorIdentifier = vendorIdentifier;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getVendorIdentifier() {
        return vendorIdentifier;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
