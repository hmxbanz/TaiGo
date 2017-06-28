package com.xtdar.app.ble;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;

@TargetApi(21)
public class BleScan_21 extends ScanCallback implements IBleScan {
    private BleScanner.ScanCallback callback;

    @Override
    public void setListener(BleScanner.ScanCallback callback) {
        this.callback = callback;
    }

    @Override
    public void startBleScan(BluetoothAdapter bluetoothAdapter) {
        bluetoothAdapter.getBluetoothLeScanner().startScan(this);

    }

    @Override
    public void stopBleScan(BluetoothAdapter bluetoothAdapter) {
        bluetoothAdapter.getBluetoothLeScanner().stopScan(this);
    }

    @Override
    public void onScanResult(int callbackType, ScanResult result) {
        if (callback != null) {
            callback.onBleScan(result.getDevice());
        }
    }
}
