package com.daveo.spring.restapi.mongodb.repo.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomCustomerRepositoryImpl implements CustomCustomerRepository {

    @Autowired
    private MongoTemplate mongoTemplate;
}
