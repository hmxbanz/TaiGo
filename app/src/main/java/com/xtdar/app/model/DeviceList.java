package com.xtdar.app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/3/8.
 */

public class DeviceList {
    private static final String[] tiles=   {"设备1"};
    private static final String[] imgUrl=   {
            "http://img.hb.aicdn.com/c9dfad8e7811b939cdcaf62c104cae43c5e769b717827-xQjY41_fw658"
           };

    public static  List<Category> getData ()
    {
        List<Category> listItems = new ArrayList<>();

        for (int i=0;i<1;i++){
            for(int j = 0; j< DeviceList.tiles.length; j++)
            {
                Category listItem=new Category();
                listItem.setName(DeviceList.tiles[j]);
                listItem.setImgResource(DeviceList.imgUrl[j]);
                listItems.add(listItem);
            }
        }

        return listItems;

    }
}
