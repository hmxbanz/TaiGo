package com.xtdar.app.ble;

import android.bluetooth.BluetoothAdapter;

public interface IBleScan {
    void setListener(BleScanner.ScanCallback callback);

    void startBleScan(BluetoothAdapter bluetoothAdapter);

    void stopBleScan(BluetoothAdapter bluetoothAdapter);
}
