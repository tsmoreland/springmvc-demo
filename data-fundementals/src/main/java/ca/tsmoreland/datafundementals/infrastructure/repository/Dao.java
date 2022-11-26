package ca.tsmoreland.datafundementals.infrastructure.repository;

import java.util.List;
import java.util.Optional;

public interface Dao<TEntity> {
    TEntity create(TEntity entity);
    Optional<TEntity> findById(long id);
    List<TEntity> findAll();
    TEntity update(TEntity entity);
    int[] update(List<TEntity> entities);
    int delete(TEntity entity);
}
