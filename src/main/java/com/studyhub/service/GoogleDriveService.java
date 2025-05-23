package com.studyhub.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleDriveService {

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

    public List<String> uploadFile(File file) {
        try {
            String folderId = "1SLlIrE2IIvNpFn6Xg91y0eZlOCX-UB-V";
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

            String viewUrl = "https://drive.google.com/file/d/" + fileId + "/edit";
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
        String credentialsJson = System.getenv("GOOGLE_CREDENTIALS");
        if (credentialsJson == null) {
            throw new IOException("GOOGLE_CREDENTIALS environment variable not set");
        }
        InputStream credentialsStream = new ByteArrayInputStream(credentialsJson.getBytes());
        GoogleCredential credentials = GoogleCredential.fromStream(credentialsStream)
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                credentials).build();
    }

    public void deleteFile(String fileId) throws GeneralSecurityException, IOException {
        Drive drive = createDriveService();
        drive.files().delete(fileId).execute();
    }
}