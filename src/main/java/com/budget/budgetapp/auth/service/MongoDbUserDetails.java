package com.budget.budgetapp.auth.service;

import java.util.List;
import java.util.stream.Collectors;

import com.budget.budgetapp.auth.entity.DbUser;
import com.budget.budgetapp.auth.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MongoDbUserDetails implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Value("${log.userNotFoundMsg}")
    private String usernameNotFoundExMsg;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DbUser user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(usernameNotFoundExMsg);
        }

        List<SimpleGrantedAuthority> authorities = user.getRoles()
                                                    .stream()
                                                    .map(role -> new SimpleGrantedAuthority(role))
                                                    .collect(Collectors.toList());


        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    



}