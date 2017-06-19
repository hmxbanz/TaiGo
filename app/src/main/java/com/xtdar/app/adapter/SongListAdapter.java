package com.xtdar.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.MyVideoPlayer;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.model.User;
import com.xtdar.app.server.response.SongDetailResponse;
import com.xtdar.app.view.widget.SelectableRoundedImageView;

/**
 * Created by Bob on 2015/3/26.
 */

public class SongListAdapter extends BaseAdapter {
    private ViewHoler holder;
    private GlideImageLoader glideImageLoader;

    private OnItemButtonClick mOnItemButtonClick;
    public OnItemButtonClick getOnItemButtonClick() {
        return mOnItemButtonClick;
    }
    public void setOnItemButtonClick(OnItemButtonClick onItemButtonClick) {
        this.mOnItemButtonClick = onItemButtonClick;
    }


    public interface OnItemButtonClick {
        boolean onButtonClick(int position, View view, String songId ,String albumId);

    }

    public SongListAdapter(Context context) {
        super(context);
        glideImageLoader=new GlideImageLoader();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHoler();
            convertView = mInflater.inflate(R.layout.item_mp3, null);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txt_title);
            holder.txtPlay = (TextView) convertView.findViewById(R.id.txt_play);
            holder.txtTime = (TextView) convertView.findViewById(R.id.txt_time);
            holder.txtFileSize = (TextView) convertView.findViewById(R.id.txt_file_size);
            holder.imgCover = (SelectableRoundedImageView) convertView.findViewById(R.id.img_cover);
            holder.myVideoPlayer = (MyVideoPlayer) convertView.findViewById(R.id.my_video_player);
            convertView.setTag(holder);
        } else {
            holder = (ViewHoler) convertView.getTag();
        }

        //entity=mList.get(position);
        final SongDetailResponse.DataBean.SongListBean entity=((SongDetailResponse.DataBean.SongListBean)mList.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemButtonClick.onButtonClick(position, v, entity.getItem_id(),entity.getAlbum_id());
            }
        });
        holder.txtTitle.setText(entity.getItem_title());
        holder.txtPlay.setText(entity.getClick_count());
        holder.txtTime.setText(entity.getTime_spend());
        holder.txtFileSize.setText(entity.getSave_size());
        glideImageLoader.displayImage(mContext, XtdConst.IMGURI+entity.getItem_cover(),holder.imgCover);

//        final UserRelationshipResponse.ResultEntity bean = (UserRelationshipResponse.ResultEntity) mList.get(position);
//        holder.mNickName.setText(bean.getUser().getNickname());
//        if (TextUtils.isEmpty(bean.getUser().getPortraitUri())) {
//            ImageLoader.getInstance().displayImage(RongGenerate.generateDefaultAvatar(bean.getUser().getNickname(), bean.getUser().getId()), holder.mAvator, App.getOptions());
//        } else {
//            ImageLoader.getInstance().displayImage(bean.getUser().getPortraitUri(), holder.mAvator, App.getOptions());
//        }
//        holder.mMessage.setText(bean.getMessage());
//        holder.mState.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mOnItemButtonClick != null) {
//                    mOnItemButtonClick.onButtonClick(position, v, bean.getStatus());
//                }
//            }
//        });
        //增加封面
//        ImageView imageView = new ImageView(mContext);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        glideImageLoader.displayImage(mContext, XtdConst.IMGURI+entity.getItem_cover(),imageView);
//        holder.myVideoPlayer.setThumbImageView(imageView);
        final String url = XtdConst.IMGURI+entity.getResource();
        //final String url = "http://7xse1z.com1.z0.glb.clouddn.com/1491813192";
        //final String url = "http://111.198.24.133:83/yyy_login_server/pic/YB059284/97778276040859/1.mp4";

        //默认缓存路径
        holder.myVideoPlayer.setBackGroundToPlayer(XtdConst.IMGURI+entity.getItem_cover());
        holder.myVideoPlayer.setUp(url, true , null, "这是title");
        return convertView;
    }


    /**
     * imgCover :封面
     * txtTitle : 标题
     * txtPlay : 播放次数
     * txtTime : 时长
     * txtFileSize :文件大小
     */

    class ViewHoler {
        SelectableRoundedImageView imgCover;
        TextView txtTitle;
        TextView txtPlay;
        TextView txtTime;
        TextView txtFileSize;
        MyVideoPlayer myVideoPlayer;
    }



}
