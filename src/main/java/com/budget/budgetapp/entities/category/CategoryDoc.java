package com.budget.budgetapp.entities.category;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "categories")
@Data
public class CategoryDoc {

    @Id
    private String id;

    private String name;
    private List<String> subcategories;
}