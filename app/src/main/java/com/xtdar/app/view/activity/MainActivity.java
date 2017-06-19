package com.xtdar.app.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.xtdar.app.webview.MyWebViewClient;
import com.xtdar.app.widget.toast.CustomToast;

import org.xutils.view.annotation.ContentView;

import java.lang.reflect.Method;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import com.xtdar.app.R;
import com.xtdar.app.webview.MyWebChromeClient;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    private final String TAG="MainActivity";
    private WebView mWebView;
    public PullToRefreshWebView mPullWebView;
    private final static int menuService=1002;
    private long lastBackPressTime;
    private MyWebChromeClient myWebChromeClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        //requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //UpdateConfig.setDebug(true);
//        //UmengUpdateAgent.update(this);
        mPullWebView = (PullToRefreshWebView) findViewById(R.id.pull_webview);//new PullToRefreshWebView(this);
        mPullWebView.setMode(Mode.DISABLED);
        mWebView = mPullWebView.getRefreshableView();
        myWebChromeClient=new MyWebChromeClient(this);
        mWebView.setWebChromeClient(myWebChromeClient);
        mWebView.setWebViewClient(new MyWebViewClient(MainActivity.this));
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.addJavascriptInterface(new JpushForJs(), "Jpush");
        //mWebView.getSettings().setUserAgentString("Android Chrome/37.0.0.0 Mobile Safari/537.36");
        mWebView.getSettings().setAppCacheEnabled(true);
        //设置缓存模式
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        String storePath = Environment.getExternalStorageDirectory().getPath().toString() + "/webcache/";
        mWebView.getSettings().setAppCachePath(storePath);
        mWebView.loadUrl("http://www.xtdar.com");
//        Gson gsonInstance=new Gson();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
    //处理网页后退方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            // 返回键退回
            mWebView.goBack();
            return true;
        }
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
    ///菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        MenuInflater menuInflate = getMenuInflater();
//        menuInflate.inflate(R.menu.menu_main, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
//            @Override
//            public boolean onMenuItemActionExpand(MenuItem item) {
//                Toast.makeText(MainActivity.this, "展开", Toast.LENGTH_LONG);
//                return true;
//            }
//
//            @Override
//            public boolean onMenuItemActionCollapse(MenuItem item) {
//                Toast.makeText(MainActivity.this, "收缩", Toast.LENGTH_LONG);
//                return true;
//            }
//        });
//        MenuItem menuItem=menu.add(1,menuService, 1, "服务");
//        menuItem.setIcon(R.drawable.ic_home_black_24dp);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.CustomToastMenuItem:
                Intent CustomToastIntent = new Intent(MainActivity.this, CustomToast.class);
                item.setIntent(CustomToastIntent);
                 break;
            case R.id.menu_search:
                break;
            case R.id.menu_setting:
                showSettings();
                break;
            case R.id.dialog:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    //   弹出菜单    //重写这一方法让overflow中的菜单图标显示出来
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        Boolean flag = (currentTime - lastBackPressTime) < 1500;
        if (flag) {
            super.onBackPressed();
        } else {
            lastBackPressTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            if (requestCode == this.myWebChromeClient.REQUEST_SELECT_FILE)
            {
                if (this.myWebChromeClient.uploadMessage == null)
                    return;
                this.myWebChromeClient.uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                this.myWebChromeClient.uploadMessage = null;
            }
        }
        else if (requestCode == this.myWebChromeClient.FILECHOOSER_RESULTCODE)
        {
            if (null == this.myWebChromeClient.mUploadMessage)
                return;
            // Use MainActivity.RESULT_OK if you're implementing WebView inside ShopFragment
            // Use RESULT_OK only if you're implementing WebView inside an Activity
            Uri result = intent == null || resultCode != MainActivity.RESULT_OK ? null : intent.getData();
            this.myWebChromeClient.mUploadMessage.onReceiveValue(result);
            this.myWebChromeClient.mUploadMessage = null;
        }
        else
            Toast.makeText(getApplicationContext(), "Failed to Upload Image", Toast.LENGTH_LONG).show();
    }
    //用菜单显示系统设置
    private void showSettings() {
        final Intent settings = new Intent(android.provider.Settings.ACTION_SETTINGS);
        settings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(settings);
    }
    //小方法
//    private void setLastUpdateTime() {
//        String text = formatDateTime(System.currentTimeMillis());
//        mPullWebView.setLastUpdatedLabel(text);}

//    private class JsToJava {
//        public void jsMethod(String paramFromJS) {
//
//            Log.i("CDH", paramFromJS);
//        }
//        // 获取在webview上获取js生成的html的源码
//        @JavascriptInterface
//        public void getSource(String htmlstr) {
//            // Log.e("html", htmlstr);
//            // String path = c.getFilesDir().getAbsolutePath() + "/serve.html"; //
//            // data/data目录
//        }
//    }

    //设置极光推送代码开始
    // 这是来自 JPush Example 的设置别名的 Activity 里的代码。一般 App 的设置的调用入口，在任何方便的地方调用都可以。
    private void setAlias(String cellPhone) {
        String phone=sp.getString("cellPhone", "");
        if(!cellPhone.equals(phone)) {
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, cellPhone.trim()));        // 调用 Handler 来异步设置别名
            editor.putString("cellPhone", cellPhone.trim());
            editor.apply();
        }
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,null,mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
    //设置极光推送代码结束
    private final class JpushForJs {
        @JavascriptInterface
        public void setJpushAlias(String cellPhone) {
            setAlias(cellPhone);
        }

        //Html调用此方法传递数据
//        public void showcontacts() {
//            String json = "[{\"name\":\"zxx\", \"amount\":\"9999999\", \"phone\":\"18600012345\"}]";
//            // 调用JS中的方法
//            mWebView.loadUrl("javascript:show('" + json + "')");
//        }
    }
}
