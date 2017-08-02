package com.xtdar.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.response.GameCheckResponse;
import com.xtdar.app.server.response.HelpResponse;

/**
 * Created by Bob on 2015/3/26.
 */

public class AlertListAdapter extends BaseAdapter {
    private AlertListAdapter.ViewHoler holder;
    private GlideImageLoader glideImageLoader;

    private OnItemClick onItemClick;
    public OnItemClick getOnItemClick() {
        return onItemClick;
    }
    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }


    public interface OnItemClick {
        boolean onClick(int position, View view, GameCheckResponse.DataBean entity);

    }

    public AlertListAdapter(Context context) {
        super(context);
        glideImageLoader = new GlideImageLoader();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new AlertListAdapter.ViewHoler();
            convertView = mInflater.inflate(R.layout.item_alert_list, null);
            holder.deviceName = (TextView) convertView.findViewById(R.id.txt_device_name);
            convertView.setTag(holder);
        } else {
            holder = (AlertListAdapter.ViewHoler) convertView.getTag();
        }
        final GameCheckResponse.DataBean entity = (GameCheckResponse.DataBean) mList.get(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onClick(position, v, entity);

            }
        });


        holder.deviceName.setText(entity.getDevice_name());

        return convertView;
    }


    /**
     * deviceName :蓝牙名
     */

    class ViewHoler {
        TextView deviceName;
    }



}
