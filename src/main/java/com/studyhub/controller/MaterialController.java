package com.studyhub.controller;

import com.studyhub.model.Material;
import com.studyhub.model.MaterialUpload;
import com.studyhub.model.Search;
import com.studyhub.service.GoogleDriveService;
import com.studyhub.service.MaterialService;
import com.studyhub.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MaterialController {

    @Autowired
    private GoogleDriveService googleDriveService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private UserService userService;

    @GetMapping("/study-material")
    public String material(HttpSession session) {
        session.setAttribute("materials", materialService.getAllMaterials());
        return "pages/material";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute MaterialUpload materialUpload, HttpSession session) {
        try {
            if (materialUpload.getFile().isEmpty()) {
                session.setAttribute("redirectUrl", "/upload-material");
                session.setAttribute("success", null);
                session.setAttribute("error", "File is empty.");
                return "pages/message";
            }
            Material resp = materialService.uploadMaterialDrive(materialUpload, session);

            if (resp != null) {
                session.setAttribute("redirectUrl", "/profile");
                session.setAttribute("error", null);
                session.setAttribute("success", "Your study material has been uploaded successfully!");
                return "pages/message";
            }
            session.setAttribute("redirectUrl", "/upload-material");
            session.setAttribute("success", null);
            session.setAttribute("error", "There was an error uploading your material. Please try again.");
            return "pages/message";
        } catch (Exception e) {
            session.setAttribute("redirectUrl", "/upload-material");
            session.setAttribute("success", null);
            session.setAttribute("error", e.getMessage());
            return "pages/message";
        }
    }


    @GetMapping("/search")
    public String search(@ModelAttribute Search search, HttpSession session) {
        session.setAttribute("materials", materialService.searchMaterials(search.getQuery(), search.getCategory(), search.getType()));
        return "pages/material";
    }

    @GetMapping("/update-material/{id}")
    public String updateMaterial(@PathVariable int id, HttpSession session) {
        Material result = materialService.getMaterial(id, session);
        if (result != null) {
            session.setAttribute("updateMaterial", result);
            return "pages/updateMaterial";
        }
        session.setAttribute("redirectUrl", "/profile");
        session.setAttribute("error", "You are not authorized to edit this material or it doesn't exist.");
        session.setAttribute("success", null);
        return "pages/message";
    }


    @PostMapping("/update-material")
    public String updateMaterial(@RequestParam("id") int id, @RequestParam("title") String title, @RequestParam("description") String description, HttpSession session) {
        Material material = materialService.updateMaterial(id, title, description);
        if (material.getId() != 0) {
            session.setAttribute("redirectUrl", "/profile");
            session.setAttribute("error", null);
            session.setAttribute("success", "Update successful!");
            return "pages/message";
        }
        session.setAttribute("success", null);
        session.setAttribute("redirectUrl", "/profile");
        session.setAttribute("error", "Something went wrong on our end. Please try again later.");
        return "pages/message";

    }

    @GetMapping("/delete-material/{id}")
    public String showDeleteMaterial(@PathVariable int id,HttpSession session) {
        session.setAttribute("deleteMaterial", materialService.getMaterialById(id));
        return "pages/deleteMaterial";
    }
    @PostMapping("/delete-material/{id}")
    public String deleteMaterial(@PathVariable("id") int id, HttpSession session) {
        Material result = materialService.deleteMaterial(id, session);
        if (result != null) {
            session.setAttribute("redirectUrl", "/profile");
            session.setAttribute("error", null);
            session.setAttribute("success", "Delete successful!");
            return "pages/message";
        }
        session.setAttribute("success", null);
        session.setAttribute("redirectUrl", "/profile");
        session.setAttribute("error", "Something went wrong on our end. Please try again later.");
        return "pages/message";
    }

}
