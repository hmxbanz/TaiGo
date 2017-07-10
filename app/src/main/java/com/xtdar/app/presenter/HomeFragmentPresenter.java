package com.xtdar.app.presenter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.clj.fastble.data.ScanResult;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.ClassListAnimationAdapter;
import com.xtdar.app.adapter.MyDevicesAdapter;
import com.xtdar.app.common.NToast;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.ClassListResponse;
import com.xtdar.app.server.response.MyDevicesResponse;
import com.xtdar.app.service.BluetoothService;
import com.xtdar.app.service.DeviceConectService;
import com.xtdar.app.view.activity.BleActivity;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.QrCodeActivity;
import com.xtdar.app.view.activity.UnityPlayerActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HomeFragmentPresenter extends BasePresenter implements OnDataListener,MyDevicesAdapter.ItemClickListener {
    private static final int GETDRIVERS = 1;
    public static final int REQUEST_CODE = 1;
    private final BasePresenter basePresenter;
    private List<MyDevicesResponse.DataBean> list=new ArrayList<>();
    private RecyclerView recyclerView;
    private MyDevicesAdapter dataAdapter;
    private GridLayoutManager gridLayoutManager;
    private boolean isAdapterSetted=false;

    private BluetoothService mBluetoothService;
    private String deviceName;

    //private ContactsActivity mActivity;
    public HomeFragmentPresenter(Context context){
        super(context);
        basePresenter = BasePresenter.getInstance(context);
        //mActivity = (ContactsActivity) context;
        dataAdapter = new MyDevicesAdapter(this.context);
    }

    public void init(RecyclerView recyclerView) {
        this.recyclerView=recyclerView;
        gridLayoutManager=new GridLayoutManager(context,1);
        recyclerView.setLayoutManager(gridLayoutManager);

    }
public void loadData(){
    if(basePresenter.isLogin){
        LoadDialog.show(context);
        atm.request(GETDRIVERS,this);
    }
}
    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETDRIVERS:
                return mUserAction.getDevices();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case GETDRIVERS:
                MyDevicesResponse response = (MyDevicesResponse) result;
                if (response.getCode() == XtdConst.SUCCESS) {
                    if (response.getData().size() == 0) {

                    }
                    else {
                        list.clear();
                        list.addAll(response.getData());
                        //设置列表
                        //dataAdapter.setHeaderView(LayoutInflater.from(context).inflate(R.layout.recyclerview_header,null));
                        dataAdapter.setListItems(list);
                        dataAdapter.setOnItemClickListener(this);
                        //dataAdapter.setFooterView(LayoutInflater.from(context).inflate(R.layout.recyclerview_footer,null));

                        if(!isAdapterSetted)
                            recyclerView.setAdapter(dataAdapter);
                        isAdapterSetted=true;
                        dataAdapter.notifyDataSetChanged();
                        this.recyclerView.setVisibility(View.VISIBLE);
                    }
                }
                NToast.longToast(context,response.getMsg());
                break;
        }
    }

    public void onScanClick() {
            context.startActivity(new Intent(context, BleActivity.class));
    }

    public void onQrClick() {
            Intent intent = new Intent(context, QrCodeActivity.class);
            ((Activity)context).startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onItemClick(int position, String itemId, String deviceName) {
        //Intent startConectServiceIntent = new Intent(context, DeviceConectService.class);
        //startConectServiceIntent.putExtra("deviceName", deviceName);
        //context.startService(startConectServiceIntent);
        this.deviceName=deviceName;
        if (mBluetoothService == null) {
            bindService();
        } else {
            mBluetoothService.scanDevice();
        }
    }


    public void bindService() {
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
        }

        @Override
        public void onScanning(ScanResult result) {
            LoadDialog.show(context);
            if(HomeFragmentPresenter.this.deviceName.equals(result.getDevice().getName()))
            {
                LoadDialog.dismiss(context);
                mBluetoothService.connectDevice(result);
            }
            NToast.shortToast(context,result.getDevice().getName());
        }

        @Override
        public void onScanComplete() {
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
            Intent intent = new Intent(context, UnityPlayerActivity.class);
            context.startActivity(intent);

        }
    };

}