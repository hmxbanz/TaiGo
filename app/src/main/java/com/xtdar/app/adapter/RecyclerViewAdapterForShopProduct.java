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
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.model.User;

import java.util.List;

/**
 * Created by hmxbanz on 2017/3/8.
 */

public class RecyclerViewAdapterForShopProduct extends RecyclerView.Adapter<RecyclerViewAdapterForShopProduct.DataHolder>  {
    private List<User> listItems;
    private LayoutInflater layoutInflater;
    private  final int TYPE_HEADER = 0;
    private  final int TYPE_NORMAL = 1;
    private  final int TYPE_FOOTER = 2;
    private View mHeaderView;
    private View mFooterView;
    private ItemClickListener mListener;
    private RecyclerView mRecyclerView;
    private GlideImageLoader glideImageLoader;
    private Context context;

    public void setOnItemClickListener(ItemClickListener listener) {
        mListener = listener;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);//告知Adapter首位置项变动了
    }

    public View getFooterView() {
        return mFooterView;
    }
    public void setFooterView(View headerView) {
        mFooterView = headerView;
        notifyItemInserted(0);//告知Adapter首位置项变动了
    }

    public View getHeaderView() {
        return mHeaderView;
    }

public RecyclerViewAdapterForShopProduct(List<User> l, Context c){
    this.listItems=l;
    this.context=c;
    this.layoutInflater=LayoutInflater.from(c);
    glideImageLoader=new GlideImageLoader();
}

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER)
        {
            return new DataHolder(mHeaderView);
        }
        else if(mFooterView != null &&viewType == TYPE_FOOTER)
        {
          return new DataHolder(mFooterView);
        }
        else {
            View v = layoutInflater.inflate(R.layout.recyclerview_listitem_product, parent, false);
            return new DataHolder(v);
        }
    }
    @Override
    public void onBindViewHolder(DataHolder holder, final int position) {

        if(getItemViewType(position) == TYPE_FOOTER) return;

        final int pos = getRealPosition(holder);
        final User listItem = listItems.get(position);
        if(holder instanceof DataHolder) {
            holder.nickName.setText(listItem.getNickName());
            glideImageLoader.displayImage(context,listItem.getAvator(),holder.imageView);
            //Glide.with(context).load(listItem.getAvator()).asBitmap().into(holder.imageView);
            //holder.imageView.setImageResource(listItem.getImgResource());
            if(mListener == null) return;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position,listItem.getNickName());
                }
            });
        }


    }
    @Override
    public int getItemCount() {
        int count = (listItems == null ? 0 : listItems.size());
        if (mHeaderView != null)   count++;
        if (mFooterView != null)   count++;
        return count;
    }
    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return TYPE_HEADER;
        } else if (isFooterView(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView;
            }
            ifGridLayoutManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ifGridLayoutManager() {
        if (mRecyclerView == null) return;
        final RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager.SpanSizeLookup originalSpanSizeLookup = ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            ((GridLayoutManager) layoutManager).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeaderView(position) || isFooterView(position)) ?
                            ((GridLayoutManager) layoutManager).getSpanCount() :
                            1;
                }
            });
        }
    }
    private boolean isHeaderView(int position) {
        return (mHeaderView != null) && (position == 0);
    }

    private boolean isFooterView(int position) {
        return (mFooterView != null) && (position == getItemCount() - 1);
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }
    public interface ItemClickListener {
        void onItemClick(int position, String data);
    }
    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView nickName;
        private ImageView imageView;
        private View listLayoutView;

        public DataHolder(View itemView) {
            super(itemView);
            nickName = (TextView) itemView.findViewById(R.id.list_item_text);
            imageView = (ImageView) itemView.findViewById(R.id.list_item_icon);
            listLayoutView = itemView.findViewById(R.id.list_item_layout);
        }

        public View getListLayoutView() {
            return listLayoutView;
        }
        public void setListLayoutView(View listLayoutView) {
            this.listLayoutView = listLayoutView;
        }
        public ImageView getImageView() {
            return imageView;
        }
        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }
        public TextView getNickName() {
            return nickName;
        }
        public void setNickName(TextView nickName) {
            this.nickName = nickName;
        }

        @Override
        public void onClick(View v) {
            //mListener.onItemClick(getAdapterPosition(),"a");
            switch (v.getId())
            {
                case R.id.list_item_layout:


            }
        }
    }
}
