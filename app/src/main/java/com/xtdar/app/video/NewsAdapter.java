package com.xtdar.app.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.CommonTools;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.response.NewsResponse;

import java.util.List;

/**
 * Created by guoshuyu on 2017/1/9.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.DataHolder> {
    private final GlideImageLoader glideImageLoader;
    private ItemClickListener mListener;
    public void setOnItemClickListener(ItemClickListener listener) {
        mListener = listener;
    }

    private List<NewsResponse.DataBean> dataList = null;
    private Context context = null;

    public NewsAdapter(Context context, List<NewsResponse.DataBean> dataList) {
        this.dataList = dataList;
        this.context = context;
        glideImageLoader = new GlideImageLoader();
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_news_item, parent, false);
        final DataHolder holder = new DataHolder(context, v);
        return holder;

    }

    @Override
    public void onBindViewHolder(final DataHolder holder,  final int position) {
        final NewsResponse.DataBean listItem = dataList.get(position);
        holder.txtTitle.setText(listItem.getTitle());
        holder.txtTime.setText(CommonTools.FormatTime(listItem.getPost_time()));
        holder.txtCount.setText(listItem.getRead_count());
        holder.txtThumbUp.setText(listItem.getLike_count());

        glideImageLoader.displayImage(context, XtdConst.IMGURI+listItem.getHead_img(),holder.imageView);

        if(mListener == null) return;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(position,listItem.getRecommend_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    public void setListData(List<NewsResponse.DataBean> data) {
        dataList = data;
        notifyDataSetChanged();
    }
    public interface ItemClickListener {
        void onItemClick(int position, String itemId);
    }

    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView txtTitle;
        private TextView txtTime;
        private TextView txtCount;
        private TextView txtThumbUp;
        private ImageView imageView;

        public DataHolder(Context context, View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            txtTime = (TextView) itemView.findViewById(R.id.txt_time);
            txtCount = (TextView) itemView.findViewById(R.id.txt_count);
            txtThumbUp = (TextView) itemView.findViewById(R.id.txt_thumb_up);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {

            }
        }
    }
}
