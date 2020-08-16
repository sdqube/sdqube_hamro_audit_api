package com.sdqube.hamroaudit.utils;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 8/16/20 1:12 PM
 */
public class S3Utils {

    private final Path fileStorageLocation;

    public S3Utils(String fileStorageLocation) {
        System.out.println("fileStorageLocation = " + fileStorageLocation);
        if(StringUtils.isEmpty(fileStorageLocation)) fileStorageLocation  = "/hamro-audit";
        this.fileStorageLocation = Paths.get(fileStorageLocation)
                .toAbsolutePath().normalize();
    }

    public String storeFile(MultipartFile file) throws Exception {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {

            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileNotFoundException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            System.out.println("targetLocation = " + targetLocation);
            System.out.println("fileName = " + fileName);
            Files.createFile(targetLocation);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new Exception("Could not store file " + fileName + ". Please try again! " + ex);
        }
    }

}
