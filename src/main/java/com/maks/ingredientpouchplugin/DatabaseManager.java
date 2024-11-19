//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
        String host = this.plugin.getConfig().getString("database.host");
        String port = this.plugin.getConfig().getString("database.port");
        String database = this.plugin.getConfig().getString("database.name");
        String user = this.plugin.getConfig().getString("database.user");
        String password = this.plugin.getConfig().getString("database.password");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false";

        try {
            this.connection = DriverManager.getConnection(url, user, password);
            this.plugin.getLogger().info("Database connected successfully.");
            this.createTable();
        } catch (SQLException var8) {
            SQLException e = var8;
            this.plugin.getLogger().severe("Could not connect to the database!");
            e.printStackTrace();
        }

    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS player_pouch (player_uuid VARCHAR(36),item_id VARCHAR(255),quantity INT,PRIMARY KEY(player_uuid, item_id));";

        try {
            Statement stmt = this.connection.createStatement();

            try {
                stmt.executeUpdate(sql);
                this.plugin.getLogger().info("Database table ensured.");
            } catch (Throwable var6) {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (Throwable var5) {
                        var6.addSuppressed(var5);
                    }
                }

                throw var6;
            }

            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException var7) {
            SQLException e = var7;
            this.plugin.getLogger().severe("Could not create the database table!");
            e.printStackTrace();
        }

    }

    public Connection getConnection() {
        return this.connection;
    }

    public void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
                this.plugin.getLogger().info("Database connection closed.");
            } catch (SQLException var2) {
                SQLException e = var2;
                this.plugin.getLogger().severe("Error closing the database connection!");
                e.printStackTrace();
            }
        }

    }
}
