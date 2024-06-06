package com.example.demo.controller;

import com.example.demo.Entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.validator.EmailValidator;
import com.example.demo.validator.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    @GetMapping("/")
    public String home() {
        return "home";
    }
    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "loginSuccess";}
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user, Model model, String email, String password) {
        if (userRepository.findByEmail(user.getEmail())!= null){
            model.addAttribute("errorEmail", "Email Already Exists");
            return "register";
        }
        boolean isValidEmail = EmailValidator.isValidEmail(email);
        if (!isValidEmail) {
            model.addAttribute("errorEmail", "Invalid Email");
            return "register";
        }
        if (userRepository.findByUsername(user.getUsername()) != null) {
            model.addAttribute("errorUsername", "Username already exists");
            return "register";

        }
        boolean isValidPassword = PasswordValidator.isValidPassword(password);
        if (!isValidPassword) {
            model.addAttribute("errorPassword", "Invalid Password." +"\n"+
                    "Password has to be at least one symbol and one number");
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/register?success";
    }


}
