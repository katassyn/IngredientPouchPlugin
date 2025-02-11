package com.maks.ingredientpouchplugin;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private final IngredientPouchPlugin plugin;
    private HikariDataSource dataSource;

    public DatabaseManager(IngredientPouchPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        String host = this.plugin.getConfig().getString("database.host");
        String port = this.plugin.getConfig().getString("database.port");
        String database = this.plugin.getConfig().getString("database.name");
        String user = this.plugin.getConfig().getString("database.user");
        String password = this.plugin.getConfig().getString("database.password");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);

        // HikariCP specific configuration
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(300000); // 5 minutes
        config.setConnectionTimeout(10000); // 10 seconds
        config.setValidationTimeout(5000); // 5 seconds
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        config.addDataSourceProperty("useSSL", "false");

        try {
            dataSource = new HikariDataSource(config);
            this.plugin.getLogger().info("Database connection pool initialized successfully.");
            this.createTable();
        } catch (Exception e) {
            this.plugin.getLogger().severe("Could not initialize database connection pool!");
            e.printStackTrace();
        }
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS player_pouch ("
                + "player_uuid VARCHAR(36),"
                + "item_id VARCHAR(255),"
                + "quantity INT,"
                + "PRIMARY KEY(player_uuid, item_id));";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            this.plugin.getLogger().info("Database table ensured.");
        } catch (SQLException e) {
            this.plugin.getLogger().severe("Could not create the database table!");
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            this.plugin.getLogger().info("Database connection pool closed.");
        }
    }
}