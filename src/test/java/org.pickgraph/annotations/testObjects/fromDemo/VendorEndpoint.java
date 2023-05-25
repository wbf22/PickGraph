package org.pickgraph.annotations.testObjects.fromDemo;

import org.pickgraph.annotations.PickEndpoint;
import org.pickgraph.annotations.PickGraphMapping;
import org.springframework.stereotype.Controller;

@Controller
public class VendorEndpoint {


    @PickEndpoint
    @PickGraphMapping
    public Vendor getVendor() {
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
