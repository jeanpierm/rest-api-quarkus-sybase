package org.acme.customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomerService {

    final CustomerRepository customerRepository;

    public List<CustomerResponse> findAll() {
        try {
            List<Map<String, Object>> rows = customerRepository.findAll();
            return rows.stream()
                    .map(row -> CustomerMapper.rowToCustomerResponse(row))
                    .collect(Collectors.toList());
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public CustomerResponse findByDni(String dni) {
        try {
            List<Map<String, Object>> rows = customerRepository.findByDni(dni);
            return rows.stream()
                    .map(row -> CustomerMapper.rowToCustomerResponse(row))
                    .findFirst()
                    .orElseThrow(() -> new WebApplicationException("Customer not found",
                            Status.NOT_FOUND));
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
