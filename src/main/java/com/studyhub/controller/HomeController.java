package com.studyhub.controller;

import com.studyhub.service.MaterialService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private MaterialService materialService;

    @GetMapping("/")
    public String home(HttpSession session) {
        session.setAttribute("latestMaterial", materialService.getLatestMaterial());
        return "index";
    }



    @GetMapping("/upload-material")
    public String uploadMaterial() {
        return "pages/uploadMaterial";
    }

    @GetMapping("/login")
    public String join(HttpSession session) {
        session.removeAttribute("role");
        return "pages/login";
    }

}
