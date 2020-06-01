package server.business.dao;

import javafx.util.Pair;

public interface UserDAO<Entity, Key> {
    void create(Entity user);
    UserInfo read(Key login);

}
