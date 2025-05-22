package com.studyhub.service;

import com.studyhub.model.Material;
import org.springframework.data.jpa.domain.Specification;

public class MaterialSpecifications {

    // Search by title (case-insensitive partial match)
    public static Specification<Material> hasTitleContaining(String title) {
        return (root, query, cb) -> {
            if (title == null || title.isEmpty()) return null;
            String pattern = "%" + title.toLowerCase() + "%";
            return cb.like(cb.lower(root.get("title")), pattern);
        };
    }

    // Filter by category
    public static Specification<Material> hasCategory(String category) {
        return (root, query, cb) -> {
            if (category == null || category.isEmpty()) return null;
            return cb.equal(root.get("category"), category);
        };
    }

    // Filter by filetype
    public static Specification<Material> hasFileType(String filetype) {
        return (root, query, cb) -> {
            if (filetype == null || filetype.isEmpty()) return null;
            return cb.equal(root.get("type"), filetype);
        };
    }

    // In MaterialSpecifications class
    public static Specification<Material> belongsToUser(int userId) {
        return (root, query, cb) -> cb.equal(root.get("userID"), userId);
    }
}