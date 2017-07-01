package com.xtdar.app.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.loader.GlideImageLoader;

import java.util.List;

/**
 * Created by Bob on 2015/3/26.
 */

public class BleAdapter extends BaseAdapter {
    private BleAdapter.ViewHoler holder;
    private GlideImageLoader glideImageLoader;

    private OnItemClick onItemClick;
    public OnItemClick getOnItemClick() {
        return onItemClick;
    }
    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemClick {
        boolean onItemClick(int position, View view, int status);

    }

    public BleAdapter(Context context) {
        super(context);
        glideImageLoader = new GlideImageLoader();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new BleAdapter.ViewHoler();
            convertView = mInflater.inflate(R.layout.item_ble, null);
            holder.deviceName = (TextView) convertView.findViewById(R.id.txt_device_name);
            convertView.setTag(holder);
        } else {
            holder = (BleAdapter.ViewHoler) convertView.getTag();
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.onItemClick(position, v, 1);

            }
        });
        BluetoothDevice device = (BluetoothDevice) mList.get(position);
        holder.deviceName.setText(device.getName());

        //holder.deviceName.setText(((User)mList.get(position)).getNickName());

        return convertView;
    }

    /**
     * deviceName :帮助标题
     */

    class ViewHoler {
        TextView deviceName;
    }

}
