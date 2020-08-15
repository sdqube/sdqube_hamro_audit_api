package com.sdqube.hamroaudit.payload;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 8/16/20 1:39 AM
 */
public class FileResponse {
    String filename;
    String url;
    String path;

    boolean isSuccess = false;

    public FileResponse() {
    }

    public FileResponse(String filename, String url, String path) {
        this.filename = filename;
        this.url = url;
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
