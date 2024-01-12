package com.poc.entity;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@MongoEntity(collection="Greetings")
public class Greetings extends PanacheMongoEntity {
    public String name;
    public String greeting;
}