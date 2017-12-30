package com.xtdar.app.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xtdar.app.R;


/**
 * Created by AMing on 15/11/26.
 * Company RongCloud
 */
public class LotteryPopDialog {

    private static LotteryPopDialog instance = null;
    private TextView btnCancle;
    private View line;

    public static LotteryPopDialog getInstance() {
        if (instance == null) {
            instance = new LotteryPopDialog();
        }
        return instance;
    }

    private LotteryPopDialog() {
    }

    public void showDialog(Context context, int bg,String cancleText, String confirmText , final DialogCallBack callBack) {
        final AlertDialog alterDialog = new AlertDialog.Builder(context,R.style.mydialog).create();
        alterDialog.show();
        Window window=alterDialog.getWindow();
        window.setContentView(R.layout.lottery_pop_dialog);

        LinearLayout linearLayoutMain=(LinearLayout) window.findViewById(R.id.layout_main);
        linearLayoutMain.setBackground(context.getResources().getDrawable(bg));
        TextView btnConfirm = (TextView) window.findViewById(R.id.btn_confirm);
        if(!TextUtils.isEmpty(confirmText))    btnConfirm.setText(confirmText);
        btnCancle = (TextView) window.findViewById(R.id.btn_cancle);
        if(!TextUtils.isEmpty(cancleText))    btnCancle.setText(cancleText);
        btnCancle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alterDialog.dismiss();
                callBack.onCancle();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callBack.executeEvent();
                alterDialog.dismiss();
            }
        });

        line=window.findViewById(R.id.line);
        ImageView imgClose= (ImageView) window.findViewById(R.id.img_close);
        imgClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alterDialog.dismiss();
            }
        });
    }

    public void setCancleVisibility(int flag){
        if(flag==View.GONE) {
            this.btnCancle.setVisibility(View.GONE);
            this.line.setVisibility(View.GONE);
        }
    }

    public interface DialogCallBack {
        void executeEvent();
        void onCancle();
    }


}
