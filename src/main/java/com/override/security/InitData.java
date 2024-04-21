package com.override.security;


import com.override.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitData {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

}
