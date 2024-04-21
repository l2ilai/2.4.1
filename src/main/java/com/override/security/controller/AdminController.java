package com.override.security.controller;

import com.override.security.Util.UserValidator;
import com.override.security.model.User;
import com.override.security.service.UserDetailsServiceImpl;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping
    public String getAdminPage(Model model) {
        model.addAttribute("users", userDetailsService.findAllUsers());
        return "admin";
    }

    @PostMapping("/newUser")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "new";
        }
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
