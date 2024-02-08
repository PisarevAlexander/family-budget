package com.pisarevAS.familybudget.user.controller;

import com.pisarevAS.familybudget.user.model.UserDto;
import com.pisarevAS.familybudget.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("index")
    public String homePage() {
        return "index";
    }

    @GetMapping("register")
    public String registrationPage(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }

        userService.create(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/users")
    public String userPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "users";
    }
}