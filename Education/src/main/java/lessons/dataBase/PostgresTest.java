package lessons.dataBase;

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
            sql = "CREATE TABLE IF NOT EXISTS company" +
                    "(ID INT PRIMARY KEY    NOT NULL, " +
                    "NAME            TEXT   NOT NULL, " +
                    "AGE             INT    NOT NULL, " +
                    "ADDRESS         CHAR(50), " +
                    "SALARY          REAL)";
            stmt.executeUpdate(sql);
            stmt.close();


            connection.setAutoCommit(false);


            stmt = connection.createStatement();
            sql = "INSERT INTO COMPANY (ID, NAME, AGE, ADDRESS, SALARY) VALUES (1, 'Paul', 32, 'California', 20000.00);";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID, NAME, AGE, ADDRESS, SALARY) VALUES (2, 'Allen', 25, 'Texas', 15000.00);";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID, NAME, AGE, ADDRESS, SALARY) VALUES (3, 'Teddy', 23, 'Norway', 20000.00);";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO COMPANY (ID, NAME, AGE, ADDRESS, SALARY) VALUES (4, 'Mark', 25, 'Rich-Mond', 65000.00);";
            stmt.executeUpdate(sql);


            stmt.close();
            connection.commit();

            stmt = connection.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM COMPANY;");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                float salary = rs.getFloat("salary");
                System.out.println("ID = " + id);
                System.out.println("NAME = " + name);
                System.out.println("AGE = " + age);
                System.out.println("ADDRESS = " + address);
                System.out.println("SALARY = " + salary);
            }

            rs.close();
            stmt.close();


        } finally {
            connection.close();
        }








    }
}
