package com.studyhub.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.studyhub.model.CustomUserDetails;
import com.studyhub.model.Material;
import com.studyhub.model.MaterialUpload;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleDriveService {

    @Autowired
    private MaterialService materialService;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_KEY_PATH = getPathToGoogleCredentials();

    public Material uploadMaterial(MaterialUpload materialUpload, HttpSession session) throws IOException {
        File tempFile = File.createTempFile("upload-", materialUpload.getFile().getOriginalFilename());
        materialUpload.getFile().transferTo(tempFile);
        List<String> list = uploadFile(tempFile);
        assert list != null;
        if (list.getFirst() != null) {
            CustomUserDetails user = (CustomUserDetails) session.getAttribute("currentUser");
            return materialService.uploadMaterial(materialUpload, list, user);
        }
        return null;
    }

    private static String getPathToGoogleCredentials() {
        String currentDirectory = System.getProperty("user.dir");
        Path filePath = Paths.get(currentDirectory, "cred.json");
        return filePath.toString();
    }

    private List<String> uploadFile(File file) {
        try {
            String folderId = "1yeMUdIyE-XIMez-k5q40FWuZvwga4vqC"; // Replace with your Drive folder ID
            Drive drive = createDriveService();

            com.google.api.services.drive.model.File fileMetaData = new com.google.api.services.drive.model.File();
            fileMetaData.setName(file.getName());
            fileMetaData.setParents(Collections.singletonList(folderId));

            String mimeType = Files.probeContentType(file.toPath());
            if (mimeType == null) {
                mimeType = "application/octet-stream"; // fallback
            }

            FileContent mediaContent = new FileContent(mimeType, file);

            com.google.api.services.drive.model.File uploadedFile = drive.files()
                    .create(fileMetaData, mediaContent)
                    .setFields("id")
                    .execute();

            String fileId = uploadedFile.getId();
            String viewUrl = "https://drive.google.com/uc?export=view&id=" + fileId;
            String downloadUrl = "https://drive.google.com/uc?export=download&id=" + fileId;

            file.delete();
            List<String> list = new ArrayList<>();
            list.add(viewUrl);
            list.add(downloadUrl);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Drive createDriveService() throws IOException, GeneralSecurityException {
        GoogleCredential credentials = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_PATH))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credentials).build();
    }


}
