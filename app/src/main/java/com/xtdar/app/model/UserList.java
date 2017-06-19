package com.xtdar.app.model;

import java.util.ArrayList;
import java.util.List;

import com.xtdar.app.widget.pinyin.CharacterParser;

/**
 * Created by hmxbanz on 2017/3/8.
 */

public class UserList {
    private static final String[] tiles=   {       "大眼仔", "大眼娃", "大眼天使"    };
    private static final String[] imgUrl=   {       "http://pic10.nipic.com/20101102/6105108_141853016938_2.jpg", "http://tc.sinaimg.cn/maxwidth.2048/tc.service.weibo.com/p/mmbiz_qlogo_cn/640dcda724bbed42f4873eebff1b0c25.jpg", "http://img03.taobaocdn.com/bao/uploaded/i3/12513029106447566/T1QRKAFlRfXXXXXXXX_!!0-item_pic.jpg"    };
    private static final int[] icons=   {  android.R.drawable.ic_dialog_map, android.R.drawable.ic_btn_speak_now, android.R.drawable.ic_dialog_alert    };

    public static  List<User> getData ()
    {
        List<User> listItems = new ArrayList<>();

        for (int i=0;i<2;i++){
            for(int j = 0; j< UserList.icons.length; j++)
            {
                User listItem=new User();
                listItem.setNickName(UserList.tiles[j]);
                listItem.setAvator(UserList.imgUrl[j]);
                listItem.setImgResource(UserList.icons[j]);
                listItem.setNameSpelling(CharacterParser.getInstance().getSpelling(UserList.tiles[j]));
                listItems.add(listItem);
            }
        }

        return listItems;

    }
}
