package org.acme.customer;

import java.sql.SQLException;
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

    public List<Map<String, Object>> findAll() throws SQLException {
        return datasourceUtils.executeStoredProcedure(SP_NAME_SELECT_CUSTOMERS);
    }

    public List<Map<String, Object>> findByDni(String dni) throws SQLException {
        return datasourceUtils.executeStoredProcedure(SP_NAME_SELECT_CUSTOMERS, Map.of(SP_PARAM_DNI, dni));
    }
}
