package com.budget.budgetapp.auth.repo;

import com.budget.budgetapp.auth.entity.DbUser;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<DbUser, String> {

    public DbUser findByUsername(String username);

}