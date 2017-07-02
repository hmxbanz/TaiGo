package com.xtdar.app.service;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.xtdar.app.adapter.BleAdapter;
import com.xtdar.app.ble.BleConnector;
import com.xtdar.app.ble.BleScanner;
import com.xtdar.app.common.NLog;
import com.xtdar.app.common.NToast;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.activity.UnityPlayerActivity;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PVer on 2017/7/1.
 */

public class DeviceConectService extends Service implements BleScanner.ScanCallback {
    private ConectBinder binder;
    private List<BluetoothDevice> list=new ArrayList<>();
    private BleScanner bleScanner;
    private BleAdapter adapter;
    private BluetoothDevice selectedDevice;
    private Handler handler;
    private static final long SCAN_PERIOD = 10000;
    private BleConnector connector;
    private String deviceName;

    @Override
    public void onCreate() {
        super.onCreate();
        adapter = new BleAdapter(this);
        handler = new Handler();
        bleScanner = new BleScanner(this);
        int result=bleScanner.init();
        NToast.shortToast(this,bleScanner.getError(result));
        if(result != BleScanner.ERROR_BLE_START)
            startBleScanner();

        NLog.d("444444444","ssssssssssssssssss");
    }

    public void startBleScanner()
    {
        bleScanner.init();
        bleScanner.startScan(this);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                bleScanner.stopScan();
//                NToast.shortToast(DeviceConectService.this,"超时");
//            }
//        }, SCAN_PERIOD);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        NLog.d("555555555","ssssssssssssssssss");
        deviceName = intent.getStringExtra("deviceName");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onBleScan(BluetoothDevice device) {
        if(deviceName.equals(device.getName()))
        {
            bleScanner.stopScan();
            Intent intent = new Intent(this, UnityPlayerActivity.class);
            intent.putExtra("device", device);
            startActivity(intent);
        }
        NToast.shortToast(DeviceConectService.this,device.getName());
    }
    public void send(String command){
        //connector.sendMsg(command);
    }

    public class ConectBinder extends Binder{
        public DeviceConectService getService(){
            return DeviceConectService.this;
        }
        public BleConnector getBleConnector(){
            return connector;
        }
    }
}
