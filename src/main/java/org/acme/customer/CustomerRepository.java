package org.acme.customer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.acme.DataSourceUtils;

import io.agroal.api.AgroalDataSource;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomerRepository {

    public static final String COLUMN_ID = "customer_id";
    public static final String COLUMN_DNI = "dni";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";

    private static final String SP_NAME_SELECT_CUSTOMERS = "sp_select_customers";
    private static final String SP_NAME_SELECT_CUSTOMERS_BY_DNI = "sp_select_customers_by_dni";
    private static final String SP_NAME_SELECT_CUSTOMERS_BY_ID = "sp_select_customers_by_id";
    private static final String SP_PARAM_DNI = "@dni";
    private static final String SP_PARAM_ID = "@customer_id";

    final AgroalDataSource dataSource;
    final DataSourceUtils dataSourceUtils;

    public List<Map<String, Object>> findAll() throws SQLException {
        return dataSourceUtils.executeStoredProcedure(SP_NAME_SELECT_CUSTOMERS);
    }

    public List<Map<String, Object>> findByDni(String dni) throws SQLException {
        return dataSourceUtils.executeStoredProcedure(SP_NAME_SELECT_CUSTOMERS_BY_DNI, Map.of(SP_PARAM_DNI, dni));
    }

    public List<Map<String, Object>> findById(Integer id) throws SQLException {
        return dataSourceUtils.executeStoredProcedure(SP_NAME_SELECT_CUSTOMERS_BY_ID, Map.of(SP_PARAM_ID, id));
    }
}
