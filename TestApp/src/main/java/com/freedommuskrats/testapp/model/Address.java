package com.freedommuskrats.testapp.model;

public record Address(
        String street,
        String city,
        String state,
        String zipCode
) {
}
