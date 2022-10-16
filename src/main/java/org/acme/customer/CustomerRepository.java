package org.acme.customer;

import io.agroal.api.AgroalDataSource;
import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomerRepository {

    final AgroalDataSource dataSource;

    public List<Customer> findAll() throws SQLException {
        Connection connection = null;
        CallableStatement cstmt = null;
        ResultSet resultSet = null;

        List<Customer> customers = new ArrayList<>();

        try {
            connection = dataSource.getConnection();
            cstmt = connection.prepareCall("{call gizlo_test..sp_select_customers()}");
            resultSet = cstmt.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt(Customer.COLUMN_ID));
                customer.setName(resultSet.getString(Customer.COLUMN_NAME));
                customers.add(customer);
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (cstmt != null) cstmt.close();
            if (connection != null) connection.close();
        }

        return customers;
    }
}
