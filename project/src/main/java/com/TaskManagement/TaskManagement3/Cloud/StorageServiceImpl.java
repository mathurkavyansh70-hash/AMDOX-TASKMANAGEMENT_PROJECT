package com.TaskManagement.TaskManagement3.Cloud;

import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Paths;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public abstract class StorageServiceImpl implements StorageService {
    @Autowired
    private Cloudinary cloudinary;

    public String store(MultipartFile file, String relativeFolder) {

        try {

            Map upload = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("folder", relativeFolder, "resouce_type", "auto"));

            return (String) upload.get("secure_url");
        } catch (IOException e) {

            throw new RuntimeException("Failed to store file", e);

        }
    }

    public byte[] read(String storagePath) {
        try {
            return Files.readAllBytes(Paths.get(storagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete() {
        try {
            cloudinary.uploader().destroy(null, ObjectUtils.emptyMap());
        } catch (Exception e) {
            throw new RuntimeException("Cloudinary delete failed");
        }
    }
}
