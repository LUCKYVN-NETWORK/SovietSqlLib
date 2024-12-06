package com.sunshroomchan;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class DatabaseCredentials {

    @NotNull
    public static DatabaseCredentials of(@NotNull String address, int port, @NotNull String database, @NotNull String username, @NotNull String password, @NotNull String poolname, int poolsize, int maxlifetime, boolean useSSL, boolean verifyCeritificate) {
        return new DatabaseCredentials(address, port, database, username, password, poolname, poolsize, maxlifetime, useSSL, verifyCeritificate);
    }

    @NotNull
    public static DatabaseCredentials fromConfig(@NotNull ConfigurationSection config) {
        return of(
                config.getString("address", "localhost"),
                config.getInt("port", 3306),
                config.getString("database", "minecraft"),
                config.getString("username", "root"),
                config.getString("password", "passw0rd"),
                config.getString("poolname", "YourPluginPoolName"),
                config.getInt("poolsize", 10),
                config.getInt("maxlifetime", 1800000),
                config.getBoolean("useSSL", false),
                config.getBoolean("verify-certificate", false)
        );
    }

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

    private DatabaseCredentials(@NotNull String address, int port, @NotNull String database, @NotNull String username, @NotNull String password, @NotNull String poolname, int poolsize, int maxlifetime, boolean useSSL, boolean verifyCeritificate) {
        this.address = Objects.requireNonNull(address);
        this.port = port;
        this.database = Objects.requireNonNull(database);
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.poolname = Objects.requireNonNull(poolname);
        this.poolsize = poolsize;
        this.maxlifetime = maxlifetime;
        this.useSSL = useSSL;
        this.verifyCeritificate = verifyCeritificate;
    }

    @NotNull
    public String getAddress() {
        return this.address;
    }

    public int getPort() {
        return this.port;
    }

    @NotNull
    public String getDatabase() {
        return this.database;
    }

    @NotNull
    public String getUsername() {
        return this.username;
    }

    @NotNull
    public String getPassword() {
        return this.password;
    }

    @NotNull
    public String getPoolName(){
        return this.poolname;
    }

    public int getPoolsize(){
        return this.poolsize;
    }

    public int getMaxLifeTime(){
        return this.maxlifetime;
    }

    public boolean isUseSSL(){
        return this.useSSL;
    }

    public boolean isVerifyCeritificate(){
        return this.verifyCeritificate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DatabaseCredentials)) return false;
        final DatabaseCredentials other = (DatabaseCredentials) o;

        return this.getAddress().equals(other.getAddress()) &&
                this.getPort() == other.getPort() &&
                this.getDatabase().equals(other.getDatabase()) &&
                this.getUsername().equals(other.getUsername()) &&
                this.getPassword().equals(other.getPassword()) &&
                this.getPoolName().equals(other.getPoolName()) &&
                this.getPoolsize() == other.getPoolsize() &&
                this.getMaxLifeTime() == other.getMaxLifeTime() &&
                this.isUseSSL() == other.isUseSSL() &&
                this.isVerifyCeritificate() == other.isVerifyCeritificate();
    }

    @Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getPort();
        result = result * PRIME + this.getAddress().hashCode();
        result = result * PRIME + this.getDatabase().hashCode();
        result = result * PRIME + this.getUsername().hashCode();
        result = result * PRIME + this.getPassword().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DatabaseCredentials(" +
                "address=" + this.getAddress() + ", " +
                "port=" + this.getPort() + ", " +
                "database=" + this.getDatabase() + ", " +
                "username=" + this.getUsername() + ", " +
                "password=" + this.getPassword() + ", " +
                "poolname=" + this.getPoolName() + ", " +
                "poolsize=" + this.getPoolsize() + ", " +
                "maxlifetime=" + this.getMaxLifeTime() + ", " +
                "useSSL=" + this.isUseSSL() + ", " +
                "verify-certificate=" + this.isVerifyCeritificate();
    }
}
