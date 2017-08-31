package com.xtdar.app.presenter;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.clj.fastble.conn.BleCharacterCallback;
import com.clj.fastble.data.ScanResult;
import com.clj.fastble.exception.BleException;
import com.xtdar.app.R;
import com.xtdar.app.common.NLog;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.service.BluetoothService;
import com.xtdar.app.view.activity.UnityPlayerActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.RockerView;

import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class CarPresenter extends BasePresenter implements OnDataListener,View.OnClickListener {
    private static final String TAG = "蓝牙发送";
    //private CarActivity mActivity;
    private BluetoothService mBluetoothService;
    private String serviceId,writeId;

    public CarPresenter(Context context){
        super(context);
        //mActivity = (CarActivity) context;
    }

    public void init(RockerView rockerView1, final ImageButton btnControlTop, final ImageButton btnControlLeft, final ImageButton btnControlBottom, final ImageButton btnControlRight, Button btnTurnLeft, Button btnTurnRight, Button btnGet, Button btnLeftGo,String serviceId, String writeId) {
        LoadDialog.show(context);

        btnGet.setOnClickListener(this);
        rockerView1.setRockerChangeListener(new RockerView.RockerChangeListener() {
            @Override
            public void report(float x, float y) {
                // TODO Auto-generated method stub
                NLog.i("坐标：",x + "/" + y);
                // setLayout(rockerView2, (int)x, (int)y);
                //setLayout(rockerView1, (int)x, (int)y);
                setAction(x,y);
            }
        });

        btnControlTop.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    btnControlTop.setImageResource(R.drawable.car_control_top2);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    btnControlTop.setImageResource(R.drawable.car_control_top);
                }
                return false;
            }
        });

        btnControlBottom.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    btnControlBottom.setImageResource(R.drawable.car_control_bottom2);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    btnControlBottom.setImageResource(R.drawable.car_control_bottom);
                }
                return false;
            }
        });

        btnControlLeft.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    btnControlLeft.setImageResource(R.drawable.car_control_left2);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    btnControlLeft.setImageResource(R.drawable.car_control_left);
                }
                return false;
            }
        });

        btnControlRight.setOnTouchListener(new View.OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    //重新设置按下时的背景图片
                    btnControlRight.setImageResource(R.drawable.car_control_right2);
                }else if(event.getAction() == MotionEvent.ACTION_UP){
                    //再修改为抬起时的正常图片
                    btnControlRight.setImageResource(R.drawable.car_control_right);
                }
                return false;
            }
        });

        this.serviceId=serviceId;
        this.writeId=writeId;

        bindService();

    }


    private void bindService() {
        Intent bindIntent = new Intent(context, BluetoothService.class);
        context.bindService(bindIntent, mFhrSCon, Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        context.unbindService(mFhrSCon);
    }

    private ServiceConnection mFhrSCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBluetoothService = ((BluetoothService.BluetoothBinder) service).getService();
            //mBluetoothService.setScanCallback(callback);
            mBluetoothService.setConnectCallback(callback2);
            showData();
            LoadDialog.dismiss(context);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBluetoothService = null;
        }
    };

    private BluetoothService.Callback callback = new BluetoothService.Callback() {
        @Override
        public void onStartScan() {
        }

        @Override
        public void onScanning(ScanResult result) {
            //LoadDialog.show(UnityPlayerActivity.this);
        }

        @Override
        public void onScanComplete() {
        }

        @Override
        public void onConnecting() {
        }

        @Override
        public void onConnectFail() {
        }

        @Override
        public void onDisConnected() {
        }

        @Override
        public void onServicesDiscovered() {
//            LoadDialog.dismiss(UnityPlayerActivity.this);
//            showData();
        }
    };

    private BluetoothService.Callback2 callback2 = new BluetoothService.Callback2() {

        @Override
        public void onDisConnected() {
            //finish();
        }
    };

    public void showData() {

        BluetoothGatt gatt = mBluetoothService.getGatt();

        for (BluetoothGattService servicess : gatt.getServices()) {
            Log.w(TAG, "================== find service: " + servicess.getUuid().toString());
            if (servicess.getUuid().toString().startsWith("0000" + serviceId.toLowerCase())) {//枪发给手机
                mBluetoothService.setService(servicess);
                List<BluetoothGattCharacteristic> characteristics = servicess.getCharacteristics();
                for (BluetoothGattCharacteristic characteristic : characteristics) {
                    Log.w(TAG, "================== find characteristics count: " + characteristics.size());
//                            BluetoothGattCharacteristic characteristic = characteristics.get(0);
                    Log.w(TAG, "================== find characteristic: " + characteristic.getUuid().toString());
                    //Log.w(TAG, "================== characteristic value: " + byte2HexStr(characteristic.getValue()));
                    //gatt.setCharacteristicNotification(characteristic, true);
                    Log.w(TAG, "================== Thread : " + Thread.currentThread().getId());
                    if (characteristic.getUuid().toString().startsWith("0000" + writeId)) {
                        mBluetoothService.setCharacteristic(characteristic);
                    }
                }
            }
        }
//        final BluetoothGattCharacteristic characteristic = mBluetoothService.getCharacteristic();
    }




//命令转换
    public void setAction(float dx, float dy){
        if ((dx>-30&&dx<30) && dy <-250) {
            //发送向前命令：w
            NLog.i("坐标==：","发送向前命令：w");
        }
        else if ((dx>-30&&dx<30) && dy >250) {
            //发送向后命令：s
            NLog.i("坐标==：","发送向后命令：s");
        }
        else if (dx >250 && (dy>-30&&dy<30)) {
            //发送向右命令：d
            NLog.i("坐标==：","发送向右命令：d");
        }
        else if (dx <-250 && (dy>-30&&dy<30)) {
            //发送向右命令：a
            NLog.i("坐标==：","发送向左命令：a");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.turn_left:
                break;
            case R.id.turn_right:
                break;
            case R.id.btn_get:
                mBluetoothService.write(mBluetoothService.getService().getUuid().toString(), mBluetoothService.getCharacteristic().getUuid().toString(), "catch", new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {

                    }

                    @Override
                    public void onFailure(BleException exception) {

                    }

                    @Override
                    public void onInitiatedResult(boolean result) {

                    }
                });
                break;
            case R.id.btn_left_go:
                mBluetoothService.write(mBluetoothService.getService().getUuid().toString(), mBluetoothService.getCharacteristic().getUuid().toString(),"release", new BleCharacterCallback() {
                    @Override
                    public void onSuccess(BluetoothGattCharacteristic characteristic) {

                    }

                    @Override
                    public void onFailure(BleException exception) {

                    }

                    @Override
                    public void onInitiatedResult(boolean result) {

                    }
                });
                break;
        }
    }
}