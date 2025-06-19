package com.sample.journalApp.repositories;

import com.sample.journalApp.models.AppUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser, ObjectId> {

    Optional<AppUser> findByUsername(String username);

    void deleteByUsername(String username);
}
