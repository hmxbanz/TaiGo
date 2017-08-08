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
import android.text.TextUtils;
import android.view.View;

import com.clj.fastble.data.ScanResult;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.AlertListAdapter;
import com.xtdar.app.adapter.ClassListAnimationAdapter;
import com.xtdar.app.common.NToast;
import com.xtdar.app.common.json.JsonMananger;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.listener.EndlessRecyclerOnScrollListener;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.GameCheckResponse;
import com.xtdar.app.server.response.GameListResponse;
import com.xtdar.app.service.BluetoothService;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.activity.UnityPlayerActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithList;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class MallGamePresenter extends BasePresenter implements  SwipeRefreshLayout.OnRefreshListener,OnDataListener,ClassListAnimationAdapter.ItemClickListener, AlertListAdapter.OnItemClick {
    private static final int GETANIMATION = 2;
    private static final int GAMECHECK = 3;
    private Banner Banner;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ClassListAnimationAdapter dataAdapter;
    private List<GameListResponse.DataBean> gameList =new ArrayList<>();
    private String lastItem ="0";
    private int lastOffset;
    private int lastPosition;
    private String deviceName;
    private Main2Activity mActivity;
    private String unityGameId;
    private String gameId;


    private SwipeRefreshLayout swiper;
    private AlertListAdapter alertListAdapter;
    private List<GameCheckResponse.DataBean> deviceList;
    private Intent toUnityPlayerActivityInent;
    private boolean isClickable=false;
    private BasePresenter basePresenter;

    public MallGamePresenter(Context context){
        super(context);
        mActivity = (Main2Activity) context;
        basePresenter = BasePresenter.getInstance(context);
        dataAdapter = new ClassListAnimationAdapter(this.context);
        alertListAdapter= new AlertListAdapter(context);

    }

    public void init(SwipeRefreshLayout swiper, RecyclerView recyclerView) {

        this.swiper=swiper;
        this.swiper.setOnRefreshListener(this);
        this.recyclerView =recyclerView;
        gridLayoutManager=new GridLayoutManager(context,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(dataAdapter);
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
                return mUserAction.gameCheck(gameId);
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
                    gameList.addAll(response.getData());
                    //设置列表
                    //dataAdapter.setHeaderView(LayoutInflater.from(context).inflate(R.layout.recyclerview_header,null));
                    dataAdapter.setListItems(gameList);
                    dataAdapter.setOnItemClickListener(this);
                    dataAdapter.notifyDataSetChanged();
                }
                break;
            case GAMECHECK :
                    //取得设备信息，做后续工作
                    //通过intent 信息
                GameCheckResponse gameCheckResponse = (GameCheckResponse) result;
                if (gameCheckResponse.getCode() == 1) {
                        //可以玩弹出选择设备列表
                    //弹出设备推荐列表
//找高低配设备属性
                    deviceList=gameCheckResponse.getData();
                    for(GameCheckResponse.DataBean bean:deviceList)
                    {
                        GameCheckResponse.DataBean.DeviceConfig gameConfig = null;
                        try {
                            gameConfig = JsonMananger.jsonToBean(bean.getDevice_conf(), GameCheckResponse.DataBean.DeviceConfig.class);
                            bean.setDeviceConfig(gameConfig);
                        } catch (HttpException e) {
                            e.printStackTrace();
                        }


                    }
//对比

                    for(GameCheckResponse.DataBean bean: deviceList){
                        for(ScanResult result1:mActivity.scanResultList)
                        {
                            //NToast.shortToast(context,bean.getMac_address()+"="+result1.getDevice().getAddress());
                            if (bean.getMac_address().equals(result1.getDevice().getAddress()))
                                bean.setStatus(1);
                        }
                    }

                    String connectMac =mActivity.mBluetoothService.getMac();

                    for(GameCheckResponse.DataBean bean2: deviceList){
                        if (bean2.getMac_address().equals(connectMac))
                            bean2.setStatus(2);
                    }

                    DialogWithList dialogWithList=DialogWithList.getInstance(context);
                    dialogWithList.showDialog(new DialogWithList.DialogCallBack(){
                        @Override
                        public void executeEvent() {

                        }

                        @Override
                        public void onCancle() {

                        }
                    });

                    dialogWithList.setTitle("请选择设备");
                    dialogWithList.setConfirmText("更多设备");
                    this.alertListAdapter.setmList(deviceList);
                    this.alertListAdapter.setOnItemClick(this);
                    dialogWithList.getListView().setAdapter(this.alertListAdapter);

                }
                else
                {
                    //弹出设备推荐列表
                    DialogWithList dialogWithList=DialogWithList.getInstance(context);
                    dialogWithList.showDialog(new DialogWithList.DialogCallBack(){
                        @Override
                        public void executeEvent() {

                        }

                        @Override
                        public void onCancle() {

                        }
                    });
                    dialogWithList.setTitle("无匹配设备");
                    dialogWithList.setContent("该游戏需要以下设备才能运行");
                    dialogWithList.setCancleText("前往购买");
                    dialogWithList.setConfirmText("玩其它游戏");
                    dialogWithList.getContent().setVisibility(View.VISIBLE);
                    this.alertListAdapter.setmList(gameCheckResponse.getData());
                    dialogWithList.getListView().setAdapter(this.alertListAdapter);
                }

                break;
        }
    }


    @Override
    public void onItemClick(int position, GameListResponse.DataBean bean) {
        if(!basePresenter.isLogin){
            DialogWithYesOrNoUtils.getInstance().showDialog(context, "请先登录", null,"前住登录", new AlertDialogCallback() {
                @Override
                public void executeEvent() {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

            });
        }
        this.unityGameId =String.valueOf(bean.getGameConfig().getUnity_game_id());
        this.gameId =bean.getGame_id();
        /////////////////////////////////////////mActivity.scanResultList.clear();
        mActivity.mBluetoothService.setScanCallback(callback);

        //if(isClickable) {
            //传游戏名查询
            LoadDialog.show(context);
            atm.request(GAMECHECK,this);
        //}
        //else
           // DialogWithYesOrNoUtils.getInstance().showDialog(context,"请稍候，正在扫描",null,null,new AlertDialogCallback());
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


    private BluetoothService.Callback callback = new BluetoothService.Callback() {
        @Override
        public void onStartScan() {
        }

        @Override
        public void onScanning(ScanResult result) {

            //NToast.shortToast(context, "name:::::"+result.getDevice().getAddress());
        }

        @Override
        public void onScanComplete() {
            isClickable=true;
        }

        @Override
        public void onConnecting() {
            LoadDialog.show(context);
        }

        @Override
        public void onConnectFail() {
            LoadDialog.dismiss(context);
            NToast.shortToast(context, "连接失败,请检查设备是否开启");
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
        gameList.clear();
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
//弹出框蓝牙列表点击事件
    @Override
    public boolean onClick(int position, View view, GameCheckResponse.DataBean entity) {
        GameCheckResponse.DataBean bean = deviceList.get(position);
        toUnityPlayerActivityInent = new Intent(context, UnityPlayerActivity.class);
        toUnityPlayerActivityInent.putExtra("ServiceId", bean.getService_uuid());
        toUnityPlayerActivityInent.putExtra("ReadId", bean.getRead_uuid());
        toUnityPlayerActivityInent.putExtra("WriteId", bean.getWrite_uuid());
        toUnityPlayerActivityInent.putExtra("isHigh", bean.getDeviceConfig().getIsHigh());
        toUnityPlayerActivityInent.putExtra("gameId", unityGameId);
            String connectMac =mActivity.mBluetoothService.getMac();
        if(TextUtils.isEmpty(connectMac)) {
            mActivity.mBluetoothService.scanAndConnect5(bean.getMac_address());

        }
        else {

            context.startActivity(toUnityPlayerActivityInent);
        }


        return true;
    }
}