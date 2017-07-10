package com.xtdar.app.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.response.ClassListResponse;
import com.xtdar.app.server.response.GameListResponse;
import com.xtdar.app.server.response.ShowResponse;
import com.xtdar.app.video.RecyclerItemNormalHolder;
import com.xtdar.app.video.RecyclerItemNormalHolder2;
import com.xtdar.app.video.RecyclerNormalAdapter;
import com.youth.banner.Banner;

import java.util.List;

/**
 * Created by hmxbanz on 2017/3/8.
 */


    public class ClassListNuAdapter extends RecyclerView.Adapter {
        private final static String TAG = "RecyclerBaseAdapter";
        private ItemClickListener mListener;
        public void setOnItemClickListener(ItemClickListener listener) {
            mListener = listener;
        }

        private List<ClassListResponse.DataBean> itemDataList = null;
        private Context context = null;

        public ClassListNuAdapter(Context context, List<ClassListResponse.DataBean> itemDataList) {
            this.itemDataList = itemDataList;
            this.context = context;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.list_video_item_normal, parent, false);
            final RecyclerView.ViewHolder holder = new RecyclerItemNormalHolder2(context, v);
            return holder;

        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder,  final int position) {
            RecyclerItemNormalHolder2 recyclerItemViewHolder = (RecyclerItemNormalHolder2) holder;
            recyclerItemViewHolder.setRecyclerBaseAdapter(this);
            recyclerItemViewHolder.onBind(position, itemDataList.get(position));
            final ClassListResponse.DataBean listItem = itemDataList.get(position);
            if(mListener == null) return;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position,listItem.getItem_id(),listItem.getClass_id());
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

        public void setListData(List<ClassListResponse.DataBean> data) {
            itemDataList = data;
            notifyDataSetChanged();
        }
        public interface ItemClickListener {
            void onItemClick(int position, String itemId,String classId);
        }
    }
