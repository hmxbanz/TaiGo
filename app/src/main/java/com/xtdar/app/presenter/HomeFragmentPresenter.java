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

import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.ClassListAnimationAdapter;
import com.xtdar.app.adapter.MyDevicesAdapter;
import com.xtdar.app.common.NToast;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.ClassListResponse;
import com.xtdar.app.server.response.MyDevicesResponse;
import com.xtdar.app.service.DeviceConectService;
import com.xtdar.app.view.activity.BleActivity;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.QrCodeActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;

import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HomeFragmentPresenter extends BasePresenter implements OnDataListener,MyDevicesAdapter.ItemClickListener {
    private static final int GETDRIVERS = 1;
    public static final int REQUEST_CODE = 1;
    private final BasePresenter basePresenter;
    private List<MyDevicesResponse.DataBean> list;
    private RecyclerView recyclerView;
    private MyDevicesAdapter dataAdapter;
    private GridLayoutManager gridLayoutManager;
    private boolean isAdapterSetted=false;
    private DeviceConectService service;
    private DeviceConectService.ConectBinder mbinder;

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
        LoadDialog.show(context);
        atm.request(GETDRIVERS,this);
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
                     list = response.getData();
                    if (list.size() == 0) {
                        this.recyclerView.setVisibility(View.GONE);
                    }
                    else {
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
                    }
                }
                NToast.longToast(context,response.getMsg());
                break;
        }
    }

    public void onScanClick() {
        if(basePresenter.isLogin)
            context.startActivity(new Intent(context, BleActivity.class));
        else
        {
            DialogWithYesOrNoUtils.getInstance().showDialog(context, "请先登录", "前住登录", new AlertDialogCallback() {
                @Override
                public void executeEvent() {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

            });
        }

    }

    public void onQrClick() {
        if(basePresenter.isLogin) {
            Intent intent = new Intent(context, QrCodeActivity.class);
            ((Activity)context).startActivityForResult(intent, REQUEST_CODE);
        }
        else
        {
            DialogWithYesOrNoUtils.getInstance().showDialog(context, "请先登录", "前住登录", new AlertDialogCallback() {
                @Override
                public void executeEvent() {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

            });
        }
    }

    @Override
    public void onItemClick(int position, String itemId, String deviceName) {
        Intent startConectServiceIntent = new Intent(context, DeviceConectService.class);
        startConectServiceIntent.putExtra("deviceName", deviceName);
        context.startService(startConectServiceIntent);
    }


    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder binder) {
            service = ((DeviceConectService.ConectBinder) binder).getService();
            mbinder=(DeviceConectService.ConectBinder) binder;
//            if (!service.initialize()) {
//                //Log.e(TAG, "Unable to initialize Bluetooth");
//            }
//            // Automatically connects to the device upon successful start-up
//            // initialization.
//            service.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            service = null;
        }
    };
}