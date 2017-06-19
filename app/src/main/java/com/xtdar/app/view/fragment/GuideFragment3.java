package com.xtdar.app.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xtdar.app.view.activity.Main2Activity;

import com.xtdar.app.R;

/**
 * Created by Administrator on 2015/10/3.
 */
@SuppressLint({"ValidFragment"})
public class GuideFragment3 extends Fragment{
    private Context ctx;
    public GuideFragment3(Context paramContext)
    {
        this.ctx = paramContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = View.inflate(ctx, R.layout.guidefragment3, null);
        Button mBtn = (Button) view.findViewById(R.id.MyLoginBtn);
        mBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(ctx, Main2Activity.class);
                    ctx.startActivity(intent);
            }
        });
        return view;
    }


}



