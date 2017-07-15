package com.xtdar.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.response.HelpResponse;
import com.xtdar.app.server.response.MyCommentResponse;
import com.xtdar.app.view.widget.SelectableRoundedImageView;

/**
 * Created by Bob on 2015/3/26.
 */

public class MyCommentAdapter extends BaseAdapter {
    private MyCommentAdapter.ViewHoler holder;
    private GlideImageLoader glideImageLoader;

    private OnItemClick onItemClick;
    public OnItemClick getOnItemClick() {
        return onItemClick;
    }
    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }


    public interface OnItemClick {
        boolean onClick(int position, View view, String status);

    }

    public MyCommentAdapter(Context context) {
        super(context);
        glideImageLoader = new GlideImageLoader();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new MyCommentAdapter.ViewHoler();
            convertView = mInflater.inflate(R.layout.item_comment, null);
            holder.avatar = (SelectableRoundedImageView) convertView.findViewById(R.id.avatar);
            holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.article_title = (TextView) convertView.findViewById(R.id.article_title);
            holder.article_img = (ImageView) convertView.findViewById(R.id.article_img);
            convertView.setTag(holder);
        } else {
            holder = (MyCommentAdapter.ViewHoler) convertView.getTag();
        }
        final MyCommentResponse.DataBean entity = (MyCommentResponse.DataBean) mList.get(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(position, v, entity.getItem_id());

            }
        });


        holder.nickname.setText(entity.getNick_name());


        return convertView;
    }


    /**
     * deviceName :帮助标题
     */

    class ViewHoler {
        SelectableRoundedImageView avatar;
        TextView nickname;
        TextView content;
        TextView article_title;
        ImageView article_img;

    }



}
