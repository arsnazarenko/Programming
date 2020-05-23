package server.business.dao;

import java.util.List;

public interface ObjectDAO<Entity, Key, User> {
    long create(Entity model, Long login);
    Entity read(Key key, User usr);
    boolean update(Key key, User usr);
    long delete(Key key);
    boolean deleteByKeys(List<Key> keys);


}
