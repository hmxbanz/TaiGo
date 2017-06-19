package com.xtdar.app.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.xtdar.app.model.UserList;
import com.xtdar.app.view.activity.DetailActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.xtdar.app.R;
import com.xtdar.app.adapter.RecyclerViewAdapter;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class AlubmFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener {
    public static AlubmFragment instance = null;
    public static List<?> images=new ArrayList<>();
    private RecyclerView recycleView;
    private RecyclerViewAdapter dataAdapter;
    public ScrollView scrollView;
    private View mView;
    private GridLayoutManager gridLayoutManager;

    public static AlubmFragment getInstance() {
        if (instance == null) {
            instance = new AlubmFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_album, null);
        initViews();
//        initData();
        return mView;
    }

    private void initViews() {
        String[] urls = getResources().getStringArray(R.array.url);
        //String[] tips = getResources().getStringArray(R.array.title);
        List list = Arrays.asList(urls);
        images = new ArrayList(list);

        recycleView= (RecyclerView) mView.findViewById(R.id.recyclerView);
        gridLayoutManager=new GridLayoutManager(getContext(),3);
        recycleView.setLayoutManager(gridLayoutManager);
        dataAdapter = new RecyclerViewAdapter(UserList.getData(), getContext());
        dataAdapter.setFooterView(LayoutInflater.from(getContext()).inflate(R.layout.recyclerview_footer,null));
        recycleView.setAdapter(dataAdapter);
        recycleView.setNestedScrollingEnabled(false);

        //recycleView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.HORIZONTAL));
        dataAdapter.setOnItemClickListener(this);
        //scrollView=(ScrollView) mView.findViewById(R.id.scrollview);
        //scrollView.smoothScrollTo(0, 0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //BroadcastManager.getInstance(getActivity()).destroy(SealConst.CHANGEINFO);
    }

    @Override
    public void onItemClick(int position, String data) {
        getActivity().startActivity(new Intent(getActivity(), DetailActivity.class));
        Toast.makeText(getContext(),"你点击了位置："+String.valueOf(position)+"-标题："+data,Toast.LENGTH_SHORT).show();
    }


}
