package com.budget.budgetapp.controllers.category;

import java.util.List;

import com.budget.budgetapp.entities.category.CategoryDoc;
import com.budget.budgetapp.services.category.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public List<CategoryDoc> getCategories() {
        List<CategoryDoc> categoryList = categoryService.getAllCategories();
        return categoryList;
    }

}