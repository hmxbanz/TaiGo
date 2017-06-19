package com.xtdar.app.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xtdar.app.R;



/**
 * Created by AMing on 15/11/26.
 * Company RongCloud
 */
public class DialogWithYesOrNoUtils {

    private static DialogWithYesOrNoUtils instance = null;

    public static DialogWithYesOrNoUtils getInstance() {
        if (instance == null) {
            instance = new DialogWithYesOrNoUtils();
        }
        return instance;
    }

    private DialogWithYesOrNoUtils() {
    }

    public void showDialog(Context context, String titleText,String confirmText ,final DialogCallBack callBack) {
        final AlertDialog alterDialog = new AlertDialog.Builder(context).create();
        alterDialog.show();
        Window window=alterDialog.getWindow();
        window.setContentView(R.layout.alert_dialog);
        TextView textTitle = (TextView) window.findViewById(R.id.text_title);
        textTitle.setText(titleText);
        TextView btnCancle = (TextView) window.findViewById(R.id.btn_cancle);
        btnCancle.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alterDialog.dismiss();
                callBack.onCancle();
            }
        });
        TextView btnConfirm = (TextView) window.findViewById(R.id.btn_confirm);
        if(!TextUtils.isEmpty(confirmText))    btnConfirm.setText(confirmText);
        btnConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                callBack.executeEvent();
                alterDialog.dismiss();
            }
        });
        //另一种自定义alertDialog样式的方法(只定义中间部分布局，按键“确定”，“取消”还是系统的)
//AlertDialogCallback.Builder alterDialog = new AlertDialogCallback.Builder(context);
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_setting, null);
//        alterDialog.setView(layout);
//        alterDialog.setMessage(titleInfo);
//        alterDialog.setCancelable(true);//
//        alterDialog.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                callBack.executeEvent();
//            }
//        });
//        alterDialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
    }

    public interface DialogCallBack {
        void executeEvent();
        void onCancle();
    }


}
