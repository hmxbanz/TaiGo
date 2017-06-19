package com.xtdar.app.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.ninegrid.NineGridView;
import com.xtdar.app.adapter.DynamicAdapter;
import com.xtdar.app.model.User;
import com.xtdar.app.model.UserList;

import java.util.ArrayList;

import com.xtdar.app.R;


public class LiftShareFragment extends Fragment {
    private String mTitle;
    private static final String ARG_TITLE = "title";

    private ListView listView;

    private DynamicAdapter mAdapter;
    private ArrayList<User> data;
    private int page = 1;

    public static LiftShareFragment getInstance(String title) {
        LiftShareFragment fra = new LiftShareFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        fra.setArguments(bundle);
        return fra;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mTitle = bundle.getString(ARG_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dynamic, container, false);
        listView= (ListView) v.findViewById(R.id.listView);
        View emptyView = View.inflate(getContext(), R.layout.item_empty, null);
        getActivity().addContentView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
        NineGridView.setImageLoader(new GlideImageLoader());
        //mAdapter = new DynamicAdapter(getActivity(), new ArrayList<User>());
        mAdapter = new DynamicAdapter(getActivity(), UserList.getData());
        listView.setAdapter(mAdapter);

        return v;
    }

    /** Glide 加载 */
    private class GlideImageLoader implements NineGridView.ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context).load(url)//
                    .placeholder(R.drawable.ic_default_color)//
                    .error(R.drawable.ic_default_color)//
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//
                    .into(imageView);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

}