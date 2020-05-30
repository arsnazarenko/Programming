package server.business.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

public class DatabaseCreator {
    private static Connection connection;
    private static final Logger logger = LogManager.getLogger(DatabaseCreator.class.getName());

    public static void init(String host, String port, String dataBaseName, String user, String password) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + dataBaseName,
                user, password);
        connection.setAutoCommit(false);
        createTables();

    }

    public static Connection getConnection() {
        return connection;
    }

    private static void createTables() throws SQLException {
        Statement stmt;
        String sql;

        stmt = connection.createStatement();
        sql = "CREATE SEQUENCE IF NOT EXISTS auto_id_users";
        stmt.executeUpdate(sql);
        stmt.close();

        stmt = connection.createStatement();
        sql = "CREATE SEQUENCE IF NOT EXISTS auto_id_locations";
        stmt.executeUpdate(sql);
        stmt.close();

        stmt = connection.createStatement();
        sql = "CREATE SEQUENCE IF NOT EXISTS auto_id_address";
        stmt.executeUpdate(sql);
        stmt.close();

        stmt = connection.createStatement();
        sql = "CREATE SEQUENCE IF NOT EXISTS auto_id_coordinates";
        stmt.executeUpdate(sql);
        stmt.close();

        stmt = connection.createStatement();
        sql = "CREATE SEQUENCE IF NOT EXISTS auto_id_organizations";
        stmt.executeUpdate(sql);
        stmt.close();


        stmt = connection.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS Users("
                + "id BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('auto_id_users') UNIQUE , "
                + "login VARCHAR NOT NULL, "
                + "password BYTEA NOT NULL )";
        stmt.executeUpdate(sql);
        stmt.close();

        stmt = connection.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS Locations("
                + "id BIGINT PRIMARY KEY DEFAULT nextval('auto_id_locations') UNIQUE, "
                + "x BIGINT NOT NULL, "
                + "y DOUBLE PRECISION NOT NULL, "
                + "z DOUBLE PRECISION NOT NULL, "
                + "name VARCHAR NOT NULL )";
        stmt.executeUpdate(sql);
        stmt.close();

        stmt = connection.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS Address("
                + "id BIGINT PRIMARY KEY DEFAULT nextval('auto_id_address') UNIQUE, "
                + "street VARCHAR NOT NULL, "
                + "zipcode VARCHAR NOT NULL, "
                + "town BIGINT REFERENCES Locations (id) ON DELETE SET NULL UNIQUE)  ";

        stmt.executeUpdate(sql);
        stmt.close();

        stmt = connection.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS Coordinates("
                + "id BIGINT PRIMARY KEY DEFAULT nextval('auto_id_coordinates') UNIQUE, "
                + "x DOUBLE PRECISION NOT NULL CHECK(x > -98), "
                + "y FLOAT NOT NULL CHECK(y > -148) )";
        stmt.executeUpdate(sql);
        stmt.close();


        stmt = connection.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS Organizations("
                + "id BIGINT PRIMARY KEY DEFAULT nextval('auto_id_organizations') UNIQUE, "
                + "object_user BIGINT NOT NULL REFERENCES Users (id), "
                + "name VARCHAR NOT NULL, "
                + "coordinates BIGINT REFERENCES Coordinates (id) UNIQUE, "
                + "creation_date TIMESTAMP NOT NULL, "
                + "employees_count INT CHECK(employees_count > 0) NOT NULL, "
                + "type VARCHAR  NOT NULL, "
                + "annualTurnover DOUBLE PRECISION CHECK(annualTurnover > 0), "
                + "officialAddress BIGINT references Address (id) ON DELETE SET NULL UNIQUE)";
        stmt.executeUpdate(sql);
        stmt.close();
        connection.commit();
        logger.debug("tables created");
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
