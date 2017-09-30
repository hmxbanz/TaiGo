package com.xtdar.app.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.clj.fastble.data.ScanResult;
import com.orhanobut.logger.Logger;
import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.AlertListAdapter;
import com.xtdar.app.adapter.ClassListAnimationAdapter;
import com.xtdar.app.common.NLog;
import com.xtdar.app.common.NToast;
import com.xtdar.app.common.json.JsonMananger;
import com.xtdar.app.db.DBManager;
import com.xtdar.app.db.DownloadGame;
import com.xtdar.app.db.DownloadGameDao;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.listener.EndlessRecyclerOnScrollListener;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.GameCheckResponse;
import com.xtdar.app.server.response.GameListResponse;
import com.xtdar.app.service.BluetoothService;
import com.xtdar.app.service.DownloadGameService;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.activity.UnityPlayerActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithList;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class MallGamePresenter extends BasePresenter implements  SwipeRefreshLayout.OnRefreshListener,OnDataListener,ClassListAnimationAdapter.ItemClickListener, AlertListAdapter.OnItemClick {
    private static final int GETMALLLIST = 2;
    private static final int GAMECHECK = 3;
    private Banner Banner;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private ClassListAnimationAdapter dataAdapter;
    private List<GameListResponse.DataBean> list =new ArrayList<>();
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
    private boolean isFirstLoad=true;
    private EndlessRecyclerOnScrollListener onScrollListener;
    private DialogWithList dialogWithList;
    private DownloadGameDao downloadGameDao;

    private DownloadGameService downloadService;
    private TextView btnStartGame;
    public boolean isDownloading;
    private String gameName;

    public MallGamePresenter(Context context){
        super(context);
        mActivity = (Main2Activity) context;
        basePresenter = BasePresenter.getInstance(context);
        dataAdapter = new ClassListAnimationAdapter(this.context);
        alertListAdapter= new AlertListAdapter(context);
        dataAdapter.setListItems(list);
        dataAdapter.setOnItemClickListener(this);
        downloadGameDao= DBManager.getInstance().getDaoSession().getDownloadGameDao();
    }

    public void init(SwipeRefreshLayout swiper, RecyclerView recyclerView) {
        this.swiper=swiper;
        this.swiper.setOnRefreshListener(this);
        this.recyclerView =recyclerView;
        gridLayoutManager=new GridLayoutManager(context,1);
        onScrollListener=new EndlessRecyclerOnScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Logger.d("getShot currentPage:%s", currentPage);
                lastItem = String.valueOf(currentPage - 1);
                LoadDialog.show(context);
                atm.request(GETMALLLIST, MallGamePresenter.this);
                setCanloadMore(false);
            }
        };
        recyclerView.addOnScrollListener(onScrollListener);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(dataAdapter);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETMALLLIST:
                return mUserAction.getShot("0",lastItem,"5");
            case GAMECHECK:
                return mUserAction.gameCheck(gameId);
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if(result ==null) return;
        this.swiper.setRefreshing(false);
        switch (requestCode) {
            case GETMALLLIST:
                GameListResponse response = (GameListResponse) result;
                if (response !=null && response.getCode() == XtdConst.SUCCESS ) {
                    final List<GameListResponse.DataBean> datas = response.getData();

                    for (GameListResponse.DataBean bean : datas) {
                        List<DownloadGame> listDb = downloadGameDao.loadAll();
                        //查询本地数据库是否有下载
                        Query<DownloadGame> query = downloadGameDao.queryBuilder().where(DownloadGameDao.Properties.GameId.eq(bean.getGame_id()))
                                .orderDesc(DownloadGameDao.Properties.GameId).build();
                        int count=query.list().size();

                        if (count > 0)
                            bean.setIs_download(true);
                    }

                    if(isFirstLoad) {
                        list = response.getData();
                        isFirstLoad=false;
                        try {
                            String cache=JsonMananger.beanToJson(datas);
                            basePresenter.aCache.put("MallGameList",cache);
                        } catch (HttpException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                        list.addAll(datas);

                    onScrollListener.setCanloadMore(true);

                    int wwwwww=0;
                    for (GameListResponse.DataBean oo : list) {
                        Logger.d("游戏索引:"+wwwwww+++"  游戏ID:%s  游戏名:%s", oo.getGame_id(),oo.getGame_name());
                    }
                    Logger.d("数量:%s=列表:%s", list.size(),list);

                    dataAdapter.setListItems(list);
                    dataAdapter.notifyDataSetChanged();
                }
                else {
                    NToast.shortToast(context, "大厅游戏："+response.getMsg());
                }
                break;
            case GAMECHECK :
                //取得设备信息，做后续工作
                //通过intent 信息
                GameCheckResponse gameCheckResponse = (GameCheckResponse) result;
                if (gameCheckResponse.getCode() == XtdConst.SUCCESS) {
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
                    String connectMac = mActivity.mBluetoothService.getMac();
                    for(GameCheckResponse.DataBean bean: deviceList){
                        for(ScanResult result1:mActivity.scanResultList)
                        {
                            if (bean.getMac_address().equals(result1.getDevice().getAddress()))
                                bean.setStatus(1);
                        }

                        if (bean.getMac_address().equals(connectMac))
                            bean.setStatus(2);
                    }

                    Iterator<GameCheckResponse.DataBean> iterator = deviceList.iterator();
                    while (iterator.hasNext()) {
                        GameCheckResponse.DataBean student = iterator.next();
                        if (student.getStatus() == 0)
                            iterator.remove();//这里要使用Iterator的remove方法移除当前对象，如果使用List的remove方法，则同样会出现ConcurrentModificationException
                    }


                    if(deviceList.size()==0)
                    {
                        DialogWithYesOrNoUtils.getInstance().showDialog(context,"请先开启玩具设备",null,null,new AlertDialogCallback());
                    }
                    else
                    {
                        dialogWithList=DialogWithList.getInstance(context);
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


                }
                else
                {
                    //无设备可用弹出推荐列表
                    //弹出设备推荐列表
                    dialogWithList=DialogWithList.getInstance(context);
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
    public void onItemClick(View v, int position, GameListResponse.DataBean bean) {
        if(isDownloading){
            if(!bean.getGame_id().equals(this.gameId))
            DialogWithYesOrNoUtils.getInstance().showDialog(context, "游戏正在下载，请稍候", null,null, new AlertDialogCallback() {   });

            return;
        }
        if(!basePresenter.isLogin){
            DialogWithYesOrNoUtils.getInstance().showDialog(context, "请先登录", null,"前住登录", new AlertDialogCallback() {
                @Override
                public void executeEvent() {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

            });
            return;
        }
        btnStartGame = (TextView)v.findViewById(R.id.btn_start_game);
        this.unityGameId =String.valueOf(bean.getGameConfig().getUnity_game_id());
        this.gameId =bean.getGame_id();
        this.gameName =bean.getGame_name();

        //查询本地数据库是否有下载
        Query<DownloadGame> query =downloadGameDao.queryBuilder().where(DownloadGameDao.Properties.GameId.eq(bean.getGame_id()))
                                                  .orderDesc(DownloadGameDao.Properties.GameId).build();

        int game=query.list().size();
        NLog.e("DBdata",game );

        if (game+1== 0) {
            List<DownloadGame> listDb = downloadGameDao.loadAll();

            for(DownloadGame a:listDb) {
                NLog.e("DBdata",a.getGameName() );
            }

            //启动下载服务
            if(downloadService==null)  bindService();
            else downloadService.startDownload("");


        }
        else
        {
            LoadDialog.show(context);
            mActivity.mBluetoothService.setScanCallback(callback);
            mActivity.mBluetoothService.scanDevice();
        }


    }

    public void bindService() {
        Intent bindIntent = new Intent(context, DownloadGameService.class);
        context.bindService(bindIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void unbindService() {
        if(serviceConnection !=null) {
            context.unbindService(serviceConnection);
            serviceConnection =null;
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadService = ((DownloadGameService.DownloadGameBinder) service).getService();
            downloadService.setDownloadCallback(new DownloadGameService.DownloadCallback() {
                @Override
                public void onProgessUpdate(float progress) {
                    isDownloading=true;
                    if(progress==1)
                        btnStartGame.setText("玩一玩");
                    else
                        btnStartGame.setText("下载中"+String.valueOf((int)(progress*100))+"%");
                }

                @Override
                public void onDownloadDone() {
                    isDownloading=!isDownloading;
                    //插入本地数据库
                    DownloadGame newDownloadGame = new DownloadGame();
                    newDownloadGame.setGameId(Integer.parseInt(MallGamePresenter.this.gameId));
                    newDownloadGame.setGameName(MallGamePresenter.this.gameName);
                    downloadGameDao.insert(newDownloadGame);

                }
            });
            downloadService.startDownload("");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            downloadService = null;
        }
    };

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
        public void onStartScan() {                mActivity.scanResultList.clear();    }

        @Override
        public void onScanning(ScanResult result) {     mActivity.scanResultList.add(result);    }

        @Override
        public void onScanComplete() {
            //传游戏名查询
            atm.request(GAMECHECK,MallGamePresenter.this);  }

        @Override
        public void onConnecting() {      }

        @Override
        public void onConnectFail() {
            LoadDialog.dismiss(context);
            NToast.shortToast(context, "连接失败,请确保设备已开启。");       }

        @Override
        public void onDisConnected() {       }

        @Override
        public void onServicesDiscovered() {
            LoadDialog.dismiss(context);
            context.startActivity(toUnityPlayerActivityInent);

        }
    };

    @Override
    public void onRefresh() {
        onScrollListener.reset();
        onScrollListener.setCanloadMore(true);
        lastItem ="0";
        list.clear();
        atm.request(GETMALLLIST,this);
    }
    //弹出框蓝牙列表点击事件
    @Override
    public boolean onClick(int position, View view, GameCheckResponse.DataBean entity) {
        dialogWithList.cancleAlterDialog();
        GameCheckResponse.DataBean bean = deviceList.get(position);
        toUnityPlayerActivityInent = new Intent(context, UnityPlayerActivity.class);

        toUnityPlayerActivityInent.putExtra("ServiceId", bean.getService_uuid());
        toUnityPlayerActivityInent.putExtra("ReadId", bean.getRead_uuid());
        toUnityPlayerActivityInent.putExtra("WriteId", bean.getWrite_uuid());
        toUnityPlayerActivityInent.putExtra("isHigh", bean.getDeviceConfig().getIsHigh());
        toUnityPlayerActivityInent.putExtra("gameId", unityGameId);
        toUnityPlayerActivityInent.putExtra("BleName", bean.getDevice_name());

        String connectMac = mActivity.mBluetoothService.getMac();
        NLog.w("connectMac:::::::",connectMac);
        if(TextUtils.isEmpty(connectMac)) {
            LoadDialog.show(context);
            mActivity.mBluetoothService.scanAndConnect5(bean.getMac_address());
        }
        else {
            context.startActivity(toUnityPlayerActivityInent);
        }
        return true;
    }
    //加载数据
    public void loadData() {
        onScrollListener.reset();
        onScrollListener.setCanloadMore(true);
        if(isFirstLoad) {
            String Cache = aCache.getAsString("MallGameList");
            if(Cache!=null && !("null").equals(Cache))
                try {
                    List<GameListResponse.DataBean> gameListCache = JsonMananger.jsonToList(Cache, GameListResponse.DataBean.class);
                    list.addAll(gameListCache);
                    dataAdapter.notifyDataSetChanged();

                } catch (HttpException e) {
                    e.printStackTrace();
                }
        }
        lastItem ="0";
        list.clear();
        LoadDialog.show(context);
        atm.request(GETMALLLIST,this);
    }


}
