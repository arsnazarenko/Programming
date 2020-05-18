package server.business.dao;


import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        DatabaseCreator.createTables();
        DatabaseCreator.testInsert();
        DatabaseCreator.closeConnection();
    }
}
