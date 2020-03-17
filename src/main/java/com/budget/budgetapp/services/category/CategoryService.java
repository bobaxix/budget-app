package com.budget.budgetapp.services.category;

import java.util.List;

import com.budget.budgetapp.entities.category.CategoryDoc;
import com.budget.budgetapp.repos.category.CategoryRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public List<CategoryDoc> getAllCategories() {
        return categoryRepo.findAll();
    }
}