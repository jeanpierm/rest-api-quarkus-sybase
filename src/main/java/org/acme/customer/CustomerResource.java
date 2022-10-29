package org.acme.customer;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.RequiredArgsConstructor;

@Path("/api/v1.0/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class CustomerResource {

    final CustomerService customerService;

    @GET
    public List<CustomerResponse> findAll() {
        return customerService.findAll();
    }
}
