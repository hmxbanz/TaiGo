package com.xtdar.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.response.GameCheckResponse;

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
            holder.deviceState = (TextView) convertView.findViewById(R.id.txt_device_state);
            holder.layout_item = (LinearLayout) convertView.findViewById(R.id.layout_item);
            convertView.setTag(holder);
        } else {
            holder = (AlertListAdapter.ViewHoler) convertView.getTag();
        }
        final GameCheckResponse.DataBean entity = (GameCheckResponse.DataBean) mList.get(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClick !=null)
                onItemClick.onClick(position, v, entity);

            }
        });

        if(entity.getStatus()==1){
            holder.deviceName.setTextColor(mContext.getResources().getColor(R.color.titleBlue));
            holder.deviceName.setText(entity.getDevice_name());
            holder.deviceState.setTextColor(mContext.getResources().getColor(R.color.titleBlue));
            holder.deviceState.setText("已开启");
        }
        else if(entity.getStatus()==2){
            holder.deviceName.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.deviceName.setText(entity.getDevice_name());
            holder.deviceState.setTextColor(mContext.getResources().getColor(R.color.green));
            holder.deviceState.setText("已连接");
        }
        else {
            holder.deviceName.setTextColor(mContext.getResources().getColor(R.color.black));
            holder.deviceName.setText(entity.getDevice_name());
            holder.deviceState.setText("");
        }



        return convertView;
    }


    /**
     * deviceName :蓝牙名
     */

    class ViewHoler {
        TextView deviceName;
        TextView deviceState;
        LinearLayout layout_item;
    }

}
