package com.xtdar.app.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.xtdar.app.R;
import com.xtdar.app.adapter.HistoryAdapter;
import com.xtdar.app.model.UserList;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class DownloadVideoFragment extends Fragment {
    public static DownloadVideoFragment instance = null;
    private View view;
    private HistoryAdapter mHistoryAdapter;
    private ListView mListView;


    public static DownloadVideoFragment getInstance() {
        if (instance == null) {
            instance = new DownloadVideoFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favor_video, null);
        initViews();

        return view;
    }

    private void initViews() {
        mHistoryAdapter = new HistoryAdapter(getActivity());
        mHistoryAdapter.setmList(UserList.getData());
        mHistoryAdapter.setOnItemButtonClick(new HistoryAdapter.OnItemButtonClick() {
            @Override
            public boolean onButtonClick(int position, View view, int status) {
                Toast.makeText(getActivity(),"删除视频"+String.valueOf(position),Toast.LENGTH_LONG).show();
                return false;
            }
        });
        mListView= (ListView) view.findViewById(R.id.listview_favor_video);
       mListView.setAdapter(mHistoryAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //BroadcastManager.getInstance(getActivity()).destroy(SealConst.CHANGEINFO);
    }

}
