package com.budget.budgetapp.controllers.category;

import java.util.List;

import com.budget.budgetapp.entities.category.CategoryDoc;
import com.budget.budgetapp.services.category.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public List<CategoryDoc> getCategories() {
        return categoryService.getAllCategories();
    }

}