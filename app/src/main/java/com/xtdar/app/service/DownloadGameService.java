package com.xtdar.app.service;


import android.app.DownloadManager;
import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.FileCallback;
import com.orhanobut.logger.Logger;
import com.xtdar.app.common.NLog;
import com.xtdar.app.widget.ZipExtractorTask;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;


public class DownloadGameService extends Service {
    private static final long SCANTIME = 3000;
    private static final String TAG = DownloadGameService.class.getSimpleName();
    public DownloadGameBinder mBinder = new DownloadGameBinder();
    private Handler threadHandler = new Handler(Looper.getMainLooper());
    private DownloadCallback mCallback = null;

    private String name;
    private String mac;
    private BluetoothGatt gatt;
    private BluetoothGattService service;
    private BluetoothGattCharacteristic characteristic;
    private int charaProp;

    @Override
    public void onCreate() {


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //bleManager = null;
        mCallback = null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.d("绑定服务成功：");
       final String s = intent.getStringExtra("HomeFragment");
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        String s = intent.getStringExtra("HomeFragment");
        Logger.d(s+"重新绑定服务打印："+s);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //bleManager.closeBluetoothGatt();
        return super.onUnbind(intent);
    }

    public class DownloadGameBinder extends Binder {
        public DownloadGameService getService() {
            return DownloadGameService.this;
        }
    }

    public void setDownloadCallback(DownloadCallback callback) {
        mCallback = callback;
    }

    public interface DownloadCallback {

        void onProgessUpdate(float progress);
        void onDownloadDone();

    }

    public void startDownload(String resourceUrl) {
        String url = "http://120.24.231.219/kp_dyz/app_source/dl/aaa.zip";

            OkHttpUtils.get(url)
                    .url(url)
                    .tag(this)
                    .execute(fileCallback);


    }

    FileCallback fileCallback=new FileCallback("/sdcard/download/", "aaaaaaaaa.zip") {
        @Override
        public void onSuccess(File file, Call call, Response response) {
            NLog.d(TAG, file.getAbsolutePath());
            ZipExtractorTask zip = new ZipExtractorTask(file.getAbsolutePath(), "/sdcard/download/aaaaaaaaa", DownloadGameService.this,true);
            zip.setUnZipCallback(new ZipExtractorTask.UnZipCallback() {
                @Override
                public void onDone() {
                    if (mCallback != null) {
                        mCallback.onDownloadDone();
                    }
                }
            });
            zip.execute();

        }

        @Override
        public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
            super.downloadProgress(currentSize, totalSize, progress, networkSpeed);

            if (mCallback != null) {
                mCallback.onProgessUpdate(progress);
            }
            NLog.d(TAG, progress);
        }


    };


    private void runOnMainThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        } else {
            threadHandler.post(runnable);
        }
    }


}
