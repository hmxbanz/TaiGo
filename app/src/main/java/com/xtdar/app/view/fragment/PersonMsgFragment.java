package com.xtdar.app.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xtdar.app.R;
import com.xtdar.app.presenter.PersonMsgPresenter;
import com.xtdar.app.presenter.SysMsgPresenter;

/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class PersonMsgFragment extends Fragment{
    public static PersonMsgFragment instance = null;
    private View view;
    private ListView mListView;
    private PersonMsgPresenter presenter;


    public static PersonMsgFragment getInstance() {
        if (instance == null) {
            instance = new PersonMsgFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person_msg, null);
        initViews();
        presenter = new PersonMsgPresenter(getContext());
        presenter.init(mListView);
        return view;
    }

    private void initViews() {
        mListView= (ListView) view.findViewById(R.id.listview_person_msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //BroadcastManager.getInstance(getActivity()).destroy(SealConst.CHANGEINFO);
    }


}
