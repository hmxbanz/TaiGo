package com.xtdar.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.model.User;

/**
 * Created by Bob on 2015/3/26.
 */

public class OrderAdapter extends BaseAdapter {
    private ViewHoler holder;

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

    public OrderAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHoler();
            convertView = mInflater.inflate(R.layout.item_order, null);
            holder.mProductName = (TextView) convertView.findViewById(R.id.product_name);
            holder.mPrice = (TextView) convertView.findViewById(R.id.price);
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
        holder.mProductName.setText(((User)mList.get(position)).getNickName());

        return convertView;
    }

    /**
     * mProductName : 产品名
     * mPrice : 价格
     * mRemark : 备注
     * item : {"mProductName":"","mPrice":"","mRemark":""}
     */

    class ViewHoler {
        TextView mProductName;
        TextView mPrice;
        TextView mRemark;
    }



}
