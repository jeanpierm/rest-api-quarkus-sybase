package org.acme.customer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.acme.DatasourceUtils;

import io.agroal.api.AgroalDataSource;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomerRepository {

    public static final String COLUMN_ID = "customer_id";
    public static final String COLUMN_DNI = "dni";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";

    private static final String DB_NAME = "gizlo_test";
    private static final String SP_NAME_SELECT_CUSTOMERS = "sp_select_customers";

    private static final String SP_PARAM_DNI = "@dni";

    final AgroalDataSource dataSource;
    final DatasourceUtils datasourceUtils;

    public List<CustomerResponse> findAll2() throws SQLException {
        Connection connection = null;
        CallableStatement cstmt = null;
        ResultSet resultSet = null;

        List<CustomerResponse> customers = new ArrayList<>();
        String sql = String.format("{call %s..%s()}", DB_NAME, SP_NAME_SELECT_CUSTOMERS);

        try {
            connection = dataSource.getConnection();
            cstmt = connection.prepareCall(sql);
            resultSet = cstmt.executeQuery();

            while (resultSet.next()) {
                CustomerResponse customer = new CustomerResponse();
                customer.setCustomerId(resultSet.getInt(COLUMN_ID));
                customer.setDni(resultSet.getString(COLUMN_DNI));
                customer.setName(resultSet.getString(COLUMN_NAME));
                customer.setSurname(resultSet.getString(COLUMN_SURNAME));
                customers.add(customer);
            }
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (cstmt != null)
                cstmt.close();
            if (connection != null)
                connection.close();
        }

        return customers;
    }

    public List<Map<String, Object>> findAll() throws SQLException {
        return datasourceUtils.executeStoredProcedure(SP_NAME_SELECT_CUSTOMERS);
    }

    public List<Map<String, Object>> findByDni(String dni) throws SQLException {
        return datasourceUtils.executeStoredProcedure(SP_NAME_SELECT_CUSTOMERS, Map.of(SP_PARAM_DNI, dni));
    }
}
