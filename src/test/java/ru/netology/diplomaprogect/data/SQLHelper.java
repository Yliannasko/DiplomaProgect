package ru.netology.diplomaprogect.data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.StatementConfiguration;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.*;

public class SQLHelper {

    private static final String url = System.getProperty("db.url");
    private static final String user = System.getProperty("db.user");
    private static final String password = System.getProperty("db.password");

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
//        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        var codesSQL = "SELECT status FROM payment_entity";
        return getData(codesSQL);
    }

    @SneakyThrows
    public static String getCreditStatus() {
        var codesSQL = "SELECT status FROM credit_request_entity;";
        return getData(codesSQL);
    }
    @SneakyThrows
    public static String getOrderCount() {
//        QueryRunner runner = new QueryRunner();
//        Long count = null;
        var codesSQL = "SELECT COUNT(*) FROM order_entity;";
//        try (var conn = DriverManager.getConnection(url, user, password)) {
//            count = runner.query(conn, codesSQL, new ScalarHandler<>());
//        Long count = null;
//        var codesSQL = "SELECT COUNT(*) FROM order_entity;";
//        var runner = new QueryRunner();
//        try (var conn = DriverManager.getConnection(url, user, password)) {
//            count = runner.query(conn, codesSQL, new ScalarHandler<>());
//        } catch (SQLException e) {
//            e.printStackTrace();

      //  return Long.toString(count);
        return getData(codesSQL);
    }

    @SneakyThrows
    private static String getData(String query) {
        QueryRunner runner = new QueryRunner();
        String data = "";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            data = runner.query(conn, query, new ScalarHandler<>());
        }
//       try (var conn = DriverManager.getConnection(url, user, password)) {
//            data = runner.query(conn, query, new ScalarHandler<>());
//       } catch (SQLException e){
//            e.printStackTrace();
//    }
        return data;
    }
}