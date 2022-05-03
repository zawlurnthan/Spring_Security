package com.zthan.spring_Security.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

        @GetMapping("/")
        public String home() {
                return "index";
        }

        @GetMapping("/login")
        public String getLogin() {
                return "login";
        }
}
