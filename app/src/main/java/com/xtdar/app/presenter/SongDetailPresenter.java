package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.MyVideoPlayer;
import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.SongDetailResponse;
import com.xtdar.app.view.activity.SongDetailActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class SongDetailPresenter extends BasePresenter implements OnDataListener
{
    private static final int GETSONGALBUM = 1;
    private static final int GETSONGLIST = 2;
    private static final int GETCOMMENT = 3;
    private final GlideImageLoader glideImageLoader;
    private SongDetailActivity mActivity;
    private String songId;
    private ListView listView;
    private String albumId;
    private TextView txtTile;
    private MyVideoPlayer videoPlayer;
    private SongDetailResponse.DataBean.SongListBean song;
    private List<SongDetailResponse.DataBean.SongListBean> songList;
    private int songIndex=0;
    private Drawable iconPause;
    private Drawable iconPlaying;
    private boolean isPlaying=true;
    private boolean isFirst =true;
    private ImageView imgPlay;


    public SongDetailPresenter(Context context){
        super(context);
        mActivity = (SongDetailActivity) context;
        glideImageLoader = new GlideImageLoader();
    }

    public void init(MyVideoPlayer videoPlayer, TextView txtTitle, ImageView imgForward, ImageView imgPlay, ImageView imgNext) {
        this.videoPlayer=videoPlayer;
        this.txtTile=txtTitle;
        this.imgPlay=imgPlay;
        Intent intent = mActivity.getIntent();
        songId = intent.getStringExtra(XtdConst.SONGID);
        albumId = intent.getStringExtra(XtdConst.ALBUMID);
        //
        LoadDialog.show(mContext);
        atm.request(GETSONGALBUM, this);
    }
    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {

        switch (requestCode) {
            case GETSONGALBUM:
                return mUserAction.getSongAlbumDetail(albumId);

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
                     songList = entity.getSong_list();
                    findSong();
                    this.txtTile.setText(song.getItem_title());

                    final String url = XtdConst.IMGURI+song.getResource();
                    this.videoPlayer.setBackGroundToPlayer(XtdConst.IMGURI+song.getItem_cover());
                    this.videoPlayer.setUp(url, false , null, "这是title");

                    ((TextView)mActivity.findViewById(R.id.txt_album_title)).setText(albumInfo.getItem_title());
                    ((TextView)mActivity.findViewById(R.id.txt_play)).setText(albumInfo.getClick_count()+"万");
                    ((TextView)mActivity.findViewById(R.id.txt_episode_num)).setText("集数："+entity.getSong_num()+"集");
                    glideImageLoader.displayImage(mContext,XtdConst.IMGURI+albumInfo.getItem_cover(),((ImageView)mActivity.findViewById(R.id.img_cover)));
//
//                    List<SongDetailResponse.DataBean.SongListBean> songList = entity.getSong_list();
//                    SongListAdapter songListAdapter = new SongListAdapter(mContext);
//                    songListAdapter.setmList(songList);
//                    listView.setAdapter(songListAdapter);
                    atm.request(GETCOMMENT, this);
                }
                NToast.shortToast(mContext, response.getMsg());
                break;

        case GETSONGLIST:

        break;
        }
    }

    private void findSong() {
        for (int i=0;i< songList.size();i++) {
            if (songList.get(i).getItem_id().equals(this.songId)) {
                this.song=songList.get(i);
                this.songIndex=i;
            }
        }
    }


    public void forward() {

        songIndex=songIndex-1;
        if (songIndex <= -1) {
            NToast.shortToast(mContext, "已经是第一首");
            songIndex=0;
            return;
        }
        else {
            isFirst=true;
            isPlaying=true;
            this.imgPlay.setImageResource(R.drawable.icon_playing);
            this.song = songList.get(songIndex);
            this.txtTile.setText(song.getItem_title());
            final String url = XtdConst.IMGURI + song.getResource();
            this.videoPlayer.setBackGroundToPlayer(XtdConst.IMGURI + song.getItem_cover());
            this.videoPlayer.setUp(url, false, null, "这是title");
        }

    }

    public void play() {
        if(isPlaying){
            imgPlay.setImageResource(R.drawable.icon_pause);
            if(isFirst)
            this.videoPlayer.startPlayLogic();
            else
            this.videoPlayer.onVideoResume();
            //GSYVideoManager.onResume();
        }
        else {
            this.videoPlayer.onVideoPause();
            //GSYVideoManager.onPause();
            imgPlay.setImageResource(R.drawable.icon_playing);
        }
        isFirst=false;
        isPlaying=!isPlaying;
    }

    public void next() {
        songIndex=songIndex+1;
        if(songIndex>=songList.size()){
            NToast.shortToast(mContext, "已经是最后一首");
            songIndex=songList.size()-1;
            return;
        }
        else {
            isFirst=true;
            isPlaying=true;
            this.imgPlay.setImageResource(R.drawable.icon_playing);
            this.song = songList.get(songIndex);
            this.txtTile.setText(song.getItem_title());
            final String url = XtdConst.IMGURI+song.getResource();
            this.videoPlayer.setBackGroundToPlayer(XtdConst.IMGURI+song.getItem_cover());
            this.videoPlayer.setUp(url, false , null, "这是title");
        }
    }
}