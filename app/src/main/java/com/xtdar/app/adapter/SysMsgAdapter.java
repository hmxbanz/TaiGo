package com.xtdar.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.response.FavorResponse;
import com.xtdar.app.server.response.SysMsgResponse;

/**
 * Created by Bob on 2015/3/26.
 */

public class SysMsgAdapter extends BaseAdapter {
    private static final int TYPE_1 = 1;
    private static final int TYPE_2 = 2;
    private ViewHoler holder;
    private GlideImageLoader glideImageLoader;

    private OnItemButtonClick mOnItemButtonClick;
    private SysMsgResponse.DataBean entity;

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

    public SysMsgAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        entity = (SysMsgResponse.DataBean) mList.get(position);

        if (convertView == null) {
            holder = new ViewHoler();
            // 按当前所需的样式，确定new的布局
            switch (type) {
                case TYPE_1:
                    convertView = mInflater.inflate(R.layout.item_sys_msg, null);
                    holder.mTitle = (TextView) convertView.findViewById(R.id.title);
                    holder.createDate = (TextView) convertView.findViewById(R.id.createdate);
                    holder.mCover = (ImageView) convertView.findViewById(R.id.img_cover);
                    holder.mCover.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemButtonClick.onItemClick(position, v, entity.getMsg_id());
                        }
                    });
                    holder.mTitle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemButtonClick.onItemClick(position, v, entity.getMsg_id());
                        }
                    });
                    holder.mTitle.setText(entity.getLinkObj().getTitle());
                    holder.createDate.setText(entity.getPost_time());
                    String avator=XtdConst.IMGURI+entity.getLinkObj().getImg();
                    Glide.with(mContext).load(avator).into(holder.mCover);
                    break;
                case TYPE_2:
                    convertView = mInflater.inflate(R.layout.item_sys_msg2, null);
                    holder.mTitle = (TextView) convertView.findViewById(R.id.title);
                    holder.mTitle.setText(entity.getMsg());

                    holder.createDate = (TextView) convertView.findViewById(R.id.createdate);
                    holder.createDate.setText(entity.getPost_time());
                    break;

            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHoler) convertView.getTag();
        }



        return convertView;
    }

    /// 根据position返回相应的Item
    @Override
    public int getItemViewType(int position) {
        SysMsgResponse.DataBean bean= (SysMsgResponse.DataBean) mList.get(position);
        if (TextUtils.isEmpty(bean.getMsg()))
            return TYPE_1;
        else
            return TYPE_2;

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
        TextView createDate;
    }


}
