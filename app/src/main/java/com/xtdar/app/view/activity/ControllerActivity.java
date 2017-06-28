package com.xtdar.app.view.activity;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.xtdar.app.R;
import com.xtdar.app.ble.BleConnector;
import com.xtdar.app.ble.SignTranslator;

public class ControllerActivity extends Activity implements CompoundButton.OnCheckedChangeListener {
    private ScrollView scroll;
    private TextView deviceInfo, txtLog;
    private CompoundButton btnConnect, btn_heartBeat;
    private StringBuilder log = new StringBuilder();

    private BluetoothDevice device;
    private BleConnector connector;

    private Handler handle = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BleConnector.CONNECTED:
                    btnConnect.setChecked(true);
                    deviceInfo.setText(msg.obj.toString());
                    break;
                case BleConnector.NOTIFY:
                    readMsg(msg.arg1);
                    break;
                case BleConnector.DISCONNECTED:
                    btnConnect.setChecked(false);
                    showDeviceInfo();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_controller);
        scroll = (ScrollView) findViewById(R.id.scroll);
        txtLog = (TextView) findViewById(R.id.txt_log);
        deviceInfo = (TextView) findViewById(R.id.deviceInfo);

        btnConnect = (CompoundButton) findViewById(R.id.btn_connect);
        btnConnect.setOnCheckedChangeListener(this);

        device = getIntent().getParcelableExtra("device");

        btn_heartBeat = (ToggleButton) findViewById(R.id.heartBeat);
        btn_heartBeat.setOnCheckedChangeListener(this);

        showDeviceInfo();
        connector = new BleConnector(handle);
    }

    //显示远程蓝牙LE的简要信息
    public void showDeviceInfo() {
        String text = String.format("设备名: %s\nMAC地址: %s\n",
                device.getName(),
                device.getAddress());
        deviceInfo.setText(text);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.btn_connect:
                if (isChecked) {
                    btn_heartBeat.setChecked(true);

                    connector.connect(this, device);
                } else {
                    connector.disconnect();
                }
                break;
            case R.id.heartBeat:
                connector.setHeatBeat(isChecked);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connector != null) {
            connector.disconnect();
        }
    }

    // 清屏
    public void clear(View v) {
        log = null;
        txtLog.setText("");
    }

    // 激光击中查询
    public void getShot(View v) {
        sendMsg(SignTranslator.GetShutCount);
    }

    // 超声波击中查询
    public void getBlowUp(View v) {
        sendMsg(SignTranslator.GetBlowCount);
    }

    // 信号弹状态查询
    public void getSmokeBomb(View v) {
        sendMsg(SignTranslator.GetSmoke);
    }

    // 生命状态查询
    public void getLife(View v) {
        sendMsg(SignTranslator.GetLife);
    }

    // 生命设置死亡
    public void setDie(View v) {
        //与什么上报相同
        if (connector.sendMsg(SignTranslator.SetDie)) {
            String translate = "生命状态设置: 设置阵亡";

            System.out.println("sendMsg ------------------------- " + translate);
            showText(translate);
        }
    }

    // 生命设置重生
    public void SetRebirth(View v) {
        sendMsg(SignTranslator.SetRebirth);
    }

    // 读取信号
    private void readMsg(int sign) {
        String translate = SignTranslator.getRead(sign);

        System.out.println("readMsg ------------------------- " + translate);
        showText(translate);
    }

    //发送信号
    private void sendMsg(int sign) {
        if (connector.sendMsg(sign)) {
            String translate = SignTranslator.getSend(sign);

            System.out.println("sendMsg ------------------------- " + translate);
            showText(translate);
        }
    }

    private void showText(String msg) {
        log.append(msg).append('\n');
        txtLog.setText(log);
        scroll.smoothScrollTo(0, scroll.getChildAt(0).getHeight() - scroll.getHeight());
    }

}
