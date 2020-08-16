package com.sdqube.hamroaudit.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.sdqube.hamroaudit.payload.ErrorCode;
import com.sdqube.hamroaudit.payload.FileRequest;
import com.sdqube.hamroaudit.payload.FileResponse;
import com.sdqube.hamroaudit.utils.ResponseUtils;
import com.sdqube.hamroaudit.utils.S3Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 8/16/20 1:02 AM
 */
@Service
public class S3ServicesImpl implements S3Services {

    private final Logger logger = LoggerFactory.getLogger(S3ServicesImpl.class);

    @Autowired
    private AmazonS3 s3client;


    @Value("${hamroaudit.s3.uploadfile}")
    private String uploadFilePath;

    @Value("${hamroaudit.s3.bucket}")
    private String bucketName;

    public S3ServicesImpl() {
    }

    @Override
    public FileResponse getFile(FileRequest fileRequest) {
        FileResponse fileResponse = new FileResponse();
        try {
            String filename = fileRequest.getFilename();
            String fileType = fileRequest.getType();
            String filepath = fileType + "/" + filename;

            // Set the presigned URL to expire after one hour.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 60;
            expiration.setTime(expTimeMillis);

            // Generate the presigned URL.
            System.out.println("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, filepath)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3client.generatePresignedUrl(generatePresignedUrlRequest);
            fileResponse.setFilename(url.getFile());
            fileResponse.setUrl(url.toString());
            fileResponse.setPath(url.getPath());

            fileResponse.setSuccess(true);
        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        }
        return fileResponse;
    }

    @Override
    public void downloadFile(String keyName) {
        try {
            logger.info("Downloading an object. {}/{} ", bucketName, keyName);
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
            logger.info("Content-Type: " + s3object.getObjectMetadata().getContentType());
            ResponseUtils.displayText(s3object.getObjectContent());
            logger.info("===================== Import File - Done! =====================");

        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        } catch (IOException ioe) {
            logger.info("IOE Error Message: " + ioe.getMessage());
        }
    }


    @Override
    public FileResponse uploadFile(FileRequest fileRequest) {
        FileResponse fileResponse = new FileResponse();
        try {
            if (null == fileRequest.getMultipartFile()) {
                fileResponse.setErrorCode(ErrorCode.NOTFOUND);
                fileResponse.setErrorMsg("File should be selected to upload.");
                return fileResponse;
            }

            String fileName = uploadFilePath + new S3Utils(uploadFilePath).storeFile(fileRequest.getMultipartFile());
            File file = new File(fileName);
            ObjectMetadata objMetadata = new ObjectMetadata();
            objMetadata.setContentLength(file.length());

            String fileUploadPath = fileRequest.getType() + "/" + fileRequest.getFilename();

            PutObjectResult putObjectResult = s3client.putObject(new PutObjectRequest(bucketName, fileUploadPath,
                    new FileInputStream(file), objMetadata));
            logger.info("===================== Upload File - Done! =====================");
            fileResponse.setSuccess(true);
            fileResponse.setFilename(fileUploadPath);
            fileResponse.setPath(uploadFilePath);
            file.deleteOnExit();
        } catch (AmazonServiceException ase) {
            logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        } catch (FileNotFoundException ex) {
            logger.error("FileNotFoundError: {}", ex);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileResponse;
    }

}