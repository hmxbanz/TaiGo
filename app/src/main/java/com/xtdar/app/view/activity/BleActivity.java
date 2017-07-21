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

import com.xtdar.app.R;
import com.xtdar.app.ble.BleScanner;
import com.xtdar.app.presenter.BlePresenter;
import com.xtdar.app.presenter.HelpPresenter;
import com.xtdar.app.widget.progressBar.MaterialProgressBar;


public class BleActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 1;
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
        switch (requestCode) {
            case BleScanner.REQUEST_ENABLE_BT:
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
