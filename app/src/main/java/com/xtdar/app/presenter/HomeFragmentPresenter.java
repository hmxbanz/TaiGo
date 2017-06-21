package com.xtdar.app.presenter;

import android.content.Context;
import android.support.design.widget.TabLayout;

import com.xtdar.app.XtdConst;
import com.xtdar.app.common.json.JsonMananger;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.TagResponse;
import com.xtdar.app.view.widget.LoadDialog;

import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class HomeFragmentPresenter extends BasePresenter implements OnDataListener {
    private static final int GETTAGS = 1;
    private TabLayout tabLayout;
    private List<TagResponse.DataBean> tagList;
    //private ContactsActivity mActivity;
    public HomeFragmentPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
    }

    public void init(TabLayout tabLayout) {
        this.tabLayout=tabLayout;
        tabLayout.getTabAt(0).setText("射击类");
        tabLayout.getTabAt(1).setText("赛车类");
        tabLayout.getTabAt(2).setText("益智类");
        tabLayout.getTabAt(3).setText("全部");
        LoadDialog.show(mContext);
        atm.request(GETTAGS,this);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case GETTAGS:
                return mUserAction.getTags();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(mContext);
        switch (requestCode) {
            case GETTAGS:
                TagResponse tagResponse = (TagResponse) result;
                if (tagResponse.getCode() == XtdConst.SUCCESS) {
                     tagList = tagResponse.getData();
                    try {
                        String cache= JsonMananger.beanToJson(tagList);
                        aCache.put("TagList",cache);
                    } catch (HttpException e) {
                        e.printStackTrace();
                    }
//                    for (int i = 0; i<= tagList.size(); i++) {
//                        tabLayout.getTabAt(i).setText(tagList.get(i).getClass_name());
//                    }
                }
                break;
        }
    }
}