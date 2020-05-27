package server.business.dao;

import java.util.Deque;
import java.util.List;

public interface ObjectDAO<Entity, Key> {
    Key create(Entity model, Key key);
    boolean update(Key key, Entity model);
    Key delete(Key key);
    boolean deleteByKeys(List<Key> keys);
    Deque<Entity> readAll();


}
