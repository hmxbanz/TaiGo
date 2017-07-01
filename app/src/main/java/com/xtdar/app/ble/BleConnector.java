package com.xtdar.app.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.List;

public class BleConnector extends BluetoothGattCallback {

    private final static String TAG = BleConnector.class.getSimpleName();
    private BluetoothGatt bluetoothGatt;
    private BluetoothGattCharacteristic characteristic;
    private int lastSign;
    private int lastSignCount;

    public final static int CONNECTED = 0;
    public final static int NOTIFY = 1;
    public final static int DISCONNECTED = 2;
    private Handler handler;

    private boolean heatBeat = true;

    public BleConnector(Handler handler) {
        this.handler = handler;
    }

    public void setHeatBeat(boolean heatBeat) {
        this.heatBeat = heatBeat;
    }

    public void connect(Context context, BluetoothDevice device) {
        bluetoothGatt = device.connectGatt(context, false, this);
        boolean b = bluetoothGatt.connect();
        Log.w(TAG, "Connector connect ================== " + b);
    }

    public void disconnect() {
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
            Log.w(TAG, "Connector disconnect ================== ");
        }
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        Log.w(TAG, "Connector discoverServices ==================" + (newState == 0 ? "disconnect" : "connect"));
        switch (newState) {
            case BluetoothProfile.STATE_CONNECTED:
                bluetoothGatt.discoverServices();
                break;
            case BluetoothProfile.STATE_DISCONNECTED:
                bluetoothGatt.close();
                bluetoothGatt = null;
                Message msg = Message.obtain();
                msg.what = DISCONNECTED;
                handler.sendMessage(msg);
                break;
        }
    }

    @Override
    // New services discovered
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        Log.w(TAG, "onServicesDiscovered received ================== " + status);

        if (status == BluetoothGatt.GATT_SUCCESS) {
            Log.w(TAG, "services count ================== " + gatt.getServices().size());

            for (BluetoothGattService service : gatt.getServices()) {
                Log.w(TAG, "================== find service: " + service.getUuid().toString());
                if (service.getUuid().toString().startsWith("0000fff0-")) {
                    List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
                    for (BluetoothGattCharacteristic characteristic : characteristics) {
                        Log.w(TAG, "================== find characteristics count: " + characteristics.size());
//                            BluetoothGattCharacteristic characteristic = characteristics.get(0);
                        Log.w(TAG, "================== find characteristic: " + characteristic.getUuid().toString());
                        Log.w(TAG, "================== characteristic value: " + byte2HexStr(characteristic.getValue()));
                        gatt.setCharacteristicNotification(characteristic, true);
                        Log.w(TAG, "================== Thread : " + Thread.currentThread().getId());
                        if (characteristic.getUuid().toString().startsWith("0000fff6-")) {
                            this.characteristic = characteristic;

                            Message msg = Message.obtain();
                            msg.what = CONNECTED;
                            msg.obj = String.format(
                                    "name: %s\naddress: %s\nservice: %s\ncharacteristic: %s",
                                    gatt.getDevice().getName(),
                                    gatt.getDevice().getAddress(),
                                    service.getUuid(),
                                    characteristic.getUuid()
                            );
                            handler.sendMessage(msg);
                            return;
                        }
                    }
                }
            }
        }
    }
    @Override
    // notification
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        int sign = byte2int(characteristic.getValue());
        if (sign == 0x02403003) {//heart beat
             byte[]ECHO = {0x02, 0x40, 0x31, 0x03};
            characteristic.setValue(ECHO);
            gatt.writeCharacteristic(characteristic);
            return;
        } else if (sign == 0x02431503 && lastSignCount <= 3) {//NAK
            lastSignCount++;
            if (lastSign != 0) sendMsg(lastSign);
            return;
        } else {
            byte[] ACK = {0x02, 0x43, 0x06, 0x03};
            characteristic.setValue(ACK);
            gatt.writeCharacteristic(characteristic);
            Log.w(TAG, "write ACK ");
            lastSign = 0;
        }

        Message msg = Message.obtain();
        msg.what = NOTIFY;
        msg.obj = characteristic.getValue();
        msg.arg1 = sign;
        if (handler != null) handler.sendMessage(msg);

        Log.w(TAG, "notification from : " + Integer.toHexString(this.hashCode()) + "    " + characteristic.getUuid());
        Log.w(TAG, "notification value: " + Integer.toHexString(this.hashCode()) + "    " + byte2HexStr(characteristic.getValue()));
    }

    public static String byte2HexStr(byte[] bytes) {
        if (bytes == null) {
            return "NULL";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString().toUpperCase().trim();
    }

    public static int byte2int(byte[] bytes) {
        if (bytes == null) {
            return 0;
        }
        int result = 0;
        result |= (bytes[0] << 24);
        result |= (bytes[1] << 16);
        result |= (bytes[2] << 8);
        result |= (bytes[3]);
        return result;
    }

    public static byte[] int2byte(int sign) {
        byte[] result = new byte[4];
        result[0] = (byte) ((sign >> 24) & 0xFF);
        result[1] = (byte) ((sign >> 16) & 0xFF);
        result[2] = (byte) ((sign >> 8) & 0xFF);
        result[3] = (byte) (sign & 0xFF);
        return result;
    }

    public boolean sendMsg(int sign) {
        if (bluetoothGatt != null && characteristic != null) {
            lastSign = sign;
            lastSignCount = 1;
            characteristic.setValue(int2byte(sign));
            return bluetoothGatt.writeCharacteristic(characteristic);
        }
        return false;
    }

}
