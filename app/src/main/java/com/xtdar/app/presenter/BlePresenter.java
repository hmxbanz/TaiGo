package com.xtdar.app.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.clj.fastble.data.ScanResult;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.BleAdapter;
import com.xtdar.app.ble.BleScanner;
import com.xtdar.app.common.NLog;
import com.xtdar.app.common.NToast;
import com.xtdar.app.common.NumberUtils;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.server.response.HelpResponse;
import com.xtdar.app.service.BluetoothService;
import com.xtdar.app.view.activity.BleActivity;
import com.xtdar.app.view.activity.ControllerActivity;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.UnityPlayerActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;

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
    //private final Handler handler;
    private ListView listView;
    private List<BluetoothDevice> list=new ArrayList<>();
    private BleScanner bleScanner;
    private BleAdapter adapter;

    private BleActivity activity;
    private boolean isScanning=true;
    private Button btnScan;
    private ScanResult selectedDevice;
    private BluetoothService mBluetoothService;

    private BasePresenter basePresenter;

    public BlePresenter(Context context) {
        super(context);
        activity = (BleActivity) context;
        adapter = new BleAdapter(context);
        adapter.setOnItemClick(this);
        basePresenter = BasePresenter.getInstance(context);
    }

    public void init(ListView listView, Button btnScan) {
        this.listView = listView;
        listView.setAdapter(adapter);
        this.btnScan=btnScan;
        // 为了确保设备上蓝牙能使用, 如果当前蓝牙设备没启用,弹出对话框向用户要求授予权限来启用
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        ((Activity)context).startActivityForResult(enableBtIntent, 100);

        bindService();

    }

    public void bindService() {
        Intent bindIntent = new Intent(context, BluetoothService.class);
        context.bindService(bindIntent, mFhrSCon, Context.BIND_AUTO_CREATE);
    }

    public void startService() {
        if (mBluetoothService == null) {
            bindService();
        } else {
            mBluetoothService.scanDevice();
        }
    }

    private void unbindService() {
        context.unbindService(mFhrSCon);
    }


    private ServiceConnection mFhrSCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBluetoothService = ((BluetoothService.BluetoothBinder) service).getService();
            mBluetoothService.setScanCallback(callback);
            mBluetoothService.scanDevice();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBluetoothService = null;
        }
    };

    private BluetoothService.Callback callback = new BluetoothService.Callback() {
        @Override
        public void onStartScan() {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }

        @Override
        public void onScanning(ScanResult result) {
            adapter.addResult(result);
            adapter.notifyDataSetChanged();
        btnScan.setText("正在扫描...");
        btnScan.setEnabled(false);
        }

        @Override
        public void onScanComplete() {
                btnScan.setText("再次扫描");
                btnScan.setEnabled(true);
        }

        @Override
        public void onConnecting() {
            LoadDialog.show(context);
        }

        @Override
        public void onConnectFail() {
            LoadDialog.dismiss(context);
            NToast.shortToast(context, "连接失败");
        }

        @Override
        public void onDisConnected() {
            NToast.longToast(context, "连接断开");
        }

        @Override
        public void onServicesDiscovered() {
            LoadDialog.dismiss(context);
            context.startActivity(new Intent(context, UnityPlayerActivity.class));
        }
    };

//    public void startBleScanner()
//    {
//        bleScanner.init();
//        bleScanner.startScan(this);
//        btnScan.setText("正在扫描...");
//        btnScan.setEnabled(false);
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                bleScanner.stopScan();
//                btnScan.setText("再次扫描");
//                btnScan.setEnabled(true);
//            }
//        }, SCAN_PERIOD);
//    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return mUserAction.bindDevice(selectedDevice.getDevice().getName());
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
//        adapter.setmList(list);
//        adapter.setOnItemClick(this);
//        listView.setAdapter(adapter);
//        NToast.shortToast(context,device.getAddress());
//        atm.request(BINDDEVICE,this);
//        if (list.indexOf(device) == -1 && device.getName() !=null){// 防止重复添加
//            list.add(device);
//        }

    }

    @Override
    public boolean onItemClick(int position, View view, int status) {
     //   bleScanner.stopScan();
        selectedDevice = (ScanResult) adapter.getItem(position);
        //查询服务器并将枪添加到用户
        if(basePresenter.isLogin)
            atm.request(BINDDEVICE,this);
        else
        {
            DialogWithYesOrNoUtils.getInstance().showDialog(context, "请先登录再绑定设备", "前住登录", new AlertDialogCallback() {
                @Override
                public void executeEvent() {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

            });
        }

//        Intent intent = new Intent(context, ControllerActivity.class);
//        intent.putExtra("device", selectedDevice);
//        context.startActivity(intent);

        return false;
    }

    public void onDestroy() {
        if (mBluetoothService != null)
            unbindService();
    }
}