package ca.tsmoreland.datafundementals.infrastructure.repository;

import java.util.List;
import java.util.Optional;

public interface Dao<TEntity> {
    void add(TEntity entity);
    Optional<TEntity> findById(long id);
    List<TEntity> findAll();
    void update(TEntity entity, String[] params);
    void delete(TEntity entity);
}
