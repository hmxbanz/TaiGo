package com.xtdar.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.response.PersonMsgResponse;
import com.xtdar.app.view.widget.SelectableRoundedImageView;

/**
 * Created by Bob on 2015/3/26.
 */

public class PersonMsgAdapter extends BaseAdapter {
    private PersonMsgAdapter.ViewHoler holder;
    private GlideImageLoader glideImageLoader;

    private ItemClickHandler onItemClick;
    public ItemClickHandler getOnItemClick() {
        return onItemClick;
    }
    public void setOnItemClick(ItemClickHandler onItemClick) {
        this.onItemClick = onItemClick;
    }


    public interface ItemClickHandler {
        boolean onItemClick(int position, View view, String status);

    }

    public PersonMsgAdapter(Context context) {
        super(context);
        glideImageLoader = new GlideImageLoader();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new PersonMsgAdapter.ViewHoler();
            convertView = mInflater.inflate(R.layout.item_person_msg, null);
            holder.avatar = (SelectableRoundedImageView) convertView.findViewById(R.id.avatar);
            holder.nickname = (TextView) convertView.findViewById(R.id.nickname);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.createDate = (TextView) convertView.findViewById(R.id.createdate);
            holder.articleTitle = (TextView) convertView.findViewById(R.id.article_title);
            holder.articleImg = (ImageView) convertView.findViewById(R.id.article_img);
            convertView.setTag(holder);
        } else {
            holder = (PersonMsgAdapter.ViewHoler) convertView.getTag();
        }
        final PersonMsgResponse.DataBean entity = (PersonMsgResponse.DataBean) mList.get(position);
        holder.nickname.setText(entity.getForm_user_name());
        holder.content.setText(entity.getMsg());
        holder.createDate.setText(entity.getPost_time());
        holder.articleTitle.setText(entity.getLinkObj().getTitle());
        glideImageLoader.displayImage(mContext, XtdConst.IMGURI+entity.getForm_img_path(),holder.avatar);
        glideImageLoader.displayImage(mContext, XtdConst.IMGURI+entity.getLinkObj().getImg(),holder.articleImg);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position, v, entity.getMsg_id());

            }
        });





        return convertView;
    }


    /**
     *
     */

    class ViewHoler {
        SelectableRoundedImageView avatar;
        TextView nickname;
        TextView content;
        TextView createDate;
        TextView articleTitle;
        ImageView articleImg;

    }



}
