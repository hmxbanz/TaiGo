package com.xtdar.app.ble;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

public class BleScanner {
    public static final int INIT_OK = 0;
    /* 硬件不支持蓝牙LE */
    public static final int ERROR_DEVICE = -1;
    /* 系统版本太低 */
    public static final int ERROR_SDK = -2;
    /* 没有开启蓝牙 */
    public static final int ERROR_BLE_START = -3;
    /* 没有权限 */
    public static final int ERROR_PERMISSION = -4;

    public static final int REQUEST_ENABLE_BT = 5;
    private final Context context;

    private BluetoothAdapter mBluetoothAdapter;
    private IBleScan iBleScan;

    public BleScanner(Context context) {
        this.context=context;
    }

    public int init() {
        // Use this check to determine whether BLE is supported on the device.
        // Then you can selectively disable BLE-related features.
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            return ERROR_DEVICE;
        }
        if (Build.VERSION.SDK_INT < 18) {
            return ERROR_SDK;
        }

//        // Initializes Bluetooth adapter.
//        BluetoothManager bluetoothManager =
//                (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
//        BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // Ensures Bluetooth is available on the device and it is enabled.
        // If not, displays a dialog requesting user permission to enable Bluetooth.
//        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
//            // 为了确保设备上蓝牙能使用, 如果当前蓝牙设备没启用,弹出对话框向用户要求授予权限来启用
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            ((Activity)context).startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//            return ERROR_BLE_START;
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
            if (!Permission.containPermission(context, permissions)) {
                return ERROR_PERMISSION;
            }
        }

        //OK
        if (Build.VERSION.SDK_INT < 21) {
            iBleScan = new BleScan_18();
        } else {
            iBleScan = new BleScan_21();
        }
        return INIT_OK;
    }

    public void startScan(ScanCallback listener) {
        iBleScan.setListener(listener);
        iBleScan.startBleScan(mBluetoothAdapter);
    }

    public void stopScan() {
        if (iBleScan != null) {
            iBleScan.stopBleScan(mBluetoothAdapter);
            iBleScan.setListener(null);
        }
    }

    public interface ScanCallback {
        void onBleScan(BluetoothDevice device);
    }

    public String getError(int errorCode) {
        switch (errorCode) {
            case BleScanner.INIT_OK:
                return "蓝牙启动正常";
            case BleScanner.ERROR_DEVICE:
                return "硬件不支持蓝牙LE";
            case BleScanner.ERROR_SDK:
                return "手机系统版本太低";
            case BleScanner.ERROR_BLE_START:
                return "没有开启蓝牙";
            case BleScanner.ERROR_PERMISSION:
                return "没有权限";
            default:
                return "未知错误";
        }
    }

}
