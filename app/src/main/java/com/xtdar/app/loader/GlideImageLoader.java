package com.xtdar.app.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xtdar.app.R;
import com.youth.banner.loader.ImageLoader;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
        Glide.with(context.getApplicationContext())
                .load(path)
                //.placeholder(R.drawable.ic_default_color)//
                .error(R.drawable.logo)//
                //.diskCacheStrategy(DiskCacheStrategy.ALL)//
                .crossFade()
                .into(imageView);
    }


}
