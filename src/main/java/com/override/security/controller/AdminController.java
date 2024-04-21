package com.override.security.controller;

import com.override.security.model.User;
import com.override.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping
    public String getAdminPage(Model model) {
        model.addAttribute("users", userDetailsService.findAllUsers());
        return "admin";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("user") User user, Model model) {
        userDetailsService.saveUser(user);

        return "redirect:/admin";
    }

    @GetMapping(path = {"/edit", "/edit/{id}"})
    public String editUser(@PathVariable("id") Optional<Long> id, Model model) {
        model.addAttribute("user", userDetailsService.updateUser(id));
        return "new";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@ModelAttribute("user") User user,
                             @PathVariable("id") Long id) {
        userDetailsService.deleteUser(id);
        return "redirect:/admin";
    }
}
