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
import android.view.View;

import com.clj.fastble.data.ScanResult;
import com.xtdar.app.MainApplication;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.MyDevicesAdapter;
import com.xtdar.app.common.NLog;
import com.xtdar.app.common.NToast;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.server.response.MyDevicesResponse;
import com.xtdar.app.service.BluetoothService;
import com.xtdar.app.view.activity.BleActivity;
import com.xtdar.app.view.activity.CarActivity;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.activity.QrCodeActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HomeFragmentPresenter extends BasePresenter implements OnDataListener,MyDevicesAdapter.ItemClickListener,SwipeRefreshLayout.OnRefreshListener {
    private static final int GETDRIVERS = 1;
    public static final int REQUEST_CODE = 1;
    public static final int UNBINDDEVICE = 2;
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

    private String connectMac;
    private String deviceId;
    private int itemIndex;
    private MyDevicesResponse.DataBean itemSelected;


    public HomeFragmentPresenter(Context context){
        super(context);
        basePresenter = BasePresenter.getInstance(context);
        mActivity = (Main2Activity) context;
        dataAdapter = new MyDevicesAdapter(this.context);
        dataAdapter.setListItems(list);
        dataAdapter.setOnItemClickListener(this);
    }

    public void init(SwipeRefreshLayout swiper, RecyclerView recyclerView) {
        this.swiper=swiper;
        this.swiper.setOnRefreshListener(this);
        this.recyclerView=recyclerView;
        recyclerView.setAdapter(dataAdapter);
        this.recyclerView.setNestedScrollingEnabled(false);
        gridLayoutManager=new GridLayoutManager(context,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        if (mBluetoothService == null) {
            bindService();
        }
    }
    public void loadData(){
        if(basePresenter.isLogin){
        LoadDialog.show(context);
        atm.request(GETDRIVERS,this);
    }
    else
        {this.swiper.setVisibility(View.GONE);}
        }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETDRIVERS:
                return mUserAction.getDevices();
            case UNBINDDEVICE:
                return mUserAction.unBindDevice(deviceId);
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
                        dataAdapter.notifyDataSetChanged();
                        this.swiper.setVisibility(View.VISIBLE);
                        scanBLE();
                    }

                }
                NToast.longToast(context,response.getMsg());
                break;
            case UNBINDDEVICE:
                CommonResponse commonResponse = (CommonResponse) result;
                if (commonResponse.getCode() == XtdConst.SUCCESS) {
                    //解绑已连接设备动作
                    if(list.get(itemIndex).getMac_address().equals(connectMac) && mBluetoothService !=null) {
                        mBluetoothService.closeConnect();
                        connectMac="";
                    }

                    list.remove(itemIndex);
                    if (list.size() == 0) {
                        this.swiper.setVisibility(View.GONE);
                    }
                    dataAdapter.notifyDataSetChanged();
                    DialogWithYesOrNoUtils.getInstance().showDialog(context,"解绑成功",null,null,new AlertDialogCallback());

                }
                NToast.longToast(context,commonResponse.getMsg());
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
        itemSelected =item;
        String mac=item.getMac_address();

        if (mBluetoothService == null) {
            bindService();
            }
        else
            {
            if(item.getStatus()==2)//如果已连接
            {
                DialogWithYesOrNoUtils.getInstance().showDialog(context,"连接成功",null,"去玩游戏",new AlertDialogCallback(){
                    @Override
                    public void executeEvent() {
                        if(connectMac.equals("C8:FD:19:4F:BA:C9"))
                        {
                            Intent newInent=new Intent(context,CarActivity.class);
                            newInent.putExtra("ServiceId", itemSelected.getService_uuid());
                            newInent.putExtra("WriteId", itemSelected.getWrite_uuid());
                            mActivity.startActivity(newInent);
                        }
                        else
                        mActivity.getViewPager().setCurrentItem(1, false);
                    }
                });
            }
            else {
                LoadDialog.show(context);
                mBluetoothService.scanAndConnect5(mac);
            }
        }
    }

    @Override
    public void unBindBtn(int position, MyDevicesResponse.DataBean listItem) {
        itemIndex=position;
        deviceId=listItem.getBind_device_id();
        LoadDialog.show(context);
        atm.request(UNBINDDEVICE,this);
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
            mBluetoothService.scanDevice();
            LoadDialog.show(context);
            mActivity.mBluetoothService=mBluetoothService;

//            HomeFragmentPresenter.this.swiper.post(new Runnable() {
//            @Override
//            public void run() {
//                HomeFragmentPresenter.this.swiper.setRefreshing(true);
//            }
//        });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBluetoothService = null;
        }
    };

    private BluetoothService.Callback callback = new BluetoothService.Callback() {
        @Override
        public void onStartScan() {
            for(MyDevicesResponse.DataBean bean:list)
            {
                if(!bean.getMac_address().equals(connectMac))
                    bean.setStatus(0);
            }
            dataAdapter.notifyDataSetChanged();
        }

        @Override
        public void onScanning(ScanResult result) {
            mActivity.scanResultList.add(result);
            String mac=result.getDevice().getAddress();

            if(list.size()>0) {

                for (MyDevicesResponse.DataBean bean : list) {
                    NLog.e("onScanning",list.toString());
                    NLog.e("onScanning",bean.getMac_address());
                    String mac2 = bean.getMac_address();
                    if (mac2.toUpperCase().equals(mac))
                        bean.setStatus(1);

                    // NToast.shortToast(context,connectMac+"----"+mac2);
                }

                for (MyDevicesResponse.DataBean bean : list) {
                    String mac2 = bean.getMac_address();
                    if (mac2.toUpperCase().equals(connectMac))
                        bean.setStatus(2);

                }

                Collections.sort(list, Collections.<MyDevicesResponse.DataBean>reverseOrder());
                dataAdapter.notifyDataSetChanged();
            }

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
            NToast.shortToast(context, "连接失败,请确保设备已开启。");
        }

        @Override
        public void onDisConnected() {
            NToast.longToast(context, "连接断开");
            mActivity.scanResultList.clear();
        }



        @Override
        public void onServicesDiscovered() {
            LoadDialog.dismiss(context);
            connectMac =mBluetoothService.getMac();
                for(MyDevicesResponse.DataBean bean:list)
                {
                    String mac2=bean.getMac_address();
                    if(mac2.toUpperCase().equals(connectMac))
                        bean.setStatus(2);

                }
            Collections.sort(list);
            Collections.reverse(list);
            dataAdapter.notifyDataSetChanged();


            DialogWithYesOrNoUtils.getInstance().showDialog(context,"连接成功",null,"去玩游戏",new AlertDialogCallback(){
                @Override
                public void executeEvent() {
                    if(connectMac.equals("C8:FD:19:4F:BA:C9"))
                    {
                        Intent newInent=new Intent(context,CarActivity.class);
                        newInent.putExtra("ServiceId", itemSelected.getService_uuid());
                        newInent.putExtra("WriteId", itemSelected.getWrite_uuid());
                        mActivity.startActivity(newInent);
                    }
                    else
                    mActivity.getViewPager().setCurrentItem(1, false);
                }
            });

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
        mBluetoothService.setScanCallback(callback);
        mBluetoothService.scanDevice();
    }

    public void scanBLE() {
        this.swiper.post(new Runnable() {
            @Override
            public void run() {
                HomeFragmentPresenter.this.swiper.setRefreshing(true);
            }
        });
        if(mBluetoothService !=null)
        onRefresh();
    }
}