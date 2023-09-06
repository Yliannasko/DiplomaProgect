package ru.netology.diplomaprogect.data;

import org.apache.commons.dbutils.QueryRunner;

import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;

public class SQLHelper {

    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");

    public static void cleanDatabase() throws SQLException {
        String deleteCreditRequestEntity = "DELETE FROM credit_request_entity";
        String deleteOrderEntity = "DELETE FROM order_entity";
        String deletePaymentEntity = "DELETE FROM payment_entity";
        QueryRunner runner = new QueryRunner();

        Connection conn = DriverManager.getConnection(url, user, password);
        runner.update(conn, deleteCreditRequestEntity);
        runner.update(conn, deleteOrderEntity);
        runner.update(conn, deletePaymentEntity);
        conn.close();
    }

    public static String getPaymentStatus() throws SQLException {
        String codesSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        return getData(codesSQL);
    }

    public static String getCreditStatus() throws SQLException {
        String codesSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        return getData(codesSQL);
    }

    public static long getOrderCount() throws SQLException {
        String codesSQL = "SELECT COUNT(*) FROM order_entity;";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            QueryRunner runner = new QueryRunner();
            Long count = runner.query(conn, codesSQL, new ScalarHandler<>());
            return count;
        }
    }

    private static String getData(String query) throws SQLException {
        QueryRunner runner = new QueryRunner();
        String data = "";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            data = runner.query(conn, query, new ScalarHandler<>());
        }
        return data;
    }
}
