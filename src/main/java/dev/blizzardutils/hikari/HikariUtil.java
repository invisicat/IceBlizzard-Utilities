package dev.blizzardutils.hikari;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HikariUtil {

    private static HikariUtil instance = null;

    public static synchronized HikariUtil getInstance() {
        if (instance == null) instance = new HikariUtil();
        return instance;
    }
    public int get(String name, String getTable, String resultSetID) {
        try (Connection connection = /* */) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getTable)) {
                preparedStatement.setString(1, name);
                try (ResultSet rs = preparedStatement.executeQuery()) {
                    if (rs.next())
                        return rs.getInt(resultSetID);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public void set(String name, String getTable, String resultSetID, int amount, String insert, String update) {
        if (get(name, getTable, resultSetID) == -1) {
            try (Connection connection = /* */) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insert)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setInt(2, amount);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            try (Connection connection = /* */) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(update)) {
                    preparedStatement.setInt(1, amount);
                    preparedStatement.setString(2, name);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int add(String name, String getTable, String resultSetID, int amount, String insert, String update) {
        int current = get(name, getTable, resultSetID);
        int total = current + amount;
        set(name, getTable, resultSetID, total, insert, update);
        return total;
    }
}
