package com.freedommuskrats.testapp.controller;


import com.freedommuskrats.annotations.PickEndpoint;
import com.freedommuskrats.testapp.model.Address;
import com.freedommuskrats.testapp.model.Vendor;

public class VendorController {


    @PickEndpoint(path = "/vendor")
    public Vendor getVendor(String vendorIdentifier) {
        return new Vendor(
                "ven_2kk2k3j4dikdf2",
                "Acme Corp",
                "Acme Corp is a company that makes things.",
                new Address(
                        "123 Main St",
                        "Anytown",
                        "NY",
                        "12345"
                ),
                "555-555-5555",
                "ceryl@acmecorp.com"
        );
    }

}
