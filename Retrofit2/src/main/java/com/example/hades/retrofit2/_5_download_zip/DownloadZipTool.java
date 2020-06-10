package com.example.hades.retrofit2._5_download_zip;

import com.example.hades.retrofit2.services.FileDownloadService;
import com.example.hades.retrofit2.services.FileDownloadServiceCreator;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.*;

import static com.example.hades.retrofit2.services.FileDownloadService.ZIP_FILE;

public class DownloadZipTool {
    private static final String TAG = DownloadZipTool.class.getSimpleName();

    public void startDownload() {
        FileDownloadService downloadService = null;
        try {
            downloadService = new FileDownloadServiceCreator().create();

//            Call<ResponseBody> call = downloadService.downloadFileWithDynamicUrlSync(ZIP_FILE);
            Call<ResponseBody> call = downloadService.downloadFileWithDynamicUrlSync2(ZIP_FILE);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        System.out.println(TAG + " : server contacted and has file");
                        boolean writtenToDisk = writeResponseBodyToDisk(response.body());
//                        File file1 = new File("./file1.txt");
//                        System.out.println(file1.toString());
                        System.out.println(TAG + " : " + "file download was a success? " + writtenToDisk);
                    } else {
                        System.out.println(TAG + " : " + "server contact failed");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println(call);
                    t.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getExternalFilesDir() {
        return new File(".").getAbsolutePath();
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File file = new File(getExternalFilesDir() + File.separator + "full.zip");

            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength(); // TODO: file size  = -1
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    System.out.println(TAG + " : " + "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }
}