package com.example.hades.retrofit2;

public interface IDownloadProgress {
    void update(long bytesRead, long length, boolean done);
}
