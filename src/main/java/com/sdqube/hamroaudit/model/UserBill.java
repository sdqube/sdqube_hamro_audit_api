package com.sdqube.hamroaudit.model;

import com.sdqube.hamroaudit.payload.FileResponse;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 8/16/20 2:07 PM
 */
@Document
public class UserBill {

    @Id
    private String billId;

    private String username;
    private String userId;
    private String billType;
    private String filename;
    private FileResponse fileResponse;

    private boolean isDeleted = false;
    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public UserBill() {
    }

    public UserBill(String billId, String username, String userId, String billType, String filename, boolean isDeleted, Instant createdAt, Instant updatedAt) {
        this.billId = billId;
        this.username = username;
        this.userId = userId;
        this.billType = billType;
        this.filename = filename;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public FileResponse getFileResponse() {
        return fileResponse;
    }

    public void setFileResponse(FileResponse fileResponse) {
        this.fileResponse = fileResponse;
    }

    @Override
    public String toString() {
        return "UserBill{" +
                "billId='" + billId + '\'' +
                ", username='" + username + '\'' +
                ", userId='" + userId + '\'' +
                ", billType='" + billType + '\'' +
                ", filename='" + filename + '\'' +
                ", fileResponse=" + fileResponse +
                ", isDeleted=" + isDeleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
