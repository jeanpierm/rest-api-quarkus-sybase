package org.acme;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import io.agroal.api.AgroalDataSource;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class DatasourceUtils {

    private static final String DB_NAME = "gizlo_test";

    final AgroalDataSource dataSource;

    public List<Map<String, Object>> executeStoredProcedure(String spName) throws SQLException {

        List<Map<String, Object>> rows = new ArrayList<>();
        Connection connection = null;
        CallableStatement cstmt = null;
        ResultSet resultSet = null;
        String sql = String.format("{call %s..%s()}", DB_NAME, spName);

        try {
            connection = dataSource.getConnection();
            cstmt = connection.prepareCall(sql);
            resultSet = cstmt.executeQuery();
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(rsmd.getColumnName(i), resultSet.getObject(i));
                }
                rows.add(row);
            }
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (cstmt != null)
                cstmt.close();
            if (connection != null)
                connection.close();
        }

        return rows;
    }
}
