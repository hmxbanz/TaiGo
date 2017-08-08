package com.xtdar.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.model.UserList;
import com.xtdar.app.server.response.RecommendResponse;
import com.xtdar.app.server.response.TaobaoResponse;
import com.xtdar.app.view.activity.DetailActivity;
import com.xtdar.app.view.activity.DeviceActivity;
import com.xtdar.app.view.activity.Main2Activity;
import com.xtdar.app.view.activity.ShopMoreActivity;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mob.MobSDK.getContext;

/**
 * Created by hugeterry(http://hugeterry.cn)
 * Date: 17/1/28 22:31
 */

public class HomeRecommendAdapter extends RecyclerView.Adapter<HomeRecommendAdapter.DataHolder> {
    private List<TaobaoResponse.DataBean.DeviceTypeListBean> listItems;
    private LayoutInflater layoutInflater;
    private  final int TYPE_HEADER = 0;
    private  final int TYPE_NORMAL = 1;
    private  final int TYPE_FOOTER = 2;
    private View mHeaderView;
    private View mFooterView;
    private RecyclerViewAdapter.ItemClickListener mListener;
    private RecyclerView mRecyclerView;
    private GlideImageLoader glideImageLoader;
    private Context context;

    public void setOnItemClickListener(RecyclerViewAdapter.ItemClickListener listener) {
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

    public HomeRecommendAdapter(List<TaobaoResponse.DataBean.DeviceTypeListBean> l, Context c){
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
            View v = layoutInflater.inflate(R.layout.listitem_recommend_title, parent, false);
            return new DataHolder(v);
        }
    }
    @Override
    public void onBindViewHolder(DataHolder holder, final int position) {

        if(getItemViewType(position) == TYPE_FOOTER) return;

        final int pos = getRealPosition(holder);
        final TaobaoResponse.DataBean.DeviceTypeListBean listItem = listItems.get(position);
        if(holder instanceof DataHolder) {
            holder.txtTitle.setText(listItem.getDevice_type_name());
            holder.listLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent moreIntent = new Intent(context, ShopMoreActivity.class);
                    moreIntent.putExtra("deviceTtypeId", listItem.getDevice_type_id());
                    context.startActivity(moreIntent);
                }
            });
            //装入item
            List<TaobaoResponse.DataBean.DeviceTypeListBean.DeviceListBean> items = listItem.getDevice_list();
            GridLayoutManager gridLayoutManager=new GridLayoutManager(context,2);
            holder.recyclerView.setLayoutManager(gridLayoutManager);
            HomeRecommendItemAdapter dataAdapter = new HomeRecommendItemAdapter(items, context);
            dataAdapter.setOnItemClickListener(new HomeRecommendItemAdapter.ItemClickListener() {
                @Override
                public void onItemClick(int position, String itemId,String classId) {
                    //DetailActivity.StartActivity(context,itemId,classId);
                    LoadDialog.show(context);
                    //商品详情page
                    AlibcBasePage detailPage = new AlibcDetailPage(itemId);
                    //提供给三方传递配置参数
                    Map<String, String> exParams = new HashMap<>();
                    exParams.put(AlibcConstants.ISV_CODE, "appisvcode");
                    //设置页面打开方式
                    AlibcShowParams showParams = new AlibcShowParams(OpenType.Native, false);

                    //使用百川sdk提供默认的Activity打开detail
                    AlibcTrade.show((Main2Activity)context, detailPage, showParams, null, null ,
                            new AlibcTradeCallback() {
                                @Override
                                public void onTradeSuccess(TradeResult tradeResult) {
                                    //打开电商组件，用户操作中成功信息回调。tradeResult：成功信息（结果类型：加购，支付；支付结果）
                                    LoadDialog.dismiss(context);
                                }

                                @Override
                                public void onFailure(int code, String msg) {
                                    //打开电商组件，用户操作中错误信息回调。code：错误码；msg：错误信息
                                }
                            });
                    //NToast.shortToast(context,"暂无库存，敬请关注！");
                }
            });
            holder.recyclerView.setAdapter(dataAdapter);
            //glideImageLoader.displayImage(context,listItem.getAvator(),holder.imageView);
            //holder.imageView.setImageResource(listItem.getImgResource());
            if(mListener == null) return;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position,listItem.getDevice_type_id());
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
        private TextView txtTitle;
        private RecyclerView recyclerView;
        private View listLayoutView;

        public DataHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txt_title);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recycler);
            listLayoutView = itemView.findViewById(R.id.list_item_layout);
        }

        public View getListLayoutView() {
            return listLayoutView;
        }
        public void setListLayoutView(View listLayoutView) {
            this.listLayoutView = listLayoutView;
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }

        public void setTxtTitle(TextView txtTitle) {
            this.txtTitle = txtTitle;
        }

        public RecyclerView getRecyclerView() {
            return recyclerView;
        }

        public void setRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
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