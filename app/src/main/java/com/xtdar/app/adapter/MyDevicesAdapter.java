package com.xtdar.app.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.response.MyDevicesResponse;
import com.youth.banner.Banner;

import java.util.List;

/**
 * Created by hmxbanz on 2017/3/8.
 */

public class MyDevicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private List<MyDevicesResponse.DataBean> listItems;
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
    private List<String> adImages;

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

    public MyDevicesAdapter(Context c){

        this.context=c;
        this.layoutInflater=LayoutInflater.from(c);
        glideImageLoader=new GlideImageLoader();
    }

    public void setListItems(List<MyDevicesResponse.DataBean> l)
    {
        this.listItems=l;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER)
        {
            return new HeaderHolder(mHeaderView);
        }
        else if(mFooterView != null &&viewType == TYPE_FOOTER)
        {
            return new DataHolder(mFooterView);
        }
        else {
            View v = layoutInflater.inflate(R.layout.listitem_device_item, parent, false);
            return new DataHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //if(getItemViewType(position) == TYPE_FOOTER) return;

        final int pos = getRealPosition(holder);
        //if(position==listItems.size())return;

        if(holder instanceof HeaderHolder) {
            HeaderHolder headerHolder=(HeaderHolder)holder;
//            String[] urls = context.getResources().getStringArray(R.array.url);
//            //String[] tips = getResources().getStringArray(R.array.deviceName);
//            List list = Arrays.asList(urls);
//            ArrayList images = new ArrayList(list);
            //简单使用
            //banner.setOnBannerListener(this);
            headerHolder.banner.setImages(adImages);//设置图片集合
            headerHolder.banner.setImageLoader(new GlideImageLoader());//设置图片加载器
            headerHolder.banner.start();
        }
        if(getItemViewType(position) == TYPE_HEADER) return;
        final MyDevicesResponse.DataBean listItem = listItems.get(position);
        if(holder instanceof DataHolder) {
            DataHolder dataDolder=(DataHolder)holder;
            //dataDolder.className.setText(listItem.getChapter_name());
            glideImageLoader.displayImage(context, XtdConst.IMGURI+listItem.getDevice_img(),dataDolder.imageView);
            //Glide.with(context).load(listItem.getAvator()).asBitmap().into(holder.imageView);
            //holder.imageView.setImageResource(listItem.getImgResource());
            dataDolder.statueIcon.setVisibility(View.GONE);
            if(listItem.getStatus()==1){dataDolder.statueIcon.setVisibility(View.VISIBLE);
            dataDolder.statueIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_not_connected));}
            else if(listItem.getStatus()==2){dataDolder.statueIcon.setVisibility(View.VISIBLE);
                dataDolder.statueIcon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_connected));}



            if(mListener == null) return;
            dataDolder.listLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position,listItem);
                }
            });
            dataDolder.layoutUnbind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.unBindBtn(position,listItem);
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

    public void setAdImages(List<String> images) {
        this.adImages=images;
    }

    public interface ItemClickListener {
        void onItemClick(int position, MyDevicesResponse.DataBean item);

        void unBindBtn(int position, MyDevicesResponse.DataBean listItem);
    }
    class HeaderHolder extends RecyclerView.ViewHolder  {
        private Banner banner;

        public HeaderHolder(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

    }

    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ImageView imageView;
        private ImageView statueIcon;
        private RelativeLayout listLayoutView;
        private LinearLayout layoutUnbind;

        public DataHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.list_item_icon);
            statueIcon = (ImageView) itemView.findViewById(R.id.statue_icon);
            listLayoutView = (RelativeLayout)itemView.findViewById(R.id.list_item_layout);
            layoutUnbind= (LinearLayout) itemView.findViewById(R.id.layout_unbind);
        }

        public View getListLayoutView() {
            return listLayoutView;
        }
        public void setListLayoutView(RelativeLayout listLayoutView) {
            this.listLayoutView = listLayoutView;
        }
        public ImageView getImageView() {
            return imageView;
        }
        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
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
