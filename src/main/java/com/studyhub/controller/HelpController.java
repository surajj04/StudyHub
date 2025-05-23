package com.studyhub.controller;

import com.studyhub.model.UserHelp;
import com.studyhub.service.HelpService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HelpController {

    @Autowired
    private HelpService helpService;

    @GetMapping("/help")
    public String help() {
        return "pages/help";
    }

    @PostMapping("/help")
    public String addHelpData(@ModelAttribute UserHelp help, HttpSession session) {
        helpService.addHelp(help);
        session.setAttribute("redirectUrl", "/help");
        session.setAttribute("error", null);
        session.setAttribute("success", "Support request submitted successfully! Our team will get back to you shortly.");
        return "pages/message";
    }
}
