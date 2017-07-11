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

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class FavorVideoFragment extends Fragment{
    public static FavorVideoFragment instance = null;
    private View view;
    private ListView mListView;
    private FavorVideoPresenter favorVideoPresenter;


    public static FavorVideoFragment getInstance() {
        if (instance == null) {
            instance = new FavorVideoFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favor_video, null);
        initViews();
        favorVideoPresenter = new FavorVideoPresenter(getContext());
        favorVideoPresenter.init(mListView);
        return view;
    }

    private void initViews() {
        mListView= (ListView) view.findViewById(R.id.listview_favor_video);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //BroadcastManager.getInstance(getActivity()).destroy(SealConst.CHANGEINFO);
    }


}
