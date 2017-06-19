package com.xtdar.app.webview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Hmx on 2015/8/19.
 */
public class MyWebViewClient extends WebViewClient {
    private Activity activity;
    public MyWebViewClient(Activity activity) {
        this.activity = activity;
    }
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
           }
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }
    @Override
    public void onReceivedError(WebView view, int errorCode,String description, String failingUrl) {
        // TODO Auto-generated method stub
        //super.onReceivedError(view, errorCode, description, failingUrl);
        //progressBar.setVisibility(android.view.View.GONE);
        view.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
        view.loadUrl("file:///android_asset/error.html");
       //String data = "<h4>找不到网络</h4>";
       //view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
        //view.setBackgroundResource(R.drawable.icon);
        //view.setBackgroundColor(Color.TRANSPARENT);
        //String customHtml = "<html><body><h4><font color='red'>找不到网络ʾ</font></h4></body></html>";
        //view.loadData(customHtml, "text/html", "UTF-8");
    }
    @Override
    public void onReceivedSslError (WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (Uri.parse(url).getHost().contains("xtdar.com"))
        {
            view.loadUrl(url);
        }
        else
        {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
            this.activity.startActivity(intent);
        }
        return true;
    }

}
