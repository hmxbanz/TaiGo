package com.xtdar.app.widget.toast;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xtdar.app.R;


public class CustomToast extends Activity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_toast);
    }

    public void CustomToastStyle1(View view){
        Toast toast= Toast.makeText(
                getApplicationContext(),
                "默认Toast样式",
                Toast.LENGTH_SHORT);
        toast.show();


    }

    public void CustomToastStyle2(View view){
        Toast toast= Toast.makeText(getApplicationContext(),
                        "自定义位置Toast", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    public void CustomToastStyle3(View view){
        Toast toast = Toast.makeText(getApplicationContext(),
                "带图片的Toast", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(getApplicationContext());
        imageCodeProject.setImageResource(R.drawable.logo);
        toastView.addView(imageCodeProject, 0);
        toast.show();

    }

    public void CustomToastStyle4(View view){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.customtoastlayout2,(ViewGroup) findViewById(R.id.llToast));
        ImageView image = (ImageView) layout.findViewById(R.id.tvImageToast);
        image.setImageResource(R.drawable.logo);
        TextView title = (TextView) layout.findViewById(R.id.tvTitleToast);
        title.setText("Attention");
        TextView text = (TextView) layout.findViewById(R.id.tvTextToast);
        text.setText("完全自定义Toast");
        Toast toast;
        toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.RIGHT | Gravity.TOP, 12, 40);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void CustomToastStyle5(View view){
        new Thread(new Runnable() {
            public void run() {
                showToast();
            }
        }).start();
    }

    public void showToast() {
        handler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "我来自其他线程！",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

}
