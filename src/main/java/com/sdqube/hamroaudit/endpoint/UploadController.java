package com.sdqube.hamroaudit.endpoint;

import com.sdqube.hamroaudit.payload.*;
import com.sdqube.hamroaudit.service.S3Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/4/20 7:31 PM
 */
@RestController
public class UploadController {
    private final Logger log = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    S3Services s3Services;

    @Value("${hamroaudit.s3.uploadfile}")
    private String uploadFilePath;

//    @Value("${hamroaudit.s3.key}")
//    private String downloadKey;

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("name") String filename,
                                        @RequestParam("type") String fileType) {
        try {
            log.info("File Upload: {} of Type: {}", filename, fileType);
            FileRequest fileRequest = new FileRequest(filename, fileType);
            fileRequest.setMultipartFile(file);
            FileResponse fileResponse = s3Services.uploadFile(fileRequest);
            return ResponseEntity.ok(fileResponse);
        } catch (Exception ex) {
            log.error("Error: {}", ex.toString());
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/file/{type}/{filename}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> findUser(@PathVariable("filename") String filename, @PathVariable("type") String fileType) {
        try {
            log.info("Login User: {} of Type: {}", filename, fileType);
            FileResponse fileResponse = s3Services.getFile(new FileRequest(filename, fileType));
            return ResponseEntity.ok(fileResponse);
        } catch (Exception ex) {
            log.error("Error: {}", ex.toString());
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
