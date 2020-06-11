package com.example.hades.retrofit2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private View mProgressBar;

    private final String EXAMPLE_URL_1 = "https://github.com/";
    private final String EXAMPLE_FILE_URL_1 = "GameJs/gamejs/archive/master.zip";

    private final String EXAMPLE_URL_2 = "https://github.com/";
    private final String EXAMPLE_FILE_URL_2 = "AtomicGameEngine/AtomicGameEngine/archive/master.zip";

    private final String EXAMPLE_URL_3 = "https://gitee.com/";
    private final String EXAMPLE_FILE_URL_3 = "YingVickyCao/ServerMocker/raw/master/full.zip";

    // 6.8MB
    private final String EXAMPLE_URL_4 = "https://gitee.com/";
    private final String EXAMPLE_FILE_URL_4 = "YingVickyCao/ServerMocker/raw/master/full2.zip";

    private final String BASE_URL = EXAMPLE_URL_4;
    private final String FILE_URL = EXAMPLE_FILE_URL_4;

    private final String FILE_NAME = "full.zip";
    private long mTs1;
    private long mTs2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progressBar);
        findViewById(R.id.download_zip).setOnClickListener(view -> downloadZipFile_NotUseStreaming());
        findViewById(R.id.download_zip_use_streaming).setOnClickListener(view -> downloadZipFile_UseStreaming());
        findViewById(R.id.download_zip_rxjava).setOnClickListener(view -> downloadZipFileRx());
    }

    private void downloadZipFileRx() {
        showProgressBar();
        IDownloadZipService downloadService = createService(IDownloadZipService.class, BASE_URL);
        downloadService.downloadFileByUrlRx(FILE_URL)
                .flatMap(processDownload())
                .flatMap(unpackZip())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(handleResult());
    }

    private Func1<Response<ResponseBody>, Observable<File>> processDownload() {
        return new Func1<Response<ResponseBody>, Observable<File>>() {
            @Override
            public Observable<File> call(Response<ResponseBody> responseBodyResponse) {
                return saveToDiskRx(responseBodyResponse);
            }
        };
    }

    private Func1<File, Observable<File>> unpackZip() {
        return new Func1<File, Observable<File>>() {
            @Override
            public Observable<File> call(File file) {
                InputStream is;
                ZipInputStream zis;
                String parentFolder;
                String filename;
                try {
                    parentFolder = file.getParentFile().getPath();

                    is = new FileInputStream(file.getAbsolutePath());
                    zis = new ZipInputStream(new BufferedInputStream(is));
                    ZipEntry ze;
                    byte[] buffer = new byte[1024];
                    int count;

                    while ((ze = zis.getNextEntry()) != null) {
                        filename = ze.getName();

                        if (ze.isDirectory()) {
                            File fmd = new File(parentFolder + "/" + filename);
                            fmd.mkdirs();
                            continue;
                        }

                        FileOutputStream fout = new FileOutputStream(parentFolder + "/" + filename);

                        while ((count = zis.read(buffer)) != -1) {
                            fout.write(buffer, 0, count);
                        }

                        fout.close();
                        zis.closeEntry();
                    }

                    zis.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                File extractedFile = new File(file.getAbsolutePath().replace(".zip", ""));
                if (!file.delete()) Log.d("unpackZip", "Failed to deleted the zip file.");
                return Observable.just(extractedFile);
            }
        };
    }

    private Observer<File> handleResult() {
        return new Observer<File>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                hideProgressBar();
                e.printStackTrace();
                Log.d(TAG, "Error " + e.getMessage());
            }

            @Override
            public void onNext(File file) {
                Log.d(TAG, "File downloaded and extracted to " + file.getAbsolutePath());
                hideProgressBar();
            }
        };
    }

    private void downloadZipFile_UseStreaming() {
        showProgressBar();
        IDownloadZipService downloadService = createService(IDownloadZipService.class, BASE_URL);
        Call<ResponseBody> call = downloadService.downloadFile_Streaming(FILE_URL);
        downloadZipFile(call);
    }

    private void downloadZipFile_NotUseStreaming() {
        showProgressBar();
        IDownloadZipService downloadService = createService(IDownloadZipService.class, BASE_URL);
        Call<ResponseBody> call = downloadService.downloadFile(BASE_URL + FILE_URL);
        downloadZipFile(call);
    }

    private void downloadZipFile(Call<ResponseBody> call) {
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Got the body for the file");
                    new AsyncTask<Void, Long, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            saveToDisk(response.body(), FILE_NAME);
                            return null;
                        }
                    }.execute();

                } else {
                    Log.d(TAG, "Connection failed " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e(TAG, t.getMessage());
                hideProgressBar();
            }
        });
    }

    private void showProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void hideProgressBar() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    private File setDestinationFilePath(String filename) {
        new File("/data/data/" + getPackageName() + "/games").mkdir();
        File destinationFile = new File("/data/data/" + getPackageName() + "/games/" + filename);
        return destinationFile;
    }

    private void saveToDisk(ResponseBody body, String filename) {
        mTs1 = System.currentTimeMillis();
        try {
            File destinationFile = setDestinationFilePath(filename);
            InputStream is = null;
            OutputStream os = null;

            try {
                long filesize = body.contentLength();
                Log.d(TAG, "File Size=" + filesize);
                is = body.byteStream();
                os = new FileOutputStream(destinationFile);

                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                while ((count = is.read(data)) != -1) {
                    os.write(data, 0, count);
                    progress += count;
                    Log.d(TAG, "Progress: " + progress + "/" + filesize + " >>>> " + (float) progress / filesize);
                }
                os.flush();
                Log.d(TAG, "File saved successfully!");
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Failed to save the file!");
                return;
            } finally {
                if (is != null) is.close();
                if (os != null) os.close();
                hideProgressBar();
                mTs2 = System.currentTimeMillis();
                Log.e(TAG, "saveToDisk: ms=" + (mTs2 - mTs1) + "");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "Failed to save the file!");
            return;
        }
    }

    private Observable<File> saveToDiskRx(final Response<ResponseBody> response) {
        return Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                try {
                    File destinationFile = setDestinationFilePath(FILE_NAME);
                    BufferedSink bufferedSink = Okio.buffer(Okio.sink(destinationFile));
                    bufferedSink.writeAll(response.body().source());
                    bufferedSink.close();
                    subscriber.onNext(destinationFile);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }

    public <T> T createService(Class<T> serviceClass, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    private OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
//                .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS,ConnectionSpec.COMPATIBLE_TLS))
                .build();
    }
}