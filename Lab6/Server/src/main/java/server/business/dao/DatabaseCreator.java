package server.business.dao;


import java.sql.*;

public class DatabaseCreator {
    private static Connection connection;
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/studs",
                    "s283333", "wts704");
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Connection getConnection() {
        return connection;
    }

    public static void createTables() throws SQLException {
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
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void testInsert() throws SQLException {
        String sql ="INSERT INTO Locations (x, y, z, name) VALUES ((?), (?), (?), (?)) RETURNING id";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, 1231231);
        ps.setDouble(2, 5.55);
        ps.setDouble(3, 6.66);
        ps.setString(4, "locat");



        ResultSet rs = ps.executeQuery();
        long i = 0;
        if (rs.next()) {
            i = rs.getInt(1);
        }
        String sql2 = "INSERT INTO Address (street, zipcode, town) VALUES ((?), (?), (?)) RETURNING id";
        ps = connection.prepareStatement(sql2);
        ps.setString(1, "street");
        ps.setString(2, "zipzip");
        ps.setLong(3, i);
        rs = ps.executeQuery();
        i = 0;
        if (rs.next()) {
            i = rs.getLong(1);
        }
        String sql3 = "INSERT INTO Coordinates (x, y) VALUES ((?), (?)) RETURNING id";
        ps = connection.prepareStatement(sql3);
        ps.setDouble(1, 4.4);
        ps.setDouble(2, 98.6);
        rs = ps.executeQuery();
        long j = 0;
        if (rs.next()) {
            j = rs.getLong(1);
        }
        String sql4 = "INSERT INTO Organizations (name, coordinates, creation_date, employees_count, type, annualTurnover, officialAddress) " +
                "VALUES ((?), (?), (?), (?), (?), (?), (?)) RETURNING id";//address i
        ps = connection.prepareStatement(sql4);
        ps.setString(1, "name");
        ps.setLong(2, j);
        ps.setTimestamp(3, new Timestamp(new java.util.Date().getTime()));
        ps.setInt(4, 123);
        ps.setString(5, "COMMERCIAL");
        ps.setNull(6, Types.DOUBLE);
        ps.setLong(7, i);

        ps.executeQuery();
        connection.commit();
        ps.close();
    }
}
