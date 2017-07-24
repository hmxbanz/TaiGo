package com.xtdar.app.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xtdar.app.R;
import com.xtdar.app.presenter.FavorVideoPresenter;
import com.xtdar.app.presenter.SysMsgPresenter;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class SysMsgFragment extends Fragment{
    public static SysMsgFragment instance = null;
    private View view;
    private ListView mListView;
    private SysMsgPresenter presenter;


    public static SysMsgFragment getInstance() {
        if (instance == null) {
            instance = new SysMsgFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sys_msg, null);
        initViews();
        presenter = new SysMsgPresenter(getContext());
        presenter.init(mListView);
        return view;
    }

    private void initViews() {
        mListView= (ListView) view.findViewById(R.id.listview_sys_msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //BroadcastManager.getInstance(getActivity()).destroy(SealConst.CHANGEINFO);
    }


}
