package com.xtdar.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.response.MyCommentResponse;
import com.xtdar.app.view.widget.SelectableRoundedImageView;

/**
 * Created by Bob on 2015/3/26.
 */

public class MyCommentAdapter extends BaseAdapter {
    private MyCommentAdapter.ViewHoler holder;
    private GlideImageLoader glideImageLoader;

    private ItemClickHandler onItemClick;
    public ItemClickHandler getOnItemClick() {
        return onItemClick;
    }
    public void setOnItemClick(ItemClickHandler onItemClick) {
        this.onItemClick = onItemClick;
    }


    public interface ItemClickHandler {
        boolean onItemClick(int position, View view, MyCommentResponse.DataBean status);

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
        holder.nickname.setText(entity.getNick_name());
        holder.content.setText(entity.getComment());
        holder.article_title.setText(entity.getReviewed_title());
        glideImageLoader.displayImage(mContext, XtdConst.IMGURI+entity.getImg_path(),holder.avatar);
        glideImageLoader.displayImage(mContext, XtdConst.IMGURI+entity.getReviewed_img(),holder.article_img);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position, v, entity);

            }
        });





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
