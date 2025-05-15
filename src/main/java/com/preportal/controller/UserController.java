package com.preportal.controller;

import com.preportal.model.CustomUserDetails;
import com.preportal.model.User;
import com.preportal.model.UserPrincipal;
import com.preportal.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public String register(@RequestParam String confirm, @ModelAttribute User user, HttpSession session) {
        if (!user.getPassword().equals(confirm)) {
            session.setAttribute("success", null);
            session.setAttribute("redirectUrl", "/join");
            session.setAttribute("error", "Passwords do not match!");
            return "/pages/message";
        }
        User result = userService.registerUser(user);
        if (result != null) {
            session.setAttribute("redirectUrl", "/join");
            session.setAttribute("error", null);
            session.setAttribute("success", "Register successful!");
            return "/pages/message";
        }
        session.setAttribute("success", null);
        session.setAttribute("redirectUrl", "/");
        session.setAttribute("error", "Something went wrong!!! Please try again later.");
        return "/pages/message";
    }



    @GetMapping("/login-success")
    public String loginSuccess(HttpSession session) {
        //  current authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        CustomUserDetails user = userService.getUserDetails(userPrincipal.getUser());

        session.setAttribute("currentUser", user);

        session.setAttribute("redirectUrl", "/profile");
        session.setAttribute("error", null);
        session.setAttribute("success", "Login successful!");
        return "pages/message";
    }

    @GetMapping("/login-error")
    public String loginError(HttpSession session) {
        session.setAttribute("redirectUrl", "/join");
        session.setAttribute("success", null);
        session.setAttribute("error", "Invalid username or password.");
        return "pages/message";
    }



}
