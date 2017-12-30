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
import com.xtdar.app.server.response.CommentResponse;
import com.xtdar.app.server.response.LotteryDataResponse;
import com.xtdar.app.server.response.LotteryPrizeResponse;
import com.xtdar.app.view.widget.SelectableRoundedImageView;

import java.util.List;

/**
 * Created by hmxbanz on 2017/3/8.
 */

public class LotteryPrizeAdapter extends RecyclerView.Adapter<LotteryPrizeAdapter.DataHolder>  {

    public void setListItems(List<LotteryPrizeResponse.DataBean> listItems) {
        this.listItems = listItems;
    }

    private List<LotteryPrizeResponse.DataBean> listItems;
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

    public LotteryPrizeAdapter(Context c){
        this.context=c;
        this.layoutInflater=LayoutInflater.from(c);
        glideImageLoader=new GlideImageLoader();
    }

    public LotteryPrizeAdapter(List<LotteryPrizeResponse.DataBean> l, Context c){
        this.listItems=l;
        this.context=c;
        this.layoutInflater=LayoutInflater.from(c);
        glideImageLoader=new GlideImageLoader();
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER)
            return new DataHolder(mHeaderView);
        else if(mFooterView != null &&viewType == TYPE_FOOTER)
            return new DataHolder(mFooterView);
        else {
            View v = layoutInflater.inflate(R.layout.listitem_lottery_prize, parent, false);
            return new DataHolder(v);
        }
    }
    @Override
    public void onBindViewHolder(DataHolder holder, final int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;
        if(getItemViewType(position) == TYPE_FOOTER) return;

        final int pos = getRealPosition(holder);
        final LotteryPrizeResponse.DataBean listItem = listItems.get(pos);
        if(holder instanceof DataHolder) {
            if(listItem.getDraw_rolls_type()=="2")
                holder.txtBtnPrize.setText("立即领奖");
            else
                holder.txtBtnPrize.setText("已兑换");
            if(listItem.getIs_handler()=="1")
                glideImageLoader.displayImage(context, XtdConst.IMGURI+listItem.getHis_img_2(),holder.imgPrize);
            else
                glideImageLoader.displayImage(context, XtdConst.IMGURI+listItem.getHis_img(),holder.imgPrize);

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
        void onItemClick(int position, String itemId, String classId);
    }
    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView txtBtnPrize;
        private ImageView imgPrize;
        private View listLayoutView;

        public DataHolder(View itemView) {
            super(itemView);
            txtBtnPrize = (TextView) itemView.findViewById(R.id.btn_submit);
            imgPrize = (ImageView) itemView.findViewById(R.id.img_prize);
            listLayoutView = itemView.findViewById(R.id.list_item_layout);
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
