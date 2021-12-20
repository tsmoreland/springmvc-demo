package tsmoreland.objecttracker.data.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import tsmoreland.objecttracker.data.entities.ObjectEntity;

@Repository
public interface ObjectRepository extends PagingAndSortingRepository<ObjectEntity, Long> {

}
