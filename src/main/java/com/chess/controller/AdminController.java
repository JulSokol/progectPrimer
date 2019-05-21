package com.chess.controller;

import com.chess.domain.User;
import com.chess.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasAuthority('ADMIN')")
@Controller
public class AdminController {
@Autowired private  UserRepository userRepository;


    @GetMapping("/admin/users")
    public String adminUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "adminUsers";
    }

    @GetMapping("/admin/users/{id}")
    public String adminUsersEdit(Model model, @PathVariable Long id) {
        model.addAttribute("user", userRepository.findById(id).get());
        return "adminUsersEdit";
    }

    @PostMapping("/admin/users/{id}")
    public String adminUsersSave(@PathVariable Long id, @ModelAttribute("user") User user) {
        User dbUser = userRepository.findById(id).get();
        dbUser.setNik(user.getNik());
        dbUser.setEmail(user.getEmail());
        dbUser.setCity(user.getCity());
        dbUser.setCountry(user.getCountry());
        userRepository.save(dbUser);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin/users";
    }

}
