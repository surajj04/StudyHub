package com.studyhub.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MaterialUpload {
    private String title;
    private String description;
    private String category;
    private String fileType;
    private MultipartFile file;

}
