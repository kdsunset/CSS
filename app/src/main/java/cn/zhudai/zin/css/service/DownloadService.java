package cn.zhudai.zin.css.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.IOException;

import cn.zhudai.zin.css.api.CSSApi;
import cn.zhudai.zin.css.app.AppConfig;
import cn.zhudai.zin.css.interf.ICallbackResult;
import cn.zhudai.zin.uiokhttp.callback.FileCallback;

/**
 * Created by admin on 2016/9/14.
 */
public class DownloadService extends Service {
    private Thread downLoadThread;

    public static final String BUNDLE_KEY_DOWNLOAD_URL = "download_url";
    public static final String BUNDLE_KEY_TITLE = "title";
    private String saveFileName = AppConfig.DEFAULT_SAVE_FILE_PATH;
    private String downloadUrl;
    private int progress;
    private boolean canceled;
    private boolean serviceIsDestroy = false;
    private ICallbackResult callback;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private void downloadNow() {
        downLoadThread = new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(AppConfig.DEFAULT_SAVE_FILE_PATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String apkFile = saveFileName;
                File saveFile = new File(apkFile);
                try {
                    downloadUpdateFile(downloadUrl, saveFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        });
        downLoadThread.start();
    }
    private void downloadUpdateFile(String downloadUrl, File saveFile) {
        CSSApi.downloadApk(new FileCallback<File>(){
            @Override
            public void onResponse(File f) {
                super.onResponse(f);
            }

            @Override
            public void inProgress(float progress, long total) {
                super.inProgress(progress, total);
            }

            @Override
            public void onFailure(IOException e) {
                super.onFailure(e);
            }
        });

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public class DownloadBinder extends Binder {
        public void start() {
            if (downLoadThread == null || !downLoadThread.isAlive()) {
                progress = 0;
               /* setUpNotification();
                new Thread() {
                    public void run() {
                        // 下载
                        startDownload();
                    }

                    ;
                }.start();*/
            }
        }
        public void cancel() {
            canceled = true;
        }

        public int getProgress() {
            return progress;
        }

        public boolean isCanceled() {
            return canceled;
        }

        public boolean serviceIsDestroy() {
            return serviceIsDestroy;
        }

        public void cancelNotification() {
            mHandler.sendEmptyMessage(2);
        }

        public void addCallback(ICallbackResult callback) {
            DownloadService.this.callback = callback;
        }
    }
}
