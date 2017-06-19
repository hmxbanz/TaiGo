package com.xtdar.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

import com.xtdar.app.R;
import com.xtdar.app.model.User;
import com.xtdar.app.view.widget.CircleImageView;

public class DynamicAdapter extends android.widget.BaseAdapter {

    private Context context;
    private List<User> data;
    private LayoutInflater mInflater;

    public void setData(List<User> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public DynamicAdapter(Context context, List<User> data) {
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public User getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_dynamic, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        User item = getItem(position);

        holder.content.setText(item.getNickName());
        holder.username.setText(item.getNickName());
        holder.createTime.setText(item.getNameSpelling());
        setImage(context, holder.avatar, item.getAvator() == null ? null : item.getAvator());

//        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
//        List<EvaluationPic> imageDetails = item.getAttachments();
//        if (imageDetails != null) {
//            for (EvaluationPic imageDetail : imageDetails) {
//                ImageInfo info = new ImageInfo();
//                info.setThumbnailUrl(imageDetail.smallImageUrl);
//                info.setBigImageUrl(imageDetail.imageUrl);
//                imageInfo.add(info);
//            }
//        }

        ArrayList<ImageInfo> imageInfo=new ArrayList<>();
        ImageInfo img=new ImageInfo();
        img.setImageViewWidth(5);
        img.setImageViewHeight(5);
        img.setImageViewX(200);
        img.setImageViewY(200);
        img.setBigImageUrl("http://www.xtdar.com//Images/User/2017/02/02/2017020223391971_b.jpg");
        img.setThumbnailUrl("http://www.xtdar.com/Images/User/2017/02/02/2017020223391971_s.jpg");

        imageInfo.add(img);

        holder.nineGrid.setAdapter(new NineGridViewClickAdapter(context, imageInfo));

//        if (item.evaluatereplys == null) {
//            holder.comments.setVisibility(View.GONE);
//        } else {
//            holder.comments.setVisibility(View.VISIBLE);
//            holder.comments.setAdapter(new CommentsAdapter(context, item.getEvaluatereplys()));
//        }
        return convertView;
    }

    private void setImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)//
                .placeholder(R.drawable.ic_default_color)//
                .error(R.drawable.ic_default_color)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView);
    }

    class ViewHolder implements View.OnClickListener {

        private TextView content;
        private NineGridView nineGrid;
        private TextView username;
        private TextView createTime;
        private RatingBar grade;
        private CircleImageView avatar;
        private ListView comments;

        private PopupWindow window;
        private PopupWindow editWindow;
        private View rootView;

        public ViewHolder(View convertView) {
            rootView = convertView;
            content = (TextView) convertView.findViewById(R.id.tv_content);
            username = (TextView) convertView.findViewById(R.id.tv_username);
            nineGrid = (NineGridView) convertView.findViewById(R.id.nineGrid);
            createTime = (TextView) convertView.findViewById(R.id.tv_createTime);
            avatar = (CircleImageView) convertView.findViewById(R.id.avatar);
        }

           @Override
            public void onClick(View v) {
                switch (v.getId()) {
//                    case R.id.favour:
//                        Toast.makeText(context, "赞+1", Toast.LENGTH_SHORT).show();
//                        if (window != null) window.dismiss();
//                        break;
//                    case R.id.comment:
//                        View editView = mInflater.inflate(R.layout.replay_input, null);
//                        editWindow = new PopupWindow(editView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                        editWindow.setOutsideTouchable(true);
//                        editWindow.setFocusable(true);
//                        editWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
//
//                        EditText replyEdit = (EditText) editView.findViewById(R.id.reply);
//                        replyEdit.setFocusable(true);
//                        replyEdit.requestFocus();
//                        // 以下两句不能颠倒
//                        editWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//                        editWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                        editWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
//                        // 显示键盘
//                        final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//
//                        editWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                            @Override
//                            public void onDismiss() {
//                                if (imm.isActive()) imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);
//                            }
//                        });
//                        if (window != null) window.dismiss();
//                        break;
                }
            }
        }
    }
