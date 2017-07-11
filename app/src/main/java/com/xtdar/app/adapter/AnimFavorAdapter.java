package com.xtdar.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.model.User;
import com.xtdar.app.server.response.FavorResponse;

/**
 * Created by Bob on 2015/3/26.
 */

public class AnimFavorAdapter extends BaseAdapter {
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
        boolean onItemClick(int position, View view, String status);
        boolean onButtonClick(int position, View view, String status);
    }

    public AnimFavorAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHoler();
            convertView = mInflater.inflate(R.layout.item_favor, null);
            holder.mTitle = (TextView) convertView.findViewById(R.id.title);
            holder.mCover = (ImageView) convertView.findViewById(R.id.img_cover);
            holder.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHoler) convertView.getTag();
        }
        final FavorResponse.DataBean entity = (FavorResponse.DataBean) mList.get(position);
        holder.mCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemButtonClick.onItemClick(position, v, entity.getItem_id());
            }
        });
        holder.mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemButtonClick.onItemClick(position, v, entity.getItem_id());
            }
        });
        holder.mTitle.setText(entity.getItem_title());
        String avator=XtdConst.IMGURI+entity.getItem_cover();
        Glide.with(mContext).load(avator).into(holder.mCover);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemButtonClick.onButtonClick(position, v, entity.getItem_id());
            }
        });

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
//                    mOnItemButtonClick.onClick(position, v, bean.getStatus());
//                }
//            }
//        });

        return convertView;
    }


    /**
     * mAvator :头像
     * mNickName : 昵称
     * mIconThumbsup : 查看人数icon
     * mCount : 查看次数
     * item : {"mAvator":"","mNickName":"","mIconThumbsup":"","mCount":""}
     */

    class ViewHoler {
        ImageView mCover;
        TextView mTitle;
        ImageView mIconThumbsup;
        TextView mCount;
        Button btnDelete;
    }



}
