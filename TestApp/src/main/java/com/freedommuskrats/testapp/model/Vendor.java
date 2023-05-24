package com.freedommuskrats.testapp.model;

public record Vendor(
        String vendorIdentifier,
        String name,
        String description,
        Address address,
        String phoneNumber,
        String email
) {
}
