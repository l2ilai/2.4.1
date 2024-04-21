package com.override.security.Util;

import com.override.security.model.User;
import com.override.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz) ;
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        try {
            userDetailsService.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException e) {
            return;
        }

        errors.rejectValue("name" ,"", "Человек с таким именем пользователя существует!");
    }
}
