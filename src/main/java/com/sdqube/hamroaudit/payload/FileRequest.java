package com.sdqube.hamroaudit.payload;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 8/16/20 1:10 AM
 */
public class FileRequest {
    String filename;
    String filePath;
    String fileSize;
    String type;
    MultipartFile multipartFile;

    public String getFilename() {
        return filename;
    }

    public FileRequest(String filename, String type) {
        this.filename = filename;
        this.type = type;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
