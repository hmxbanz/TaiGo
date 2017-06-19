package com.xtdar.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xtdar.app.model.User;
import com.xtdar.app.view.widget.SelectableRoundedImageView;

import java.util.List;

import com.xtdar.app.R;

/**
 * Created by AMing on 16/1/14.
 * Company RongCloud
 */
public class FriendListAdapter extends BaseAdapter implements SectionIndexer {
    private Context mContext;
    private List<User> list;

    public FriendListAdapter(Context context, List<User> list) {
        this.mContext = context;
        this.list = list;
    }


    /**
     * 传入新的数据 刷新UI的方法
     */
    public void updateListView(List<User> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null) return list.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list == null)
            return null;

        if (position >= list.size())
            return null;

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final User mContent = list.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_friend, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.friendname);
            viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
            viewHolder.mImageView = (SelectableRoundedImageView) convertView.findViewById(R.id.frienduri);
            viewHolder.tvUserId = (TextView) convertView.findViewById(R.id.friend_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            String letterFirst = mContent.getLetters();
            if(!TextUtils.isEmpty(letterFirst)){
                letterFirst = String.valueOf(letterFirst.toUpperCase().charAt(0));
            }
            viewHolder.tvLetter.setText(letterFirst);
        } else {
            viewHolder.tvLetter.setVisibility(View.GONE);
        }
        viewHolder.tvTitle.setText(this.list.get(position).getNickName());
        Glide.with(mContext).load(mContent.getAvator()).into(viewHolder.mImageView);
//        if (mContent.isExitsDisplayName()) {
//            viewHolder.tvTitle.setText(this.list.get(position).getDisplayName());
//        } else {
//            viewHolder.tvTitle.setText(this.list.get(position).getName());
//        }
        //String portraitUri = SealUserInfoManager.getInstance().getPortraitUri(list.get(position));
        //ImageLoader.getInstance().displayImage(portraitUri, viewHolder.mImageView, App.getOptions());
        if (mContext.getSharedPreferences("config", Context.MODE_PRIVATE).getBoolean("isDebug", false)) {
            viewHolder.tvUserId.setVisibility(View.VISIBLE);
            viewHolder.tvUserId.setText(list.get(position).getNickName());
        }
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getLetters();
            char firstChar = sortStr.charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getLetters().charAt(0);
    }


    final static class ViewHolder {
        /**
         * 首字母
         */
        TextView tvLetter;
        /**
         * 昵称
         */
        TextView tvTitle;
        /**
         * 头像
         */
        SelectableRoundedImageView mImageView;
        /**
         * userid
         */
        TextView tvUserId;
    }
}
