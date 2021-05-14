package dev.blizzardutils.hikari;

import com.zaxxer.hikari.HikariDataSource;
import dev.blizzardutils.string.StringUtils;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

public class HikariSetup {

    private static HikariSetup instance = null;
    private HikariDataSource dataSource;

    // Hikari Settings
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    public static synchronized HikariSetup getInstance() {
        if (instance == null) instance = new HikariSetup();
        return instance;
    }

    public HikariSetup init(String host, int port, String database, String username, String password, String... tables) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        dataSource = new HikariDataSource();
        setDataSourceSettings();
        createTable(tables);
        Bukkit.getConsoleSender().sendMessage(StringUtils.format("&bBlizzard Utilities &7made a &aConnection&7!"));
        return this;
    }


    /**
     * Sets default settings for hikari
     */
    private void setDataSourceSettings() {
        dataSource.setMaximumPoolSize(3);
        dataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        dataSource.addDataSourceProperty("serverName", host);
        dataSource.addDataSourceProperty("port", port);
        dataSource.addDataSourceProperty("databaseName", database);
        dataSource.addDataSourceProperty("user", username);
        dataSource.addDataSourceProperty("password", password);
    }

    /**
     * Create a table
     *
     * @param tableName Prepared Statement
     */
    public HikariSetup createTable(String... tableName) {
        try (Connection conn = dataSource.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(Arrays.toString(tableName))) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return this;
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }
}
