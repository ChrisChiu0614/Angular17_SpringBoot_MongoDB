package com.chris.spring.data.mongodb.repository;

import com.chris.spring.data.mongodb.model.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TutorialRepository extends MongoRepository<Tutorial, String> {
    Page<Tutorial> findByTitleContaining(String title, Pageable pageable);
    Page<Tutorial> findByPublished(boolean published, Pageable pageable);

}
