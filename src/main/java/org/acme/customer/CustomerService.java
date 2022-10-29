package org.acme.customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

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
}
