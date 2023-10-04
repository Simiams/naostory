package com.naostory.repositories;

import com.naostory.models.Monument_MDL;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IMonument_REP extends MongoRepository<Monument_MDL, Long> {
}
