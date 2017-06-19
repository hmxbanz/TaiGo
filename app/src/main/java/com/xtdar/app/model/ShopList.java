package com.xtdar.app.model;

import com.xtdar.app.widget.pinyin.CharacterParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/3/8.
 */

public class ShopList {
    private static final String[] tiles=   {       "商品1", "商品1", "商品1"    };
    private static final String[] imgUrl=   {
            "https://gd1.alicdn.com/imgextra/i3/57542282/TB2atCukVXXXXccXXXXXXXXXXXX_!!57542282.jpg_400x400.jpg",
            "https://gd3.alicdn.com/imgextra/i1/0/TB1yZunPXXXXXaSXpXXXXXXXXXX_!!0-item_pic.jpg_400x400.jpg",

            "https://img.alicdn.com/bao/uploaded/i4/10324030783189560/T1nwjQFe4aXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg"    };
    private static final int[] icons=   {  android.R.drawable.ic_dialog_map, android.R.drawable.ic_btn_speak_now, android.R.drawable.ic_dialog_alert    };

    public static  List<User> getData ()
    {
        List<User> listItems = new ArrayList<>();

        for (int i=0;i<12;i++){
            for(int j = 0; j< ShopList.icons.length; j++)
            {
                User listItem=new User();
                listItem.setNickName(ShopList.tiles[j]);
                listItem.setAvator(ShopList.imgUrl[j]);
                listItem.setImgResource(ShopList.icons[j]);
                listItem.setNameSpelling(CharacterParser.getInstance().getSpelling(ShopList.tiles[j]));
                listItems.add(listItem);
            }
        }

        return listItems;

    }
}
