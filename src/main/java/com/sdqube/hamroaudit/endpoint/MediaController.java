package com.sdqube.hamroaudit.endpoint;

import com.sdqube.hamroaudit.model.AuditUserDetails;
import com.sdqube.hamroaudit.model.UserBill;
import com.sdqube.hamroaudit.payload.FileRequest;
import com.sdqube.hamroaudit.payload.FileResponse;
import com.sdqube.hamroaudit.service.S3Services;
import com.sdqube.hamroaudit.service.UserBillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 7/4/20 7:31 PM
 */
@RestController
@RequestMapping("/api")
public class MediaController {
    private final Logger log = LoggerFactory.getLogger(MediaController.class);

    @Autowired
    S3Services s3Services;

    @Autowired
    UserBillService userBillService;

    @Value("${hamroaudit.s3.uploadfile}")
    private String uploadFilePath;

//    @Value("${hamroaudit.s3.key}")
//    private String downloadKey;

    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,
                                        @RequestParam("billType") String billType,
                                        @AuthenticationPrincipal AuditUserDetails userDetails) {
        try {
            String filename = file.getOriginalFilename();
            log.info("Authenticated User: {}", userDetails);
            log.info("File Upload: {} of Type: {}", filename, billType);
            FileRequest fileRequest = new FileRequest(filename, billType);
            fileRequest.setMultipartFile(file);
            FileResponse fileResponse = s3Services.uploadFile(fileRequest);

            UserBill userBill = userBillService.storeUserBill(billType, userDetails, fileResponse);

            return ResponseEntity.ok(userBill);
        } catch (Exception ex) {
            log.error("Error: {}", ex.toString());
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/bill/{billType}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getBillsForUser(@PathVariable("billType") String billType,
                                             @AuthenticationPrincipal AuditUserDetails userDetails) {
        try {
            log.info("Login User: {} of Type: {}", userDetails, billType);
            String userId = userDetails.getId();
            List<UserBill> billsByUserId = userBillService.getBillsByUserIdType(userId, billType);
            log.info("Number of bills: {}", billsByUserId.size());
            return ResponseEntity.ok(billsByUserId);
        } catch (Exception ex) {
            log.error("Error: {}", ex.toString());
            return ResponseEntity.ok(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/file/{type}/{filename}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getFiles(@PathVariable("filename") String filename, @PathVariable("type") String fileType) {
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
