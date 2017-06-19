package com.xtdar.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.model.User;

import com.xtdar.app.R;

import com.xtdar.app.view.widget.SelectableRoundedImageView;

/**
 * Created by Bob on 2015/3/26.
 */

public class SearchListAdapter extends BaseAdapter {
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
        boolean onButtonClick(int position, View view, int status);

    }

    public SearchListAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHoler();
            convertView = mInflater.inflate(R.layout.item_user, null);
            holder.mNickName = (TextView) convertView.findViewById(R.id.nick_name);
            holder.mAvator = (SelectableRoundedImageView) convertView.findViewById(R.id.img_avator);
            convertView.setTag(holder);
        } else {
            holder = (ViewHoler) convertView.getTag();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemButtonClick.onButtonClick(position, v, 1);

            }
        });
        holder.mNickName.setText(((User)mList.get(position)).getNickName());
        String avator=((User)mList.get(position)).getAvator();
        Glide.with(mContext).load(avator).into(holder.mAvator);

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
        SelectableRoundedImageView mAvator;
        TextView mNickName;
        ImageView mIconThumbsup;
        TextView mCount;
    }



}
