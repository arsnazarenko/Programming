package server.business.handlers;


import javafx.util.Pair;
import library.clientCommands.Command;
import library.clientCommands.UserData;
import server.business.dao.PasswordHash;
import server.business.dao.UserDAO;

import java.util.Arrays;

public interface ICommandHandler {
    Object processCommand(Command command);
    default Long authorization(UserData user, UserDAO<UserData, String> dao) {
        Pair<byte[], Long> userInfo = dao.read(user.getLogin());
        byte[] b2 = userInfo.getKey();
        if(b2 == null) {
            return 0L;
        }
        byte[] b1 = PasswordHash.passwordHash(user.getPassword());
        if(Arrays.equals(b2, b1)) {
            return userInfo.getValue();
        }
        return 0L;

    }
}
