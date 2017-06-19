package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.adapter.SongListAdapter;
import com.xtdar.app.common.NToast;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.SongDetailResponse;
import com.xtdar.app.view.activity.SongAlbumActivity;
import com.xtdar.app.view.activity.SongDetailActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class SongAlbumPresenter extends BasePresenter implements OnDataListener,SongListAdapter.OnItemButtonClick
{
    private static final int GETSONGALBUM = 1;
    private static final int GETSONGLIST = 2;
    private final GlideImageLoader glideImageLoader;
    private SongAlbumActivity mActivity;
    private String itemId;
    private ListView listView;


    public SongAlbumPresenter(Context context){
        super(context);
        mActivity = (SongAlbumActivity) context;
        glideImageLoader = new GlideImageLoader();
    }

    public void init(ListView listView) {
        this.listView=listView;
        Intent intent = mActivity.getIntent();
        itemId = intent.getStringExtra(XtdConst.ITEMID);
        LoadDialog.show(mContext);
        atm.request(GETSONGALBUM, this);
    }
    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {

        switch (requestCode) {
            case GETSONGALBUM:
                return mUserAction.getSongAlbumDetail(itemId);

        }
        return null;
    }
    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(mContext);
        switch (requestCode) {
            case GETSONGALBUM:
                SongDetailResponse response = (SongDetailResponse) result;
                if (response.getCode() == XtdConst.SUCCESS) {
                    SongDetailResponse.DataBean entity = response.getData();
                    SongDetailResponse.DataBean.AlbumInfoBean albumInfo = response.getData().getAlbum_info();
                    ((TextView)mActivity.findViewById(R.id.txt_title)).setText(albumInfo.getItem_title());
                    ((TextView)mActivity.findViewById(R.id.txt_episode_num)).setText("集数："+String.valueOf(entity.getSong_num())+"集");
                    ((TextView)mActivity.findViewById(R.id.txt_age)).setText("年龄："+albumInfo.getAge_group()+"岁");
                    ((TextView)mActivity.findViewById(R.id.txt_class)).setText("分类："+albumInfo.getClass_name());
                    ((TextView)mActivity.findViewById(R.id.txt_play_num)).setText("播放:"+albumInfo.getClick_count()+"万");
                    ((TextView)mActivity.findViewById(R.id.xianji)).setText("选集 ("+String.valueOf(entity.getSong_num())+")");
                    glideImageLoader.displayImage(mContext,XtdConst.IMGURI+albumInfo.getItem_cover(),((ImageView)mActivity.findViewById(R.id.img_cover)));

                    List<SongDetailResponse.DataBean.SongListBean> songList = entity.getSong_list();
                    SongListAdapter songListAdapter = new SongListAdapter(mContext);
                    songListAdapter.setmList(songList);
                    songListAdapter.setOnItemButtonClick(this);
                    listView.setAdapter(songListAdapter);
                    atm.request(GETSONGLIST, this);
                }
                NToast.shortToast(mContext, response.getMsg());
                break;

        case GETSONGLIST:

        break;
        }
    }


    @Override
    public boolean onButtonClick(int position, View view, String songId,String albumId) {
        switch (view.getId()) {
            case R.id.list_item_layout:
                SongDetailActivity.StartActivity(mContext,songId,albumId);
                break;
        }
        return true;

    }

}