package com.preportal.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/study-material")
    public String material() {
        return "/pages/material";
    }


    @GetMapping("/help")
    public String help() {
        return "/pages/help";
    }

    @GetMapping("/upload-material")
    public String uploadMaterial() {
        return "/pages/uploadMaterial";
    }

    @GetMapping("/profile")
    public String profile() {
        return "/pages/profile";
    }

    @GetMapping("/login")
    public String join(HttpSession session) {
        session.removeAttribute("role");
        return "/pages/login";
    }

}
