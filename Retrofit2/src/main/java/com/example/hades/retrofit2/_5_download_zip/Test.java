package com.example.hades.retrofit2._5_download_zip;

public class Test {
    public static void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                DownloadZipTool downloadZipTool = new DownloadZipTool();
                downloadZipTool.startDownload();
            }
        }).start();
    }
}
