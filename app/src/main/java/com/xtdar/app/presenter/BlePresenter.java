package com.xtdar.app.presenter;

import android.Manifest;
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
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.xtdar.app.server.response.BindResponse;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.server.response.HelpResponse;
import com.xtdar.app.service.BluetoothService;
import com.xtdar.app.view.activity.BleActivity;
import com.xtdar.app.view.activity.ControllerActivity;
import com.xtdar.app.view.activity.HelpDetailActivity;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.QrCodeActivity;
import com.xtdar.app.view.activity.UnityPlayerActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;
import com.xtdar.app.widget.progressBar.MaterialProgressBar;

import java.util.ArrayList;
import java.util.List;

import static com.xtdar.app.view.activity.BleActivity.REQUEST_CODE;


/**
 * Created by hmxbanz on 2017/4/5.
 */

public class BlePresenter extends BasePresenter implements OnDataListener, BleAdapter.OnItemClick {
    private static final String DEVICE_UUID_PREFIX = "dferewre";
    private static final String TAG = BlePresenter.class.getSimpleName();
    private static final long SCAN_PERIOD = 10000;
    private static final int BINDDEVICE = 1;
    private static final int GETHELP = 2;
    private static final int SCANDEVICE = 3;
    private static final int BINDQRCODE = 4;
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
    private MaterialProgressBar progressWheel;
    private RelativeLayout emptyView;
    private String driverId,qrCode;

    public BlePresenter(Context context) {
        super(context);
        activity = (BleActivity) context;
        adapter = new BleAdapter(context);
        adapter.setOnItemClick(this);
        basePresenter = BasePresenter.getInstance(context);
    }

    public void init(ListView listView, Button btnScan, MaterialProgressBar progressWheel, RelativeLayout emptyView) {
        this.listView = listView;
        this.btnScan=btnScan;
        this.progressWheel=progressWheel;
        this.emptyView=emptyView;
        listView.setAdapter(adapter);


        // 为了确保设备上蓝牙能使用, 如果当前蓝牙设备没启用,弹出对话框向用户要求授予权限来启用
        //Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        //((Activity)context).startActivityForResult(enableBtIntent, 100);

        //checkPermissions();

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
            listView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            progressWheel.setVisibility(View.VISIBLE);
            btnScan.setText("正在扫描...");
            btnScan.setEnabled(false);
        }

        @Override
        public void onScanning(ScanResult result) {

            if(result.getDevice().getName() !=null && (result.getDevice().getName().contains("Taigo")
                    || result.getDevice().getName().contains("BLE"))) {
                listView.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
                adapter.addResult(result);
            }
            adapter.notifyDataSetChanged();

        }

        @Override
        public void onScanComplete() {
                progressWheel.setVisibility(View.GONE);
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

    //    @Override
//    public void onBleScan(BluetoothDevice device) {
//        adapter.setmList(list);
//        adapter.setOnItemClick(this);
//        listView.setAdapter(adapter);
//        NToast.shortToast(context,device.getAddress());
//        atm.request(BINDDEVICE,this);
//                if (list.indexOf(device) == -1 && device.getName() !=null){// 防止重复添加list.add(device);
//            }
//
//    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case BINDDEVICE:
                return mUserAction.bindDevice(selectedDevice.getDevice().getAddress());
            case BINDQRCODE:
                return mUserAction.bindQrcode(selectedDevice.getDevice().getAddress(),qrCode);

        }
            return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result==null)return;

        switch (requestCode) {
            case BINDDEVICE:
                BindResponse response = (BindResponse) result;
                if (response != null && response.getCode() == XtdConst.SUCCESS) {
                    Intent intent = new Intent(context, HelpDetailActivity.class);
                    intent.putExtra("title","设备绑定成功");
                    intent.putExtra("url",XtdConst.SERVERURI+"cli-dgc-devicehelp.php?device_id="+response.getData().getDevice_id());
                    context.startActivity(intent);
                }
                else if(response != null && response.getCode() == 2)//请扫瞄枪托后面的二维码进行绑定
                {
                    Intent intent = new Intent(context, QrCodeActivity.class);
                    activity.startActivityForResult(intent, REQUEST_CODE);
                }
                NToast.shortToast(context,response.getMsg());
                break;
            case BINDQRCODE:
                BindResponse response2 = (BindResponse) result;
                if (response2 != null && response2.getCode() == XtdConst.SUCCESS) {
                    Intent intent = new Intent(context, HelpDetailActivity.class);
                    intent.putExtra("title","设备绑定成功");
                    intent.putExtra("url",XtdConst.SERVERURI+"cli-dgc-devicehelp.php?device_id="+response2.getData().getDevice_id());
                    context.startActivity(intent);
                }
                NToast.shortToast(context,response2.getMsg());
                break;
        }



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
            DialogWithYesOrNoUtils.getInstance().showDialog(context, "请先登录再绑定设备",null, "前住登录", new AlertDialogCallback() {
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

    private void checkPermissions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(context, permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permission);
            } else {
                permissionDeniedList.add(permission);
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            String[] deniedPermissions = permissionDeniedList.toArray(new String[permissionDeniedList.size()]);
            ActivityCompat.requestPermissions((BleActivity)context, deniedPermissions, 12);
        }
    }
    public void onPermissionGranted(String permission) {
        switch (permission) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                if (mBluetoothService == null) {
                    bindService();
                } else {
                    mBluetoothService.scanDevice();
                }
                break;
        }
    }

    public void bindQrCode(String qrCode) {
        this.qrCode=qrCode;
        LoadDialog.show(context);
        atm.request(BINDQRCODE,this);
    }
}