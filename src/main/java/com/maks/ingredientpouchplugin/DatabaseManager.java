package com.maks.ingredientpouchplugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private final IngredientPouchPlugin plugin;
    private Connection connection;

    public DatabaseManager(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        String host = plugin.getConfig().getString("database.host");
        String port = plugin.getConfig().getString("database.port");
        String database = plugin.getConfig().getString("database.name");
        String user = plugin.getConfig().getString("database.user");
        String password = plugin.getConfig().getString("database.password");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";

        try {
            connection = DriverManager.getConnection(url, user, password);
            plugin.getLogger().info("Database connected successfully.");
            createTable();
        } catch (SQLException e) {
            plugin.getLogger().severe("Could not connect to the database!");
            e.printStackTrace();
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS player_pouch (" +
                "player_uuid VARCHAR(36)," +
                "item_id VARCHAR(255)," +
                "quantity INT," +
                "PRIMARY KEY(player_uuid, item_id)" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
            plugin.getLogger().info("Database table ensured.");
        } catch (SQLException e) {
            plugin.getLogger().severe("Could not create the database table!");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
                plugin.getLogger().info("Database connection closed.");
            } catch (SQLException e) {
                plugin.getLogger().severe("Error closing the database connection!");
                e.printStackTrace();
            }
        }
    }
}
