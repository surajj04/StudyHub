package com.studyhub.service;

import com.studyhub.model.CustomUserDetails;
import com.studyhub.model.Material;
import com.studyhub.model.Search;
import com.studyhub.model.User;
import com.studyhub.repository.MaterialRepository;
import com.studyhub.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private MaterialService materialService;


    public User registerUser(User user) {
        User result = userRepository.findByEmail(user.getEmail());
        if (result == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setName(user.getName().toUpperCase());
            user.setCourse(user.getCourse().toUpperCase());
            user.setInstitute(user.getInstitute().toUpperCase());

            return userRepository.save(user);
        }
        return null;
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public CustomUserDetails getUserDetails(User user) {
        CustomUserDetails ud = new CustomUserDetails();
        ud.setId(user.getId());
        ud.setName(user.getName());
        ud.setEmail(user.getEmail());
        ud.setInstitute(user.getInstitute());
        ud.setCourse(user.getCourse());
        return ud;
    }

    public List<Material> userMaterial(HttpSession session) {
        CustomUserDetails user = (CustomUserDetails) session.getAttribute("currentUser");
        return materialService.getMaterialByUserId(user.getId());
    }

    // UserService.java
    public List<Material> searchUserMaterials(Search search, HttpSession session) {
        CustomUserDetails user = (CustomUserDetails) session.getAttribute("currentUser");

        Specification<Material> spec = Specification.where(
                        MaterialSpecifications.belongsToUser(user.getId())
                )
                .and(MaterialSpecifications.hasTitleContaining(search.getQuery()))
                .and(MaterialSpecifications.hasCategory(search.getCategory()))
                .and(MaterialSpecifications.hasFileType(search.getType()));

        return materialService.findAllUserSearch(spec);
    }

    // Paginated version
    public Page<Material> searchUserMaterialsPaginated(
            int userId,        // Add user ID parameter
            String title,
            String category,
            String filetype,
            Pageable pageable
    ) {
        Specification<Material> spec = Specification
                .where(MaterialSpecifications.belongsToUser(userId))
                .and(MaterialSpecifications.hasTitleContaining(title))
                .and(MaterialSpecifications.hasCategory(category))
                .and(MaterialSpecifications.hasFileType(filetype));

        return materialService.findAllBySpecPageable(spec, pageable);
    }




}
