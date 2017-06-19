package com.xtdar.app.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtdar.app.R;


/**
 * Created by AMing on 16/6/21.
 * Company RongCloud
 */
public class InfoFragment extends Fragment  {
    public static InfoFragment instance = null;
    private View mView;

    public static InfoFragment getInstance() {
        if (instance == null) {
            instance = new InfoFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_info, null);
        //initViews(mView);

        return mView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //BroadcastManager.getInstance(getActivity()).destroy(SealConst.CHANGEINFO);
    }


}
