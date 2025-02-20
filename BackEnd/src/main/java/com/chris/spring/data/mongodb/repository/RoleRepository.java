package com.chris.spring.data.mongodb.repository;

import com.chris.spring.data.mongodb.model.ERole;
import com.chris.spring.data.mongodb.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
