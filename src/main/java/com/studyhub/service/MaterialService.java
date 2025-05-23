package com.studyhub.service;

import com.studyhub.model.CustomUserDetails;
import com.studyhub.model.Material;
import com.studyhub.model.MaterialUpload;
import com.studyhub.model.Search;
import com.studyhub.repository.MaterialRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MaterialService {

    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private GoogleDriveService googleDriveService;

    public Material uploadMaterialDrive(MaterialUpload materialUpload, HttpSession session) throws IOException {
        File tempFile = File.createTempFile("upload-", materialUpload.getFile().getOriginalFilename());
        materialUpload.getFile().transferTo(tempFile);
        List<String> list = googleDriveService.uploadFile(tempFile);
        assert list != null;
        if (list.getFirst() != null) {
            CustomUserDetails user = (CustomUserDetails) session.getAttribute("currentUser");
            return saveMaterial(materialUpload, list, user);
        }
        return null;
    }

    public Material saveMaterial(MaterialUpload materialUpload, List<String> list, CustomUserDetails user) {
        Material material = new Material();
        material.setTitle(materialUpload.getTitle());
        material.setDescription(materialUpload.getDescription());
        material.setViewUrl(list.getFirst());
        material.setDownloadUrl(list.getLast());
        material.setCategory(materialUpload.getCategory());
        material.setType(materialUpload.getFileType());
        material.setDateTime(LocalDateTime.now());
        material.setUserID(user.getId());
        material.setUserName(user.getName());

        return materialRepository.save(material);
    }

    public List<Material> getAllMaterials() {
        return materialRepository.findAll();
    }


    public List<Material> getMaterialByUserId(int userID) {
        return materialRepository.findByUserID(userID);
    }

    public List<Material> searchMaterials(String title, String category, String type) {
        Specification<Material> spec = Specification.where(MaterialSpecifications.hasTitleContaining(title))
                .and(MaterialSpecifications.hasCategory(category))
                .and(MaterialSpecifications.hasFileType(type));

        return materialRepository.findAll(spec);
    }

    // Paginated version
    public Page<Material> searchMaterialsPaginated(String title, String category, String filetype, Pageable pageable) {
        Specification<Material> spec = Specification.where(MaterialSpecifications.hasTitleContaining(title))
                .and(MaterialSpecifications.hasCategory(category))
                .and(MaterialSpecifications.hasFileType(filetype));

        return materialRepository.findAll(spec, pageable);
    }

    public List<Material> findAllUserSearch(Specification<Material> spec) {
        return materialRepository.findAll(spec);
    }

    public Page<Material> findAllBySpecPageable(Specification<Material> spec, Pageable pageable) {
        return materialRepository.findAll(spec, pageable);
    }

    public Material getMaterialById(int id) {
        return materialRepository.findById(id).orElse(new Material());
    }

    public Material updateMaterial(int id, String title, String description) {
        Material m = getMaterialById(id);
        m.setTitle(title);
        m.setDescription(description);
        return materialRepository.save(m);
    }

    public Material getMaterial(int id, HttpSession session) {
        CustomUserDetails user = (CustomUserDetails) session.getAttribute("currentUser");
        Material m = getMaterialById(id);
        if (user.getId() == m.getUserID()) {
            return m;
        }
        return null;
    }

    public Material deleteMaterial(int id, HttpSession session) {
        try {
            Material m = getMaterialById(id);
            CustomUserDetails user = (CustomUserDetails) session.getAttribute("currentUser");
            if (m.getUserID() == user.getId()) {
                googleDriveService.deleteFile(m.getDownloadUrl().substring(47));
                materialRepository.delete(m);
                return m;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<Material> getLatestMaterial() {
        return materialRepository.findTop2ByOrderByIdDesc();
    }

}
