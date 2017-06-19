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
    private List<TagResponse.ResultEntity> tagList;

    //private ContactsActivity mActivity;
    public HomeFragmentPresenter(Context context){
        super(context);
        //mActivity = (ContactsActivity) context;
    }

    public void init(TabLayout tabLayout) {
        this.tabLayout=tabLayout;
        //取缓存
        String TagListCache = aCache.getAsString("TagList");
        if(TagListCache!=null && !("null").equals(TagListCache)) {
            try {
                tagList = JsonMananger.jsonToList(TagListCache, TagResponse.ResultEntity.class);
                for (int i = 0; i <= tagList.size(); i++) {
                    tabLayout.getTabAt(i).setText(tagList.get(i).getClass_name());
                }
            } catch (HttpException e) {
                e.printStackTrace();
            }

        }
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
                    for (int i = 0; i<= tagList.size(); i++) {
                        tabLayout.getTabAt(i).setText(tagList.get(i).getClass_name());
                    }
                }
                break;
        }
    }
}