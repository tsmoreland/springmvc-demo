package tsmoreland.objecttracker.data.repositories;


import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tsmoreland.objecttracker.data.entities.LogEntryEntity;
import tsmoreland.objecttracker.data.entities.ObjectEntity;

@Repository
public interface ObjectRepository extends PagingAndSortingRepository<ObjectEntity, Long> {
    @Query(
        value = "select l from LogEntity l where l.ObjectEntityId = :id")
    Page<LogEntryEntity> findLogsByObjectEntityId(Long id, Pageable pageable);

}
