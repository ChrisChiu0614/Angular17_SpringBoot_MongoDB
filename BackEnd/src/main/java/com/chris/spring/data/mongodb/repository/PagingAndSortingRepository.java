package com.chris.spring.data.mongodb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface PagingAndSortingRepository<T, ID> extends CrudRepository {
    Page<T> findAll(Pageable pageable);
}
