package com.budget.budgetapp.auth.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "users")
public class DbUser {

    @Id
    private String id;
    private String username;
    private String password;

    private List<String> roles;
    
}