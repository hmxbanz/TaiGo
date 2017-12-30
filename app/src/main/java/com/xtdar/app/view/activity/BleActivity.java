package com.xtdar.app.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.xtdar.app.R;
import com.xtdar.app.ble.BleScanner;
import com.xtdar.app.presenter.BlePresenter;
import com.xtdar.app.presenter.HelpPresenter;
import com.xtdar.app.widget.progressBar.MaterialProgressBar;


public class BleActivity extends BaseActivity  {
    public static final int REQUEST_CODE = 1;
    private BlePresenter presenter;
    private ListView listView;
    private Button btnScan;
    private MaterialProgressBar progressWheel;
    private RelativeLayout emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);

        initViews();
        presenter = new BlePresenter(this);
        presenter.init(listView,btnScan,progressWheel,emptyView);

    }

    private void initViews() {
        layout_back = (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("附近的蓝牙设备");

        progressWheel=(MaterialProgressBar)findViewById(R.id.progress_wheel);
        emptyView = (RelativeLayout) findViewById(R.id.empty_view);
        findViewById(R.id.txt_add_driver).setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listview_ble);
        btnScan = (Button) findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_back:
                finish();
                break;
            case R.id.btn_scan:
                presenter.startService();
                break;
            case R.id.txt_add_driver:
                Intent intent = new Intent(this, QrCodeActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         * 处理二维码扫描结果
         */
        switch (requestCode) {
            case REQUEST_CODE:
                //处理扫描结果（在界面上显示）
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        presenter.bindQrCode(result);
                        Toast.makeText(this, "扫描成功:" + result, Toast.LENGTH_LONG).show();
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(this, "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }





    @Override
    public final void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 12:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            presenter.onPermissionGranted(permissions[i]);
                        }
                    }
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
