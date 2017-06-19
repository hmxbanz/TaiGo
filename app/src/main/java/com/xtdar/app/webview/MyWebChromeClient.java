package com.xtdar.app.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.xtdar.app.widget.progressBar.MaterialProgressBar;

import com.xtdar.app.R;

/**
 * Created by Administrator on 2015/8/19.
 */
public class MyWebChromeClient extends WebChromeClient {

    private Activity context;
    private MaterialProgressBar progressBar;
    private Animation animation;
    public final static int FILECHOOSER_RESULTCODE = 1;
    public static final int REQUEST_SELECT_FILE = 100;
    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> uploadMessage;

    public MyWebChromeClient(Activity activity) {
        this.context = activity;
    }

    @Override
    public void onProgressChanged(WebView view, int progress) {
        progressBar = (MaterialProgressBar)context.findViewById(R.id.progressBar);
        progressBar.setMode(MaterialProgressBar.Mode.INDETERMINATE);

        if (progress < 100) {
            if (progressBar.getVisibility() == View.GONE)
                progressBar.setVisibility(View.VISIBLE);
                animation = AnimationUtils.loadAnimation(context, R.anim.animation);
                progressBar.startAnimation(animation);
                //progressBar.setProgress(progress);
        } else {
                progressBar.setVisibility(View.GONE);
            //((MainActivity)this.context).mPullWebView.onRefreshComplete();
        }

        super.onProgressChanged(view, progress);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
    //handle Alert event, here we are showing AlertDialogCallback
        new AlertDialog.Builder(context)
                .setTitle("男女友提示您：")
                .setMessage(message)
                .setPositiveButton("Close", null)
                .setPositiveButton(android.R.string.ok,
                        new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do your stuff
                                result.confirm();
                            }
                        }).setCancelable(false).create().show();
        return true;
    }

    /**
     * 覆盖默认的window.confirm展示界面，避免title里显示为“：来自file:////”
     */
    @Override
    public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("对话框")
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.cancel();
                    }
                });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                result.cancel();
            }
        });

        // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                Log.v("onJsConfirm", "keyCode==" + keyCode + "event=" + event);
                return true;
            }
        });
        // 禁止响应按back键的事件
        // builder.setCancelable(false);
        // AlertDialogCallback dialog = builder.create();
        //dialog.show();
        return true;
        // return super.onJsConfirm(view, url, message, result);
    }

    /**
     * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
     * window.prompt('请输入您的域名地址', '618119.com');
     */
//    public boolean onJsPrompt(WebView view, String url, String message,
//                              String defaultValue, final JsPromptResult result) {
//        final AlertDialogCallback.Builder builder = new AlertDialogCallback.Builder(view.getContext());
//
//        builder.setNickName("对话框").setMessage(message);
//
//        final EditText et = new EditText(view.getContext());
//        et.setSingleLine();
//        et.setText(defaultValue);
//        builder.setView(et)
//                .setPositiveButton("确定", new OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm(et.getText().toString());
//                    }
//
//                })
//                .setNeutralButton("取消", new OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.cancel();
//                    }
//                });
//
//        // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
//        builder.setOnKeyListener(new OnKeyListener() {
//            public boolean onKey(DialogInterface dialog, int keyCode,KeyEvent event) {
//                Log.v("onJsPrompt", "keyCode==" + keyCode + "event="+ event);
//                return true;
//            }
//        });
   // return true;
//}

    //关键代码，以下函数是没有API文档的，所以在Eclipse中会报错，如果添加了@Override关键字在这里的话。
    // For 3.0+ Devices (Start)
    // onActivityResult attached before constructor
    protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        this.context.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
    }
    // For Lollipop 5.0+ Devices
    public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        if (uploadMessage != null) {
            uploadMessage.onReceiveValue(null);
            uploadMessage = null;
        }

        uploadMessage = filePathCallback;

        Intent intent = fileChooserParams.createIntent();
        try {
            this.context.startActivityForResult(intent, REQUEST_SELECT_FILE);
        } catch (ActivityNotFoundException e) {
            uploadMessage = null;
            Toast.makeText(this.context.getApplicationContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    //For Android 4.1 only
    protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        mUploadMessage = uploadMsg;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        this.context.startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
    }
    protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("image/*");
        this.context.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
    }


}
