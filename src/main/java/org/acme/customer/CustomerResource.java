package org.acme.customer;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.reactive.RestPath;

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

    @POST
    public CustomerResponse findByDni(GetCustomerByDniRequest request) {
        return customerService.findByDni(request.getDni());
    }

    @GET
    @Path("{id}")
    public CustomerResponse findById(@RestPath Integer id) {
        return customerService.findById(id);
    }
}
