package dev.blizzardutils.hikari;

import com.zaxxer.hikari.HikariDataSource;
import dev.blizzardutils.example.ExamplePlugin;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HikariSetup {
    private HikariDataSource dataSource;
    private final Configuration config = JavaPlugin.getPlugin(ExamplePlugin.class).getConfig();

    public void connectToDatabase() {
        dataSource = new HikariDataSource();
        setDataSourceSettings();
        registerDefaultTables();
    }

    /**
     * Register tables in the database
     */
    private void registerDefaultTables() {
        // Register tables

    }

    /**
     * Create a table
     * @param tableName Prepared Statement
     */
    private void createTable(String tableName) {
        try(Connection conn = dataSource.getConnection()) {
            try(PreparedStatement statement = conn.prepareStatement(tableName)) {
                statement.executeUpdate();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Sets default settings for hikari
     */
    private void setDataSourceSettings() {
        dataSource.setMaximumPoolSize(3);
        dataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        dataSource.addDataSourceProperty("serverName", config.getString("host"));
        dataSource.addDataSourceProperty("port", config.getInt("port"));
        dataSource.addDataSourceProperty("databaseName", config.getString("database"));
        dataSource.addDataSourceProperty("user", config.getString("username"));
        dataSource.addDataSourceProperty("password", config.getString("password"));
    }
}
