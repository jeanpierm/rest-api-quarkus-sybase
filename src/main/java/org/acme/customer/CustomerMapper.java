package org.acme.customer;

import java.util.Map;

public class CustomerMapper {

    static public CustomerResponse rowToCustomerResponse(Map<String, Object> row) {
        CustomerResponse customer = new CustomerResponse();
        customer.setCustomerId((Integer) row.get(CustomerRepository.COLUMN_ID));
        customer.setDni((String) row.get(CustomerRepository.COLUMN_DNI));
        customer.setName((String) row.get(CustomerRepository.COLUMN_NAME));
        customer.setSurname((String) row.get(CustomerRepository.COLUMN_SURNAME));
        return customer;
    }
}
