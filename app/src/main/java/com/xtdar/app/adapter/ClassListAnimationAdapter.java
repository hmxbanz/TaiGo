package com.xtdar.app.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NLog;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.response.ClassListResponse;
import com.xtdar.app.server.response.GameListResponse;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hmxbanz on 2017/3/8.
 */

public class ClassListAnimationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private List<GameListResponse.DataBean> listItems;
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

    public List<RecyclerView.ViewHolder> getViewList() {
        return viewList;
    }

    private List<RecyclerView.ViewHolder> viewList=new ArrayList<>();                    //View对象集合

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

    public ClassListAnimationAdapter( Context c){

        this.context=c;
        this.layoutInflater=LayoutInflater.from(c);
        glideImageLoader=new GlideImageLoader();
    }

    public void setListItems(List<GameListResponse.DataBean> l)
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
            View v = layoutInflater.inflate(R.layout.listitem_animation_item, parent, false);
            return new DataHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        //if(getItemViewType(position) == TYPE_FOOTER) return;

        final int pos = getRealPosition(holder);
        if(position==listItems.size())return;

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

        final GameListResponse.DataBean listItem = listItems.get(position);
        if(holder instanceof DataHolder) {
            DataHolder dataDolder=(DataHolder)holder;
            dataDolder.title.setText(listItem.getGame_name());
            if(TextUtils.isEmpty(listItem.getLoad_size()))
            dataDolder.className.setText(listItem.getGame_title());
            else
            dataDolder.className.setText(listItem.getGame_title()+"("+listItem.getLoad_size()+")");
            glideImageLoader.displayImage(context, XtdConst.IMGURI+listItem.getGame_img(),dataDolder.imageView);
            //Glide.with(context).load(listItem.getAvator()).asBitmap().into(holder.imageView);
            //holder.imageView.setImageResource(listItem.getImgResource());
            if(mListener == null) return;
            dataDolder.btnStartGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(v,position,listItem);
                }
            });
            if(listItem.is_download())
                dataDolder.btnStartGame.setText("进 入");
            else
                dataDolder.btnStartGame.setText("加 载");


            boolean foundPosition=false;
            for (int i=0;i<viewList.size();i++) {
                ClassListAnimationAdapter.DataHolder holder2= (ClassListAnimationAdapter.DataHolder) viewList.get(i);
                if(position==holder2.getLayoutPosition())
                  foundPosition=true;
            }
           // if(foundPosition==false) {
                viewList.add(position, dataDolder);
           // }



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
        void onItemClick(View v, int position, GameListResponse.DataBean bean);
    }
    class HeaderHolder extends RecyclerView.ViewHolder  {
        private Banner banner;

        public HeaderHolder(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

    }

    public class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView title;
        private TextView className;
        private ImageView imageView;
        private View listLayoutView;

       private TextView btnStartGame;

        public DataHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.txt_title);
            className = (TextView) itemView.findViewById(R.id.txt_class_name);
            imageView = (ImageView) itemView.findViewById(R.id.list_item_icon);
            listLayoutView = itemView.findViewById(R.id.list_item_layout);
            btnStartGame = (TextView) itemView.findViewById(R.id.btn_start_game);
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
        public TextView getTitle() {
            return title;
        }
        public void setTitle(TextView title) {
            this.title = title;
        }
        public TextView getBtnStartGame() {
            return btnStartGame;
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
