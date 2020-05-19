package server.business.dao;

import java.util.List;

public interface DAO<Entity, Key> {
    long create(Entity model);
    Entity read(Key key);
    boolean update(Key key);
    long delete(Key key);
    boolean deleteByKeys(Key[] keys);


}
