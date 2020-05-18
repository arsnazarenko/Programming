package server.business.dao;

import java.util.List;

public interface DAO<Entity, Key> {
    boolean create(Entity model);
    Entity read(Key key);
    boolean update(Key key);
    boolean delete(Key key);
    boolean deleteByKeys(Key[] keys);


}
