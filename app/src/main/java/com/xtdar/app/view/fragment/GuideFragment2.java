package com.xtdar.app.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xtdar.app.R;

/**
 * Created by Administrator on 2015/10/3.
 */
@SuppressLint({"ValidFragment"})
public class GuideFragment2 extends Fragment{
    private Context ctx;
    public GuideFragment2(Context paramContext)
    {
        this.ctx = paramContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view;
        view = View.inflate(ctx, R.layout.guidefragment2, null);
        LinearLayout mLinear = (LinearLayout) view
                .findViewById(R.id.GuideFragment02_LinearLayout);
        mLinear.setBackgroundResource(R.drawable.guide_pic_02);
        return view;
    }


}



