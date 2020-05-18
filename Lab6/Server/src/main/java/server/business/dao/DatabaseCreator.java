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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void createTables() throws SQLException {
        Statement stmt;
        String sql;

        stmt = connection.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS Locations("
                + "id SERIAL PRIMARY KEY, "
                + "x BIGINT NOT NULL, "
                + "y FLOAT NOT NULL, "
                + "z FLOAT NOT NULL, "
                + "name CHAR(50) NOT NULL )";
        stmt.executeUpdate(sql);
        stmt.close();

        stmt = connection.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS Address("
                + "id SERIAL PRIMARY KEY, "
                + "street CHAR(50) NOT NULL, "
                + "zipcode CHAR(50) NOT NULL, "
                + "town  SERIAL REFERENCES Locations (id) ON UPDATE CASCADE ON DELETE CASCADE )";

        stmt.executeUpdate(sql);
        stmt.close();

        stmt = connection.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS Coordinates("
                + "id SERIAL PRIMARY KEY, "
                + "x FLOAT NOT NULL CHECK(x > -98), "
                + "y FLOAT NOT NULL CHECK(y > -148) )";
        stmt.executeUpdate(sql);
        stmt.close();


        stmt = connection.createStatement();
        sql = "CREATE TABLE IF NOT EXISTS Organizations("
                + "id SERIAL PRIMARY KEY, "
                + "name CHAR(50) NOT NULL, "
                + "coordinates SERIAL REFERENCES Coordinates (id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL, "
                + "creation_date TIMESTAMP NOT NULL, "
                + "employees_count INT CHECK(employees_count > 0) NOT NULL, "
                + "type CHAR (35)  NOT NULL, "
                + "annualTurnover FLOAT CHECK(annualTurnover > 0), "
                + "officialAddress SERIAL references Address (id) ON UPDATE CASCADE ON DELETE CASCADE )";
        stmt.executeUpdate(sql);
        stmt.close();


        connection.setAutoCommit(false);



    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
    public static void testInsert() throws SQLException {
        Statement stmt = connection.createStatement();

        String sql ="INSERT INTO Locations (x, y, z, name) VALUES (25, 5.5, 5.5, 'russia') RETURNING id";

        ResultSet rs = stmt.executeQuery(sql);
        int i = 0;
        if (rs.next()) {
            i = rs.getInt(1);
        }
        sql = "INSERT INTO Address (street, zipcode, town) VALUES ('Mira', '14881488', " + i + ")";
        stmt.executeUpdate(sql);
        connection.commit();
        stmt.close();
    }
}
