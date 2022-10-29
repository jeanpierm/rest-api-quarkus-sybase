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

import org.jboss.logging.Logger;

import io.agroal.api.AgroalDataSource;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class DatasourceUtils {

    private static final String DB_NAME = "gizlo_test";

    final AgroalDataSource dataSource;
    final Logger logger;

    public List<Map<String, Object>> executeStoredProcedure(String spName)
            throws SQLException {
        return executeStoredProcedure(spName, new HashMap<>());
    }

    public List<Map<String, Object>> executeStoredProcedure(String spName, Map<String, Object> parameters)
            throws SQLException {
        List<Map<String, Object>> rows = new ArrayList<>();
        Connection connection = null;
        CallableStatement cs = null;
        ResultSet rs = null;
        int parameterCount = parameters == null ? 0 : parameters.size();
        String sql = buildSqlCallSp(parameterCount, spName);

        try {
            connection = dataSource.getConnection();
            cs = connection.prepareCall(sql);

            if (parameterCount != 0) {
                setSpParameters(parameters, cs);
            }

            rs = cs.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                rows.add(row);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (cs != null)
                cs.close();
            if (connection != null)
                connection.close();
        }

        return rows;
    }

    private void setSpParameters(Map<String, Object> parameters, CallableStatement cs) throws SQLException {
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            cs.setObject(entry.getKey(), entry.getValue());
        }
    }

    private String buildSqlCallSp(int parameterCount, String spName) {
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("{call %s..%s(");
        for (int i = 0; i < parameterCount; i++) {
            boolean isTheLast = i == parameterCount - 1;
            if (isTheLast) {
                sqlStringBuilder.append("?");
            } else {
                sqlStringBuilder.append("?,");
            }
        }
        sqlStringBuilder.append(")}");

        String sql = String.format(sqlStringBuilder.toString(), DB_NAME, spName);
        logger.info("sql prepared -> " + sql);
        return sql;
    }
}
