package com.xtdar.app.model;

import com.xtdar.app.widget.pinyin.CharacterParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/3/8.
 */

public class CategoryList {
    private static final String[] tiles=   {"模型玩具", "毛绒玩具", "婴幼玩具" , "益智早教", "电动遥控", "习惯回力", "摇铃床铃"       };
    private static final String[] imgUrl=   {
            "http://img.hb.aicdn.com/c9dfad8e7811b939cdcaf62c104cae43c5e769b717827-xQjY41_fw658",
            "http://img.hb.aicdn.com/2be9492adb6b68f0190e1322c3e31eeab4f0dc331cc42-BAaHon_fw658",
            "http://img.hb.aicdn.com/e715b8528b36647755c3e1949fc343930fbb96f77d61-dVB8Vp_fw658",
            "http://www.poluoluo.com/sc/UploadFiles_2845/201211/2012112622184473.png",
            "http://img.hb.aicdn.com/d957accaf5dd69d1a45eeb2ea05e279b1e456301ef60-mWC3We_fw658",
            "http://www.poluoluo.com/sc/UploadFiles_2845/201211/2012112622184576.png",
            "http://img.hb.aicdn.com/11c8a22a44b1c6cadeee46e8f0166b42bee3d89bcb74-peBAEb_fw658"    };
    private static final int[] icons=   {  android.R.drawable.ic_dialog_map, android.R.drawable.ic_btn_speak_now, android.R.drawable.ic_dialog_alert    };

    public static  List<Category> getData ()
    {
        List<Category> listItems = new ArrayList<>();

        for (int i=0;i<1;i++){
            for(int j = 0; j< CategoryList.tiles.length; j++)
            {
                Category listItem=new Category();
                listItem.setName(CategoryList.tiles[j]);
                listItem.setImgResource(CategoryList.imgUrl[j]);
                listItems.add(listItem);
            }
        }

        return listItems;

    }
}
