package server.business.dao;

import javafx.util.Pair;

public interface UserDAO<Entity, Key> {
    void create(Entity user);
    Pair<byte[], Long> read(Key login);

}
