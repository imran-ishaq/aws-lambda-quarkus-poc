package com.poc.repository;

import com.poc.entity.Greetings;
import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingRepository implements PanacheMongoRepository<Greetings> {
}
