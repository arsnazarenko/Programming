package server.business.dao;

import javafx.util.Pair;
import library.clientCommands.UserData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDAO<UserData, String> {
    private Connection connection;
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);



    public UserDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(UserData user) {
        try{
            logger.debug("isValid:" + connection.isValid(2));
            try(PreparedStatement ps = connection.prepareStatement(SQLUsers.INSERT_USERS.sql)) {
                ps.setString(1, user.getLogin());
                byte[] pass = PasswordHash.passwordHash(user.getPassword());
                ps.setBytes(2, pass);
                ps.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Pair<byte[], Long> read(String login) {
        byte[] bytes = null;
        long userId = 0;
        try{
            logger.debug("before prepareStatement");
            logger.debug("valid: " + connection.isValid(2));
            try(PreparedStatement ps = connection.prepareStatement(SQLUsers.SELECT_USERS.sql)) {
                ps.setString(1, login);
                try(ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        bytes = rs.getBytes("password");
                        userId = rs.getLong("id");
                    }
                }
            }
            return new Pair<>(bytes, userId);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("User read not completed");
            return new Pair<>(bytes, userId);
        }

    }
    enum SQLUsers {
        INSERT_USERS("INSERT INTO Users (login, password) VALUES ((?), (?))"),
        SELECT_USERS("SELECT * FROM Users WHERE login = (?)");

        String sql;

        SQLUsers(String sql) {
            this.sql = sql;
        }
    }
}
