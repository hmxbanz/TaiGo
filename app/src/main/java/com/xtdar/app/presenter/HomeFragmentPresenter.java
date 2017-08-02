package com.xtdar.app.presenter;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.activity.QrCodeActivity;
import com.xtdar.app.view.activity.UnityPlayerActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HomeFragmentPresenter extends BasePresenter implements OnDataListener,MyDevicesAdapter.ItemClickListener,SwipeRefreshLayout.OnRefreshListener {
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

    private Main2Activity mActivity;
    private SwipeRefreshLayout swiper;

    public HomeFragmentPresenter(Context context){
        super(context);
        basePresenter = BasePresenter.getInstance(context);
        mActivity = (Main2Activity) context;
        dataAdapter = new MyDevicesAdapter(this.context);
    }

    public void init(SwipeRefreshLayout swiper, RecyclerView recyclerView) {
        this.swiper=swiper;
        this.swiper.setOnRefreshListener(this);
        this.recyclerView=recyclerView;
        this.recyclerView.setNestedScrollingEnabled(false);
        gridLayoutManager=new GridLayoutManager(context,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        if (mBluetoothService == null) {
            bindService();
        } else {
            LoadDialog.show(context);
        }
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
                        this.swiper.setVisibility(View.VISIBLE);
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
    public void onItemClick(int position, MyDevicesResponse.DataBean item) {
        //Intent startConectServiceIntent = new Intent(context, DeviceConectService.class);
        //startConectServiceIntent.putExtra("deviceName", deviceName);
        //context.startService(startConectServiceIntent);
        String mac=item.getMac_address();

        if (mBluetoothService == null) {
            bindService();
        } else {
            LoadDialog.show(context);
            mBluetoothService.scanAndConnect5(mac);
        }
    }

    public void bindService() {
        Intent bindIntent = new Intent(context, BluetoothService.class);
        context.bindService(bindIntent, mFhrSCon, Context.BIND_AUTO_CREATE);
    }

    public void unbindService() {
        //context.unbindService(mFhrSCon);
    }

    private ServiceConnection mFhrSCon = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBluetoothService = ((BluetoothService.BluetoothBinder) service).getService();
            mBluetoothService.setScanCallback(callback);
            LoadDialog.show(context);
            //mBluetoothService.scanAndConnect5(HomeFragmentPresenter.this.deviceName);

            //mBluetoothService.scanDevice();
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
            //LoadDialog.show(context);
            String mac=result.getDevice().getAddress();

            for(MyDevicesResponse.DataBean bean:list)
            {
                bean.setStatus(0);
                String mac2=bean.getMac_address();
                if(mac2.toUpperCase().equals(mac))
                    bean.setStatus(1);

                NToast.shortToast(context,mac+"----"+mac2);
            }
            Collections.sort(list);
            Collections.reverse(list);
            dataAdapter.notifyDataSetChanged();


        }

        @Override
        public void onScanComplete() {
            HomeFragmentPresenter.this.swiper.setRefreshing(false);
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
            String mac=mBluetoothService.getMac();
                for(MyDevicesResponse.DataBean bean:list)
                {
                    String mac2=bean.getMac_address();
                    if(mac2.toUpperCase().equals(mac))
                        bean.setStatus(2);

                }
            Collections.sort(list);
            Collections.reverse(list);
            dataAdapter.notifyDataSetChanged();
//
//            LoadDialog.dismiss(context);
//            NToast.longToast(context, "连接成功，请选择游戏开始玩。");
//            ((Main2Activity)context).getViewPager().setCurrentItem(1, false);
//            Intent intent = new Intent(context, UnityPlayerActivity.class);
//            context.startActivity(intent);

        }
    };

    @Override
    public void onRefresh() {
        mBluetoothService.scanDevice();
    }

}