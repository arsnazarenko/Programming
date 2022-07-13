package lessons.database;

import java.sql.*;

public class PostgresTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/studs",
                "s283333", "wts704");
        Statement stmt;
        String sql;

        try {
            stmt = connection.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS Locations("
                    + "id SERIAL PRIMARY KEY, "
                    + "x BIGINT NOT NULL, "
                    + "y FLOAT NOT NULL, "
                    + "z FLOAT NOT NULL, "
                    + "name CHAR(50) )";
            stmt.executeUpdate(sql);
            stmt.close();

            stmt = connection.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS Address("
                    + "id SERIAL PRIMARY KEY, "
                    + "street CHAR(50) NOT NULL, "
                    + "zipcode CHAR(50) NOT NULL, "
                    + "town  SERIAL REFERENCES Locations (id) DEFAULT NULL )";
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
                    + "coordinates SERIAL REFERENCES Coordinates (id) NOT NULL, "
                    + "creation_date TIMESTAMP NOT NULL, "
                    + "employees_count INT CHECK(employees_count > 0) NOT NULL, "
                    + "type CHAR (35)  NOT NULL, "
                    + "annualTurnover FLOAT CHECK(annualTurnover > 0), "
                    + "officialAddress SERIAL references Address (id) )";
            stmt.executeUpdate(sql);
            stmt.close();




        } finally {
            connection.close();
        }










    }
}
