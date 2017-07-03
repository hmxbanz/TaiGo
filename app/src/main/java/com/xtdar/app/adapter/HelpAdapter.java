package com.xtdar.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.response.HelpResponse;

/**
 * Created by Bob on 2015/3/26.
 */

public class HelpAdapter extends BaseAdapter {
    private HelpAdapter.ViewHoler holder;
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

    public HelpAdapter(Context context) {
        super(context);
        glideImageLoader = new GlideImageLoader();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new HelpAdapter.ViewHoler();
            convertView = mInflater.inflate(R.layout.item_help, null);
            holder.title = (TextView) convertView.findViewById(R.id.txt_title);
            convertView.setTag(holder);
        } else {
            holder = (HelpAdapter.ViewHoler) convertView.getTag();
        }
        final HelpResponse.DataBean entity = (HelpResponse.DataBean) mList.get(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(position, v, entity.getArticle_id());

            }
        });


        holder.title.setText(entity.getTitle());


        return convertView;
    }


    /**
     * deviceName :帮助标题
     */

    class ViewHoler {
        TextView title;
    }



}
