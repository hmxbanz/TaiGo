package com.xtdar.app.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtdar.app.R;
import com.xtdar.app.adapter.ClassListAnimationAdapter;
import com.xtdar.app.server.response.ClassListResponse;
import com.xtdar.app.server.response.GameListResponse;
import com.xtdar.app.server.response.ShowResponse;

import java.util.List;

/**
 * Created by guoshuyu on 2017/1/9.
 */

public class RecyclerNormalAdapter extends RecyclerView.Adapter {
    private final static String TAG = "RecyclerBaseAdapter";
    private ItemClickListener mListener;
    public void setOnItemClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    private List<ShowResponse.DataBean> itemDataList = null;
    private Context context = null;

    public RecyclerNormalAdapter(Context context, List<ShowResponse.DataBean> itemDataList) {
        this.itemDataList = itemDataList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_video_item_normal, parent, false);
        final RecyclerView.ViewHolder holder = new RecyclerItemNormalHolder(context, v);
        return holder;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder,  final int position) {
        RecyclerItemNormalHolder recyclerItemViewHolder = (RecyclerItemNormalHolder) holder;
        recyclerItemViewHolder.setRecyclerBaseAdapter(this);
        recyclerItemViewHolder.onBind(position, itemDataList.get(position));
        final ShowResponse.DataBean listItem = itemDataList.get(position);
        if(mListener == null) return;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(position,listItem.getShow_id(),listItem.getShow_class_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemDataList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    public void setListData(List<ShowResponse.DataBean> data) {
        itemDataList = data;
        notifyDataSetChanged();
    }
    public interface ItemClickListener {
        void onItemClick(int position, String itemId,String classId);
    }
}
