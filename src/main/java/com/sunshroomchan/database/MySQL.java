package com.sunshroomchan.database;

import com.sunshroomchan.DatabaseCredentials;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class MySQL {

    public DatabaseCredentials databaseCredentials;
    private final String address;
    private final int port;
    private final String database;
    private final String username;
    private final String password;
    private final String poolname;
    private final int poolsize;
    private final int maxlifetime;
    private final boolean useSSL;
    private final boolean verifyCeritificate;
    private boolean isConnected = false;

    public HikariDataSource ds;
    public MySQL(){
        this.address = databaseCredentials.getAddress();
        this.port = databaseCredentials.getPort();
        this.database = databaseCredentials.getDatabase();
        this.username = databaseCredentials.getUsername();
        this.password = databaseCredentials.getPassword();
        this.poolname = databaseCredentials.getPoolName();
        this.poolsize = databaseCredentials.getPoolsize();
        this.maxlifetime = databaseCredentials.getMaxLifeTime();
        this.useSSL = databaseCredentials.isUseSSL();
        this.verifyCeritificate = databaseCredentials.isVerifyCeritificate();
    }

    public boolean connect(){
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setPoolName(poolname);
        hikariConfig.setDriverClassName("org.mariadb.jdbc.Driver");
        hikariConfig.setMaximumPoolSize(poolsize);
        hikariConfig.setMaxLifetime(maxlifetime);

        hikariConfig.setJdbcUrl("jdbc:mariadb://" + address + ":" + port + "/" + database);

        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);

        hikariConfig.addDataSourceProperty("useSSL", this.useSSL);
        hikariConfig.addDataSourceProperty("verifyServerCertificate", this.verifyCeritificate);

        hikariConfig.addDataSourceProperty("characterEncoding", "utf8");
        hikariConfig.addDataSourceProperty("encoding", "UTF-8");
        hikariConfig.addDataSourceProperty("useUnicode", "true");

        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("jdbcCompliantTruncation", "false");

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "275");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        // Recover if connection gets interrupted
        hikariConfig.addDataSourceProperty("socketTimeout", String.valueOf(TimeUnit.SECONDS.toMillis(30)));

        ds = new HikariDataSource(hikariConfig);

        try {
            ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void checkConnection() {
        try (Connection connection = ds.getConnection()) {
            if (connection.isValid(1000)) { // Kiểm tra tính hợp lệ của kết nối
                isConnected = true;
            } else {
                isConnected = false;
                reconnect(); // Gọi hàm reconnect() để thử kết nối lại
            }
        } catch (SQLException e) {
            isConnected = false;
            reconnect();
        }
    }

    public boolean isConnected() {
        try (Connection connection = ds.getConnection()) {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void reconnect() {
        // Thử kết nối lại
        if (!ds.isClosed()){
            ds.close(); // Đóng kết nối hiện tại (nếu có)
        }
        connect(); // Thử kết nối lại
        Bukkit.getServer().getLogger().severe("RECONNECT");
    }

    public void disconnect() {
        if (ds != null) {
            try {
                if (!ds.isClosed()) {
                    ds.close();
                }
            } finally {
                ds = null;
            }
        }
    }

    public void getConndction(){
        try {
            Connection conndction = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
