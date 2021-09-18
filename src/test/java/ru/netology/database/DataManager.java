package ru.netology.database;

import ru.netology.data.DataGenerator;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataManager {


    public static String getAuthCodeByLogin(String login) {
        var runner = new QueryRunner();
        var authSQL = "SELECT code FROM auth_codes WHERE created = (SELECT max(created) FROM auth_codes);";
        String authCode = null;
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "qwerty123");
        ) {
            var authResult = runner.query(conn, authSQL, new ScalarHandler<>());
            authCode = authResult.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authCode;
    }

    public static String getUserStatusByLogin(String login) {
        var runner = new QueryRunner();
        var statusSQL = "SELECT status FROM users WHERE login='" + login + "';";
        String status = null;
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "qwerty123");
        ) {
            var statusResult = runner.query(conn, statusSQL, new ScalarHandler<>());
            status = statusResult.toString().trim();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static void clearDatabase() {
        try (var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "qwerty123");
             Statement stmt = conn.createStatement();) {
            var disableChecks = "SET FOREIGN_KEY_CHECKS = 0;";
            var truncateAuth = "TRUNCATE TABLE auth_codes;";
            var truncateTransactions = "TRUNCATE TABLE card_transactions;";
            var truncateCards = "TRUNCATE TABLE cards;";
            var truncateUsers = "TRUNCATE TABLE users;";
            var activateChecks = "SET FOREIGN_KEY_CHECKS = 1;";
            stmt.executeUpdate(disableChecks);
            stmt.executeUpdate(truncateUsers);
            stmt.executeUpdate(truncateCards);
            stmt.executeUpdate(truncateAuth);
            stmt.executeUpdate(activateChecks);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


