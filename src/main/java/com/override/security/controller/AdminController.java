package com.override.security.controller;

import com.override.security.model.Role;
import com.override.security.util.UserValidator;
import com.override.security.model.User;
import com.override.security.service.RoleService;
import com.override.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public String getAdminPage(Model model) {
        model.addAttribute("users", userDetailsService.findAllUsers());
        return "admin";
    }

    @GetMapping(path = {"/new"})
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("allRoles",roleService.findAllRoles());
        return "new";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAllRoles());
            return "new";
        }
        userDetailsService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping(path = {"/edit/{id}"})
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userDetailsService.findUser(id));
        model.addAttribute("allRoles", roleService.findAllRoles());
        return "edit";
    }

    @PostMapping(path = {"/{id}"})
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             Authentication authentication, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAllRoles());
            return "edit";
        }
        userDetailsService.updateUser(user);
        if (userDetailsService.isNotRoleAdmin(authentication, user)) {
            return "redirect:/logout";
        }
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userDetailsService.deleteUser(id);
        return "redirect:/admin";
    }
}
