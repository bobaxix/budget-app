package com.budget.budgetapp.repos.category;

import com.budget.budgetapp.entities.category.CategoryDoc;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends MongoRepository<CategoryDoc, String> { };