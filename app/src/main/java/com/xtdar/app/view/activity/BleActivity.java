package com.xtdar.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.ble.BleScanner;
import com.xtdar.app.presenter.BlePresenter;
import com.xtdar.app.presenter.HelpPresenter;


public class BleActivity extends BaseActivity implements View.OnClickListener {
private BlePresenter presenter;
    private ListView listView;
    private Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);

        initViews();
        presenter = new BlePresenter(this);
        presenter.init(listView,btnScan);

    }

    private void initViews() {
        layout_back = (RelativeLayout) findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);
        txtTitle =(TextView) findViewById(R.id.text_title);
        txtTitle.setText("附近的蓝牙设备");

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
                presenter.startBleScanner();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case BleScanner.REQUEST_ENABLE_BT:
                presenter.startBleScanner();
        }
    }
}
