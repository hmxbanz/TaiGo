package com.xtdar.app.presenter;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.BleAdapter;
import com.xtdar.app.ble.BleScanner;
import com.xtdar.app.common.NLog;
import com.xtdar.app.common.NToast;
import com.xtdar.app.common.NumberUtils;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.server.response.HelpResponse;
import com.xtdar.app.view.activity.BleActivity;
import com.xtdar.app.view.activity.ControllerActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hmxbanz on 2017/4/5.
 */

public class BlePresenter extends BasePresenter implements OnDataListener, BleScanner.ScanCallback,BleAdapter.OnItemClick {
    private static final String DEVICE_UUID_PREFIX = "dferewre";
    private static final String TAG = BlePresenter.class.getSimpleName();
    private static final long SCAN_PERIOD = 10000;
    private static final int BINDDEVICE = 1;
    private final Handler handler;
    private ListView listView;
    private List<BluetoothDevice> list=new ArrayList<>();
    private BleScanner bleScanner;
    private BleAdapter adapter;

    private BleActivity activity;
    private boolean isScanning=true;
    private Button btnScan;
    private BluetoothDevice selectedDevice;

    public BlePresenter(Context context) {
        super(context);
        activity = (BleActivity) context;
        adapter = new BleAdapter(context);
        handler = new Handler();
        bleScanner = new BleScanner(context);
    }

    public void init(ListView listView, Button btnScan) {
        this.listView = listView;
        this.btnScan=btnScan;

        setBleScanner();
    }

    private void setBleScanner()
    {
        int result=bleScanner.init();
        NToast.shortToast(context,bleScanner.getError(result));
        if(result != BleScanner.ERROR_BLE_START)
            startBleScanner();
    }

    public void startBleScanner()
    {
        bleScanner.init();
        bleScanner.startScan(this);
        btnScan.setText("正在扫描...");
        btnScan.setEnabled(false);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bleScanner.stopScan();
                btnScan.setText("再次扫描");
                btnScan.setEnabled(true);
            }
        }, SCAN_PERIOD);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return mUserAction.bindDevice(selectedDevice.getName());
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        CommonResponse response = (CommonResponse) result;
        if (response != null && response.getCode() == XtdConst.SUCCESS) {

        }
        NToast.shortToast(context,response.getMsg());
    }

    @Override
    public void onBleScan(BluetoothDevice device) {
        adapter.setmList(list);
        adapter.setOnItemClick(this);
        listView.setAdapter(adapter);
        NToast.shortToast(context,device.getAddress());
        atm.request(BINDDEVICE,this);
        if (list.indexOf(device) == -1 && device.getName() !=null){// 防止重复添加
            list.add(device);
        }

    }

    @Override
    public boolean onItemClick(int position, View view, int status) {
        bleScanner.stopScan();
        selectedDevice = (BluetoothDevice) adapter.getItem(position);
        //查询服务器并将枪添加到用户
atm.request(BINDDEVICE,this);
//        Intent intent = new Intent(context, ControllerActivity.class);
//        intent.putExtra("device", selectedDevice);
//        context.startActivity(intent);
        return false;
    }
}