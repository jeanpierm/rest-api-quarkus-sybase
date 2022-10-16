package org.acme.customer;

import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomerService {

    final CustomerRepository customerRepository;

    public List<Customer> findAll() {
        try {
            return customerRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
