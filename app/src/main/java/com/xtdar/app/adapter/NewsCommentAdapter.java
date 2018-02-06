package com.xtdar.app.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LayoutDirection;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.response.CommentResponse;
import com.xtdar.app.view.widget.SelectableRoundedImageView;

import java.util.List;

/**
 * Created by hmxbanz on 2017/3/8.
 */

public class NewsCommentAdapter extends RecyclerView.Adapter<NewsCommentAdapter.DataHolder>  {

    public void setListItems(List<CommentResponse.DataBean> listItems) {
        this.listItems = listItems;
    }

    private List<CommentResponse.DataBean> listItems;
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

    public NewsCommentAdapter(Context c){
        this.context=c;
        this.layoutInflater=LayoutInflater.from(c);
        glideImageLoader=new GlideImageLoader();
    }

    public NewsCommentAdapter(List<CommentResponse.DataBean> l, Context c){
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
            View v = layoutInflater.inflate(R.layout.listitem_news_comment, parent, false);
            return new DataHolder(v);
        }
    }
    @Override
    public void onBindViewHolder(final DataHolder holder, final int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;
        if(getItemViewType(position) == TYPE_FOOTER) return;

        final int pos = getRealPosition(holder);
        final CommentResponse.DataBean listItem = listItems.get(pos);
        if(holder instanceof DataHolder) {
            holder.nickName.setText(listItem.getNick_name());
            holder.content.setText(listItem.getComment());
            List<CommentResponse.DataBean.ReplyListBean> listReply = listItem.getReplyList();
            holder.layout_reply.removeAllViews();
            if(listReply.size()>0)
            {
                for (CommentResponse.DataBean.ReplyListBean bean:listReply ) {
                    //实例化一个LinearLayout
                    LinearLayout linearLayout=new LinearLayout(context);
                    //设置LinearLayout属性(宽和高)
                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //设置边距
                    layoutParams.setLayoutDirection(LayoutDirection.LTR);
                    //设置边距
                    layoutParams.setMargins(0, 0, 0, 0);
                    //将以上的属性赋给LinearLayout
                    linearLayout.setLayoutParams(layoutParams);
                    //实例化一个TextView
                    TextView tvNickName = new TextView(context);
                    //设置宽高以及权重
                    LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //设置textview垂直居中
                    tvParams.gravity = Gravity.LEFT;
                    tvNickName.setLayoutParams(tvParams);
                    tvNickName.setTextSize(10);
                    tvNickName.setTextColor(context.getResources().getColor(R.color.deepskyblue));
                    tvNickName.setText(bean.getNick_name());
                    //实例化一个TextView
                    TextView tvComment = new TextView(context);
                    //设置宽高以及权重
                    LinearLayout.LayoutParams tvCommentParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //设置textview垂直居中
                    tvParams.gravity = Gravity.LEFT;
                    tvComment.setLayoutParams(tvParams);
                    tvComment.setTextSize(10);
                    tvComment.setText(":"+bean.getReply());
                    if(bean.getAt_nick_name()==null){
                        linearLayout.addView(tvNickName);
                    }
                    else
                    {
                        //实例化一个TextView
                        TextView tvCommentTo = new TextView(context);
                        //设置宽高以及权重
                        LinearLayout.LayoutParams tvCommentToParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        //设置textview垂直居中
                        tvCommentToParams.gravity = Gravity.LEFT;
                        tvCommentTo.setLayoutParams(tvCommentToParams);
                        tvCommentTo.setTextSize(10);
                        tvCommentTo.setText(" 回复");
                        //实例化一个TextView
                        TextView tvCommentToNickName = new TextView(context);
                        //设置宽高以及权重
                        LinearLayout.LayoutParams tvCommentToNickNameParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        //设置textview垂直居中
                        tvCommentToNickNameParams.gravity = Gravity.LEFT;
                        tvCommentToNickName.setTextColor(context.getResources().getColor(R.color.deepskyblue));
                        tvCommentToNickName.setLayoutParams(tvCommentToParams);
                        tvCommentToNickName.setTextSize(10);
                        tvCommentToNickName.setText(bean.getAt_nick_name().toString());

                        linearLayout.addView(tvNickName);
                        linearLayout.addView(tvCommentTo);
                        linearLayout.addView(tvCommentToNickName);
                    }

                    linearLayout.addView(tvComment);
                    holder.layout_reply.addView(linearLayout);
                }

                holder.layout_reply.setVisibility(View.VISIBLE);
                holder.txt_reply_count.setVisibility(View.VISIBLE);
                holder.txt_reply_count.setText("共"+listItem.getReply_count()+"条回复");
            }

            holder.createdate.setText(listItem.getCom_date());
            if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Drawable compoundThumbUpOn=context.getDrawable(R.drawable.icon_thumbsup).mutate();
                if(listItem.getIs_like()!=null)
                {
                    DrawableCompat.setTint(compoundThumbUpOn, context.getResources().getColor(R.color.salmon));
                }
                                    else
                {
                    DrawableCompat.setTint(compoundThumbUpOn, context.getResources().getColor(R.color.gray));
                }

                compoundThumbUpOn.setBounds(0, 0, 30, 30);
                holder.txtThumbUp.setCompoundDrawables(compoundThumbUpOn,null , null, null);
            }
            holder.txtThumbUp.setText(listItem.getLike_count());
            glideImageLoader.displayImage(context, XtdConst.IMGURI+listItem.getImg_path(),holder.avatar);
            if(mListener == null) return;
            final TextView t=holder.txtThumbUp;
            holder.txtThumbUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onTxtThumbUpClick(position,listItem,t);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.layout_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClick(position,listItem);
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
        void onItemClick(int position, CommentResponse.DataBean bean);
        void onTxtThumbUpClick(int position, CommentResponse.DataBean bean,TextView t);
    }
    class DataHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView nickName;
        private TextView content;
        private TextView createdate;
        private LinearLayout layout_reply;
        private TextView txt_reply_count;
        private TextView txtThumbUp;
        private SelectableRoundedImageView avatar;
        private View listLayoutView;

        public DataHolder(View itemView) {
            super(itemView);
            nickName = (TextView) itemView.findViewById(R.id.nickname);
            content = (TextView) itemView.findViewById(R.id.content);
            createdate = (TextView) itemView.findViewById(R.id.createdate);
            layout_reply = (LinearLayout) itemView.findViewById(R.id.layout_reply);
            txt_reply_count = (TextView) itemView.findViewById(R.id.txt_reply_count);
            txtThumbUp = (TextView) itemView.findViewById(R.id.txt_thumb_up);
            avatar = (SelectableRoundedImageView) itemView.findViewById(R.id.avatar);
            listLayoutView = itemView.findViewById(R.id.list_item_layout);

        }

        public View getListLayoutView() {
            return listLayoutView;
        }
        public void setListLayoutView(View listLayoutView) {
            this.listLayoutView = listLayoutView;
        }
        public ImageView getAvatar() {
            return avatar;
        }
        public void setAvatar(SelectableRoundedImageView avatar) {
            this.avatar = avatar;
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
                case R.id.layout_reply:
                    //context.startActivity(new Intent(context, ReplyDetailActivity.class));
                    break;
            }
        }
    }
}
