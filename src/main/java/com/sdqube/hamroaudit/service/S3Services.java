package com.sdqube.hamroaudit.service;

import com.sdqube.hamroaudit.payload.FileRequest;
import com.sdqube.hamroaudit.payload.FileResponse;

/**
 * Created by >Sagar Duwal<
 * Github: @sagarduwal
 * Date: 8/16/20 1:01 AM
 */
public interface S3Services {

    public FileResponse getFile(FileRequest fileRequest);

    public void downloadFile(String keyName);

    public FileResponse uploadFile(FileRequest fileRequest);
}