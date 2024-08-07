package com.sdqube.hamroaudit.payload;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 8/16/20 1:39 AM
 */
public class FileResponse {
    String filename = "";
    String url = "";
    String path = "";

    boolean isSuccess = false;
    ErrorCode errorCode = ErrorCode.UNKNOWN_ERROR;
    String errorMsg = "";

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

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "FileResponse{" +
                "filename='" + filename + '\'' +
                ", url='" + url + '\'' +
                ", path='" + path + '\'' +
                ", isSuccess=" + isSuccess +
                ", errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
