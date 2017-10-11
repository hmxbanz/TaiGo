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

    private String TAG=UnityPlayerActivity.class.getSimpleName();
    private BluetoothService mBluetoothService;
    private String isExisted="0";
    private int firstTime=0;
    private String ServiceId,ReadId,WriteId,isHigh,gameId;
    private BluetoothGattCharacteristic writecharacteristic;

    // Setup activity layout
    @Override protected void onCreate (Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy

        mUnityPlayer = new UnityPlayerForMe(this);

        setContentView(mUnityPlayer);
        mUnityPlayer.requestFocus();
        Intent intent = getIntent();
        ServiceId=intent.getStringExtra("ServiceId");
        ReadId=intent.getStringExtra("ReadId");
        WriteId=intent.getStringExtra("WriteId");
        isHigh=intent.getStringExtra("isHigh");
        gameId=intent.getStringExtra("gameId");
        NLog.d("id:", gameId);
        //firstTime=1;
        //UnityPlayer.UnitySendMessage("Main Camera","ChooseGame","");     //第二次进入调用
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService();
    }

    private void bindService() {
        Intent bindIntent = new Intent(this, BluetoothService.class);
        this.bindService(bindIntent, mFhrSCon, Context.BIND_AUTO_CREATE);
    }

    private void unbindService() {
        if(mFhrSCon!=null) {
            unbindService(mFhrSCon);
            mFhrSCon=null;
        }
    }

    private ServiceConnection mFhrSCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBluetoothService = ((BluetoothService.BluetoothBinder) service).getService();
            //mBluetoothService.setScanCallback(callback);
            mBluetoothService.setConnectCallback(callback2);
            showData();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBluetoothService = null;
        }
    };



    private BluetoothService.Callback2 callback2 = new BluetoothService.Callback2() {

        @Override
        public void onDisConnected() {
            NToast.longToast(UnityPlayerActivity.this,"蓝牙已断开，请重新进入游戏！");
            NToast.longToast(UnityPlayerActivity.this,"蓝牙已断开，请重新进入游戏！");
            NToast.longToast(UnityPlayerActivity.this,"蓝牙已断开，请重新进入游戏！");
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
            if (servicess.getUuid().toString().startsWith("0000"+ServiceId)) {//枪发给手机
                mBluetoothService.setService(servicess);
                List<BluetoothGattCharacteristic> characteristics = servicess.getCharacteristics();
                for (BluetoothGattCharacteristic characteristic : characteristics) {

                    if (characteristic.getUuid().toString().startsWith("0000"+WriteId)) {
                        writecharacteristic=characteristic;

                    }

                    Log.w(TAG, "================== find characteristics count: " + characteristics.size());
//                            BluetoothGattCharacteristic characteristic = characteristics.get(0);
                    Log.w(TAG, "================== find characteristic: " + characteristic.getUuid().toString());
                    //Log.w(TAG, "================== characteristic value: " + byte2HexStr(characteristic.getValue()));
                    //gatt.setCharacteristicNotification(characteristic, true);
                    Log.w(TAG, "================== Thread : " + Thread.currentThread().getId());
                    if (characteristic.getUuid().toString().startsWith("0000"+ReadId)) {
                        mBluetoothService.setCharacteristic(characteristic);

                    }
                }
            }






        }
        final BluetoothGattCharacteristic characteristic = mBluetoothService.getCharacteristic();
        mBluetoothService.notify(  characteristic.getService().getUuid().toString(), characteristic.getUuid().toString(), new BleCharacterCallback() {

                    @Override
                    public void onSuccess(final BluetoothGattCharacteristic characteristic) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String s=String.valueOf(HexUtil.encodeHex(characteristic.getValue()));
                               //String s10=NumberUtils.print10(s);
                                //NLog.d("BLEBLE",s+"-----");
                                //UnityPlayer.UnitySendMessage("Main Camera","eee",s10);

                                // For all other profiles, writes the data formatted in HEX.对于所有的文件，写入十六进制格式的文件
                                //这里读取到数据
                                final byte[] data = characteristic.getValue();
                                for (int i = 0; i < data.length; i++) {
                                    //System.out.println("BLEBLE---原始byte" + (int)data[i]);
                                }
                                if (data != null && data.length > 0) {
                                    final StringBuilder stringBuilder = new StringBuilder(data.length);
                                    for(byte byteChar : data)
                                        //以十六进制的形式输出
                                        stringBuilder.append(String.format("%02x ", byteChar));
                                    // intent.putExtra(EXTRA_DATA, new String(data) + "\n" + stringBuilder.toString());
                                    //intent.putExtra(EXTRA_DATA, new String(data));
                                    NLog.d("BLEBLE---转换成16进制",stringBuilder.toString());
                                }
                                //Integer.toHexString(10)
                                byte command=data[6];
//                                int aaaaa=command & 0x01;
//                                NLog.d("BLEBLE",aaaaa);


                                NLog.d("BLEBLE---未知",s);
                                String x = s.substring(14, 18);
                                String y = s.substring(18, 22);

                                //NLog.d("BLEBLE>>>>",x+"-"+y);

                                int xx=Integer.parseInt(x,16);
                                int yy=Integer.parseInt(y,16);

                                //NLog.d("xxxx+yyyy:",x+"-"+y);

                                //NLog.d("xxxxyyyy:",xx+"-"+yy);

                                if((xx>460 && xx<530) && (yy>460 && yy<530))
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","op");
                                else
                                    UnityPlayer.UnitySendMessage("Main Camera","eee",xx+","+yy);
                                //if(!"0000".equals(x) && !"0000".equals(y)){
                                    //UnityPlayer.UnitySendMessage("Main Camera","eee",xx+","+yy);
                                //}

                                if((int)command==1) {
                                    UnityPlayer.UnitySendMessage("Main Camera", "eee", "a");//射击
                                }
                                if((int)command==0)
                                {UnityPlayer.UnitySendMessage("Main Camera", "eee", "j");}//松开


                                if ((int)command==2)
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","b");//上弹
                                else if ((int)command==3)
                                {
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","a");
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","b");
                                }
                                else if ((int)command==4)
                                {
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","e");//换枪
                                }
                                else if ((int)command==8)
                                {
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","c");//补血
                                }
                                else if ((int)command==10)
                                {
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","h");//换枪
                                }
                                else if ((int)command==20)
                                {
                                    UnityPlayer.UnitySendMessage("Main Camera","eee","d");//无用
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
//    * @param bytes
//    * @return 将二进制转换为十六进制字符输出
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

            unbindService();
    }

    // Pause Unity
    @Override protected void onPause()
    {
        super.onPause();
        mUnityPlayer.pause();

        startActivity(new Intent(this,Main2Activity.class));

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
        String serviceUuid=mBluetoothService.getService().getUuid().toString();
        String writeUuid=writecharacteristic.getUuid().toString();
        mBluetoothService.write(serviceUuid, writeUuid, s, new BleCharacterCallback() {
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
    }//接收Unity发来

//    public void eee(String s){
//        connector.sendMsg(s);
//    }//发送给Unity
    //UnityPlayer.UnitySendMessage("Main Camera","eee",s10);
    public void unityGetEnterOrExit()  {
        UnityPlayer.UnitySendMessage("Main Camera","SendEnterOrExit",isExisted+","+isHigh);     //设置进入或退出
    }
    //获取进入退出判断
    public void unitySetEnterOrExit(String _value) {
        isExisted=_value;
    }     //设置进入退出判断
    public void exitUnity()    {
        finish();
    }  //退出unity视图

    public void UnitySceneReady(){
        UnityPlayer.UnitySendMessage("Main Camera","playGame",gameId);     //进入游戏
    }
    //正在加载的游戏
    public void unityGameLoadingList (String gameId){

    }

    //进度
    public void unitySendLoadValue(float _value,String _gameID){
        NLog.d("收到进度数："+_value);
    }


    //进度2
    public void unityStartDownload(){
        NLog.d("收到进度数：");
    }


}