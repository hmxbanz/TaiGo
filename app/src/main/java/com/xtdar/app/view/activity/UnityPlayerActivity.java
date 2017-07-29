package com.xtdar.app.view.activity;

        import com.clj.fastble.conn.BleCharacterCallback;
        import com.clj.fastble.data.ScanResult;
        import com.clj.fastble.exception.BleException;
        import com.clj.fastble.utils.HexUtil;
        import com.unity3d.player.*;
        import com.xtdar.app.ble.BleConnector;
        import com.xtdar.app.common.NLog;
        import com.xtdar.app.common.NToast;
        import com.xtdar.app.common.NumberUtils;
        import com.xtdar.app.service.BluetoothService;
        import com.xtdar.app.view.widget.LoadDialog;

        import android.app.Activity;
        import android.bluetooth.BluetoothDevice;
        import android.bluetooth.BluetoothGatt;
        import android.bluetooth.BluetoothGattCharacteristic;
        import android.bluetooth.BluetoothGattService;
        import android.content.ComponentName;
        import android.content.Context;
        import android.content.Intent;
        import android.content.ServiceConnection;
        import android.content.res.Configuration;
        import android.graphics.PixelFormat;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.IBinder;
        import android.os.Looper;
        import android.os.Message;
        import android.os.Parcelable;
        import android.util.Log;
        import android.view.KeyEvent;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.Window;
        import android.view.WindowManager;

        import java.util.List;

public class UnityPlayerActivity extends Activity
{
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
    private BluetoothDevice device;
    private BleConnector connector;

    private String TAG=UnityPlayerActivity.class.getSimpleName();


    private Handler handle = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BleConnector.CONNECTED:
                    break;
                case BleConnector.NOTIFY:
                    //UnityPlayer.UnitySendMessage("Main Camera","eee",String.valueOf(msg.obj));
                    break;
                case BleConnector.DISCONNECTED:
                    break;
            }
        }
    };
    private BluetoothService mBluetoothService;
    private String isExisted="0";
    private int firstTime=0;

    // Setup activity layout
    @Override protected void onCreate (Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

        mUnityPlayer = new UnityPlayer(this);
        setContentView(mUnityPlayer);
        mUnityPlayer.requestFocus();
        //device = getIntent().getParcelableExtra("device");
//        connector = new BleConnector(handle);
//        connector.connect(this, device);

        //firstTime=1;
        UnityPlayer.UnitySendMessage("Main Camera","ChooseGame","");     //第二次进入调用

        bindService();

    }

    public BluetoothService getBluetoothService() {
        return mBluetoothService;
    }

    private void bindService() {
        Intent bindIntent = new Intent(this, BluetoothService.class);
        this.bindService(bindIntent, mFhrSCon, Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        this.unbindService(mFhrSCon);
    }

    private ServiceConnection mFhrSCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBluetoothService = ((BluetoothService.BluetoothBinder) service).getService();
            mBluetoothService.setScanCallback(callback);
            mBluetoothService.setConnectCallback(callback2);
            showData();
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
            LoadDialog.show(UnityPlayerActivity.this);
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
            LoadDialog.dismiss(UnityPlayerActivity.this);
            showData();
        }
    };

    private BluetoothService.Callback2 callback2 = new BluetoothService.Callback2() {

        @Override
        public void onDisConnected() {
            finish();
        }
    };


    public void showData() {
        BluetoothGatt gatt = mBluetoothService.getGatt();
//
//        for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
//            mResultAdapter.addResult(characteristic);
//        }

        for (BluetoothGattService servicess : gatt.getServices()) {
            Log.w(TAG, "================== find service: " + servicess.getUuid().toString());
            //if (service.getUuid().toString().startsWith("0000ffe5-")) {//手机发给枪
            if (servicess.getUuid().toString().startsWith("0000ae00-")) {//枪发给手机
                mBluetoothService.setService(servicess);
                List<BluetoothGattCharacteristic> characteristics = servicess.getCharacteristics();
                for (BluetoothGattCharacteristic characteristic : characteristics) {
                    Log.w(TAG, "================== find characteristics count: " + characteristics.size());
//                            BluetoothGattCharacteristic characteristic = characteristics.get(0);
                    Log.w(TAG, "================== find characteristic: " + characteristic.getUuid().toString());
                    //Log.w(TAG, "================== characteristic value: " + byte2HexStr(characteristic.getValue()));
                    //gatt.setCharacteristicNotification(characteristic, true);
                    Log.w(TAG, "================== Thread : " + Thread.currentThread().getId());
                    if (characteristic.getUuid().toString().startsWith("0000ae02-")) {
                        mBluetoothService.setCharacteristic(characteristic);

                    }
                }
            }
        }
        final BluetoothGattCharacteristic characteristic = mBluetoothService.getCharacteristic();
        mBluetoothService.notify(
                characteristic.getService().getUuid().toString(),
                characteristic.getUuid().toString(),
                new BleCharacterCallback() {

                    @Override
                    public void onSuccess(final BluetoothGattCharacteristic characteristic) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s=String.valueOf(HexUtil.encodeHex(characteristic.getValue()));
                               //String s10=NumberUtils.print10(s);
                                //NLog.e("BLEBLE",s+"-----");
                                //UnityPlayer.UnitySendMessage("Main Camera","eee",s10);

                                // For all other profiles, writes the data formatted in HEX.对于所有的文件，写入十六进制格式的文件
                                //这里读取到数据
                                final byte[] data = characteristic.getValue();
                                for (int i = 0; i < data.length; i++) {
                                    System.out.println("BLEBLE---data......" + (int)data[i]);
                                }
                                if (data != null && data.length > 0) {
                                    final StringBuilder stringBuilder = new StringBuilder(data.length);
                                    for(byte byteChar : data)
                                        //以十六进制的形式输出
                                        stringBuilder.append(String.format("%02x ", byteChar));
                                    // intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());
                                    //intent.putExtra(EXTRA_DATA, new String(data));
                                    NLog.e("BLEBLE",stringBuilder.toString());
                                }
                                //Integer.toHexString(10)
                                byte command=data[6];
                                int aaaaa=command & 0x01;
                                NLog.e("BLEBLE",aaaaa);

                                if((int)command==1)
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","a");
                                else if ((int)command==2)
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","b");
                                else if ((int)command==3)
                                {
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","a");
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","b");
                                }
                                else if ((int)command==4)
                                {
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","e");
                                }
                                else if ((int)command==8)
                                {
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","c");
                                }
                                else if ((int)command==10)
                                {
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","h");
                                }
                                else if ((int)command==20)
                                {
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","d");
                                }


                            }
                        });
                    }

                    @Override
                    public void onFailure(final BleException exception) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        });
                    }

                    @Override
                    public void onInitiatedResult(boolean result) {

                    }

                });

    }



//    *
//            * @param bytes
//* @return 将二进制转换为十六进制字符输出
private static String hexStr = "0123456789ABCDEF"; //全局
    public static String BinaryToHexString(byte[] bytes){
        String result = "";
        String hex = "";
        for(int i=0;i<bytes.length;i++){
            //字节高4位
   hex = String.valueOf(hexStr.charAt((bytes[i]&0xF0)>>4));
            //字节低4位
   hex += String.valueOf(hexStr.charAt(bytes[i]&0x0F));
                    result +=hex;
        }
        return result;
    }

    @Override protected void onNewIntent(Intent intent)
    {
        // To support deep linking, we need to make sure that the client can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
        setIntent(intent);
    }

    // Quit Unity
    @Override protected void onDestroy ()
    {
        mUnityPlayer.quit();
        super.onDestroy();

        if (mBluetoothService != null)
            unbindService();
    }

    // Pause Unity
    @Override protected void onPause()
    {
        super.onPause();
        mUnityPlayer.pause();
    }

    // Resume Unity
    @Override protected void onResume()
    {
        super.onResume();
        mUnityPlayer.resume();


    }

    // Low Memory Unity
    @Override public void onLowMemory()
    {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL)
        {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override public boolean onKeyUp(int keyCode, KeyEvent event)     { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onKeyDown(int keyCode, KeyEvent event)   { return mUnityPlayer.injectEvent(event); }
    @Override public boolean onTouchEvent(MotionEvent event)          { return mUnityPlayer.injectEvent(event); }
    /*API12*/ public boolean onGenericMotionEvent(MotionEvent event)  { return mUnityPlayer.injectEvent(event); }
    public void Move(String s){
        //connector.sendMsg(s);
    }//接收Unity发来

//    public void eee(String s){
//        connector.sendMsg(s);
//    }//发送给Unity
    //UnityPlayer.UnitySendMessage("Main Camera","eee",s10);


    public void unityGetEnterOrExit()  {
        UnityPlayer.UnitySendMessage("Main Camera","SendEnterOrExit",isExisted+","+"0");     //设置进入或退出
    }
    //获取进入退出判断
    public void unitySetEnterOrExit(String _value) {
        isExisted=_value;
    }     //设置进入退出判断
    public void exitUnity()    {
        finish ();
    }  //退出unity视图

    public void UnitySceneReady(){
        UnityPlayer.UnitySendMessage("Main Camera","playGame","1");     //进入游戏
    }  //准备好了

}