package ru.netology.diplomaprogect.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;

public class SQLHelper {


    private SQLHelper() {
    }

    private static final String url = "jdbc:mysql://localhost:3306/app";
    private static final String user = "app";
    private static final String password = "pass";

    @SneakyThrows
    public static void cleanDatabase() {

        String deleteCreditRequestEntity = "DELETE FROM credit_request_entity";
        String deleteOrderEntity = "DELETE FROM order_entity";
        String deletePaymentEntity = "DELETE FROM payment_entity";
        QueryRunner runner = new QueryRunner();

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            runner.update(conn, deleteCreditRequestEntity);
            runner.update(conn, deleteOrderEntity);
            runner.update(conn, deletePaymentEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        val codesSQL = "SELECT status FROM payment_entity";
        return getData(codesSQL);
    }

    @SneakyThrows
    public static String getCreditStatus() {
        val codesSQL = "SELECT status FROM credit_request_entity;";
        return getData(codesSQL);
    }

    private static String getData(String query) {
        String data = "";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)) {
            data = runner.query(conn, query, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    @SneakyThrows
    public static String getOrderCount() {
        Long count = null;
        val codesSQL = "SELECT COUNT(*) FROM order_entity;";
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)) {
            count = runner.query(conn, codesSQL, new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Long.toString(count);
    }
}