package com.xtdar.app.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

public class BleScan_18 implements IBleScan, BluetoothAdapter.LeScanCallback {
    private BleScanner.ScanCallback callback;

    public void setListener(BleScanner.ScanCallback callback) {
        this.callback = callback;
    }

    @Override
    public void startBleScan(BluetoothAdapter bluetoothAdapter) {
        bluetoothAdapter.startLeScan(this);
    }

    @Override
    public void stopBleScan(BluetoothAdapter bluetoothAdapter) {
        bluetoothAdapter.stopLeScan(this);
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        if (callback != null) {
            callback.onBleScan(device);
        }
    }
}
