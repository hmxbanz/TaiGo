package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.HomeRecommendAdapter;
import com.xtdar.app.common.json.JsonMananger;
import com.xtdar.app.listener.AlertDialogCallback;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.AdResponse;
import com.xtdar.app.server.response.TaobaoResponse;
import com.xtdar.app.view.activity.LoginActivity;
import com.xtdar.app.view.activity.MineActivity;
import com.xtdar.app.view.activity.SignInActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.widget.DialogWithYesOrNoUtils;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HomeRecommendPresenter extends BasePresenter implements OnDataListener {
    private static final int GETADS = 1;
    private static final int GETRECOMMEND = 2;
    private Banner banner;
    private RecyclerView recycleView;
    private GridLayoutManager gridLayoutManager;
    private HomeRecommendAdapter dataAdapter;
    private List<TaobaoResponse.DataBean.DeviceTypeListBean> list=new ArrayList<>();
    private final BasePresenter basePresenter;
    public HomeRecommendPresenter(Context context){
        super(context);
        basePresenter = BasePresenter.getInstance(context);
        //mActivity = (ContactsActivity) context;
    }

    public void init(Banner banner, RecyclerView recycleView) {
        this.banner =banner;
        this.recycleView=recycleView;

        gridLayoutManager=new GridLayoutManager(context,1);
        recycleView.setLayoutManager(gridLayoutManager);

        //设置列表
        dataAdapter = new HomeRecommendAdapter(list, context);
        //dataAdapter.setFooterView(LayoutInflater.from(context).inflate(R.layout.recyclerview_footer,null));
        recycleView.setAdapter(dataAdapter);
        recycleView.setNestedScrollingEnabled(false);

        LoadDialog.show(context);
        //atm.request(GETADS,this);

    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETADS:
                return mUserAction.getAds();
            case GETRECOMMEND:
                return mUserAction.getTaobao();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        if (result==null)return;
        switch (requestCode) {
            case GETADS:
                AdResponse adResponse = (AdResponse) result;
                if (adResponse.getCode() == XtdConst.SUCCESS) {
                }
                break;
            case GETRECOMMEND:
                TaobaoResponse taobaoResponse = (TaobaoResponse) result;
                if (taobaoResponse.getCode() == XtdConst.SUCCESS) {
                    try {
                        String cache=JsonMananger.beanToJson(taobaoResponse.getData());
                        aCache.put("RecommendList",cache);
                    } catch (HttpException e) {
                        e.printStackTrace();
                    }
                    list.clear();
                    list.addAll(taobaoResponse.getData().getDevice_type_list());
                    dataAdapter.notifyDataSetChanged();

                    List<String> images = new ArrayList<>();
                    for (String s : taobaoResponse.getData().getImg_list()) {
                        s=XtdConst.IMGURI+s;
                        images.add(s);
                    }

                    banner.setImages(images);//设置图片集合
                    banner.start();
                }
                break;
        }
    }

    //加载数据
    public void loadData() {
        //取缓存
            String Cache = aCache.getAsString("RecommendList");
            if(Cache!=null && !("null").equals(Cache))
                try {
                    TaobaoResponse.DataBean listCache = JsonMananger.jsonToBean(Cache, TaobaoResponse.DataBean.class);
                    if(listCache.getDevice_type_list()!=null)
                    list.addAll(listCache.getDevice_type_list());
                    dataAdapter.notifyDataSetChanged();

                    List<String> images = new ArrayList<>();
                    if(listCache.getDevice_type_list()!=null)
                    for (String s : listCache.getImg_list()) {
                        s=XtdConst.IMGURI+s;
                        images.add(s);
                    }
                    banner.setImages(images);//设置图片集合
                    banner.start();

                } catch (HttpException e) {
                    e.printStackTrace();
                }

        LoadDialog.show(context);
        atm.request(GETRECOMMEND,this);
    }
    public void onMeClick(View v) {
        basePresenter.initData();
        if (!basePresenter.isLogin) {
            DialogWithYesOrNoUtils.getInstance().showDialog(context, "请先登录", null, "前住登录", new AlertDialogCallback() {
                @Override
                public void executeEvent() {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }

            });
        } else {
            switch (v.getId()) {
                case R.id.layout_me:
                    context.startActivity(new Intent(context, MineActivity.class));
                    break;
                case R.id.right_icon:
                    context.startActivity(new Intent(context, SignInActivity.class));
                    break;
            }

        }

    }
}