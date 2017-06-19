package com.xtdar.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xtdar.app.R;


public class ConversationActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 会话fragment
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        String stargetId = getIntent().getData().getQueryParameter("targetId");
        String sTitle = getIntent().getData().getQueryParameter("title");
        txtTitle =(TextView) findViewById(R.id.text_title);
        if(!TextUtils.isEmpty(sTitle))
            txtTitle.setText(sTitle+"聊天中...");
        else
            txtTitle.setText("聊天中...");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;

        }
    }
}
