package com.xtdar.app.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.xtdar.app.R;


/**
 * Created by AMing on 15/11/26.
 * Company RongCloud
 */
public class DialogWithList {

    private static DialogWithList instance = null;
    private final AlertDialog alterDialog;
    private final Window window;
    private TextView textTitle,btnCancle,btnConfirm,content;
    private View line;
    private ListView listView;

    public static DialogWithList getInstance(Context context) {
        //if (instance == null) {
            instance = new DialogWithList(context);
        //}
        return instance;
    }

    private DialogWithList(Context context) {
        alterDialog = new AlertDialog.Builder(context,R.style.mydialog).create();
        alterDialog.show();
        window=alterDialog.getWindow();
        window.setContentView(R.layout.alert_dialog_list);
    }

    public void showDialog(final DialogCallBack callBack) {
        textTitle = (TextView) window.findViewById(R.id.text_title);
        btnConfirm = (TextView) window.findViewById(R.id.btn_confirm);
        btnCancle = (TextView) window.findViewById(R.id.btn_cancle);
        line=alterDialog.findViewById(R.id.line);
        content = (TextView) window.findViewById(R.id.txt_content);
        listView = (ListView) window.findViewById(R.id.listView);

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

    }
    public void setTitle(String title){
        if(!TextUtils.isEmpty(title))
            textTitle.setText(title);
    }
    public void setConfirmText(String confirmText){
        if(!TextUtils.isEmpty(confirmText))
            btnCancle.setText(confirmText);
    }

    public void setCancleVisibility(int flag){
        if(flag==View.GONE) {
            this.btnCancle.setVisibility(View.GONE);
            this.line.setVisibility(View.GONE);
        }
    }

    public void setCancleText(String cancleText){
        if(!TextUtils.isEmpty(cancleText))
            btnCancle.setText(cancleText);
    }

    public void setContent(String content){
        this.content.setText(content);
    }

    public interface DialogCallBack {
        void executeEvent();
        void onCancle();
    }

    public ListView getListView(){
        return this.listView;
    }

}
