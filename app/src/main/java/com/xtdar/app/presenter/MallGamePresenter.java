package com.xtdar.app.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.clj.fastble.data.ScanResult;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.ClassListAnimationAdapter;
import com.xtdar.app.common.NToast;
import com.xtdar.app.listener.EndlessRecyclerOnScrollListener;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.AdResponse;
import com.xtdar.app.server.response.ClassListResponse;
import com.xtdar.app.server.response.GameCheckResponse;
import com.xtdar.app.server.response.GameListResponse;
import com.xtdar.app.service.BluetoothService;
import com.xtdar.app.view.activity.DetailActivity;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.activity.UnityPlayerActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class MallGamePresenter extends BasePresenter implements  SwipeRefreshLayout.OnRefreshListener,OnDataListener,ClassListAnimationAdapter.ItemClickListener {
    private static final int GETANIMATION = 2;
    private static final int GAMECHECK = 3;
    private Banner Banner;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ClassListAnimationAdapter dataAdapter;
    private List<GameListResponse.DataBean> list=new ArrayList<>();
    private String lastItem ="0";
    private int lastOffset;
    private int lastPosition;
    private boolean isAdapterSetted=false;
    private BluetoothService mBluetoothService;
    private String deviceName;
    private Main2Activity mActivity;
    private String gameName;

    private Intent toUnityPlayerActivityInent;
    private SwipeRefreshLayout swiper;

    public MallGamePresenter(Context context){
        super(context);
        mActivity = (Main2Activity) context;
        mBluetoothService=mActivity.mBluetoothService;
        dataAdapter = new ClassListAnimationAdapter(this.context);
    }

    public void init(SwipeRefreshLayout swiper, RecyclerView recycleView) {
        this.swiper=swiper;
        this.recyclerView =recycleView;
        this.swiper.setOnRefreshListener(this);
        gridLayoutManager=new GridLayoutManager(context,1);
        recycleView.setLayoutManager(gridLayoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                LoadDialog.show(context);
                atm.request(GETANIMATION,MallGamePresenter.this);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.getLayoutManager() != null) {
                    getPositionAndOffset();
                }
            }
        });
        recyclerView.setNestedScrollingEnabled(false);

        LoadDialog.show(context);
        atm.request(GETANIMATION,this);


    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETANIMATION:
                return mUserAction.getShot("0",lastItem,"4");
            case GAMECHECK:
                return mUserAction.gameCheck(gameName);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        this.swiper.setRefreshing(false);
        switch (requestCode) {
            case GETANIMATION:
                GameListResponse response = (GameListResponse) result;
                if (response.getCode() == XtdConst.SUCCESS) {
                    final List<GameListResponse.DataBean> datas = response.getData();
                    lastItem=datas.get(datas.size()-1).getGame_id();
                    list.addAll(response.getData());
                    //设置列表
                    //dataAdapter.setHeaderView(LayoutInflater.from(context).inflate(R.layout.recyclerview_header,null));
                    dataAdapter.setListItems(list);
                    dataAdapter.setOnItemClickListener(this);

                    if(!isAdapterSetted)
                        recyclerView.setAdapter(dataAdapter);
                    isAdapterSetted=true;
                    dataAdapter.notifyDataSetChanged();


                }
                break;
            case GAMECHECK :
                    //取得设备信息，做后续工作
                    //通过intent 信息
                GameCheckResponse gameCheckResponse = (GameCheckResponse) result;
                if (gameCheckResponse.getCode() == XtdConst.SUCCESS) {
                    toUnityPlayerActivityInent = new Intent(context, UnityPlayerActivity.class);
                    toUnityPlayerActivityInent.putExtra("ServiceId","ae00");
                    toUnityPlayerActivityInent.putExtra("ReadId","ae02");
                    toUnityPlayerActivityInent.putExtra("WriteId","ae01");
                    toUnityPlayerActivityInent.putExtra("isHigh","0");

                    if (mBluetoothService == null) {
                        bindService();
                    }
                }


                    //mBluetoothService.scanDevice();
                    // LoadDialog.show(context);
                    //mBluetoothService.scanAndConnect5(HomeFragmentPresenter.this.deviceName);

                break;
        }
    }


    @Override
    public void onItemClick(int position, String itemId,String gameName) {
        //DetailActivity.StartActivity(context,item_id,class_id);
        //context.startActivity(new Intent(context, UnityPlayerActivity.class));
        this.gameName=gameName;
        //传游戏名查询
        LoadDialog.show(context);
        atm.request(GAMECHECK,this);

    }

    /**
     * 记录RecyclerView当前位置
     */
    private void getPositionAndOffset() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //获取可视的第一个view
        View topView = layoutManager.getChildAt(0);
        if(topView != null) {
            //获取与该view的顶部的偏移量
            lastOffset = topView.getTop();
            //得到该View的数组位置
            lastPosition = layoutManager.getPosition(topView);
        }
    }

    /**
     * 让RecyclerView滚动到指定位置
     */
    private void scrollToPosition() {
        if(recyclerView.getLayoutManager() != null && lastPosition >= 0) {
            ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPositionWithOffset(lastPosition, lastOffset);
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
            mBluetoothService.scanAndConnect5(MallGamePresenter.this.deviceName);

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

            context.startActivity(toUnityPlayerActivityInent);

        }
    };


    @Override
    public void onRefresh() {
        lastItem ="0";
        list.clear();
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                LoadDialog.show(context);
                atm.request(GETANIMATION,MallGamePresenter.this);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(recyclerView.getLayoutManager() != null) {
                    //getPositionAndOffset();
                }
            }
        });
        atm.request(GETANIMATION,this);
    }
}