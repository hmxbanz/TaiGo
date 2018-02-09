package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.loader.GlideImageLoader;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.CommentResponse;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.view.activity.ReportActivity;
import com.xtdar.app.view.widget.LoadDialog;
import com.xtdar.app.view.widget.SelectableRoundedImageView;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class ReportPresenter extends BasePresenter implements View.OnClickListener,OnDataListener {
    private static final int REPORT = 1;
    private ReportActivity mActivity;
    private Button btn_report;
    private String tips,reportType="垃圾营销";
    private RadioGroup radioGroup;
    private final GlideImageLoader glideImageLoader;
    private String com_id,nickName,avatar,content;

    public ReportPresenter(Context context){
        super(context);
        mActivity = (ReportActivity) context;
        glideImageLoader = new GlideImageLoader();
    }

    public void init() {
        this.btn_report = (Button)mActivity.findViewById(R.id.btn_report);
        this.btn_report.setOnClickListener(this);
        this.radioGroup = (RadioGroup)mActivity.findViewById(R.id.rg);
        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton)mActivity.findViewById(radioGroup.getCheckedRadioButtonId());
                reportType = radioButton.getText().toString();
            }
        });

        Intent intent = mActivity.getIntent();
        com_id = intent.getStringExtra("com_id");
        nickName = intent.getStringExtra("nickName");
        avatar = intent.getStringExtra("avatar");
        content = intent.getStringExtra("content");

        ((TextView)mActivity.findViewById(R.id.nickname)).setText(nickName);
        ((TextView)mActivity.findViewById(R.id.content)).setText(content);
        SelectableRoundedImageView avatarImg = (SelectableRoundedImageView) mActivity.findViewById(R.id.avatar);
        glideImageLoader.displayImage(context, XtdConst.IMGURI+avatar,avatarImg);
    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        return mUserAction.addReport(com_id,reportType,tips);
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        CommonResponse commonResponse=(CommonResponse)result;
        if (commonResponse != null && commonResponse.getCode() == XtdConst.SUCCESS) {
            NToast.shortToast(mActivity,commonResponse.getMsg());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_report :
                submitReport();

        }
    }

    private void submitReport() {
        this.tips=((EditText)mActivity.findViewById(R.id.report_content)).getText().toString();
        if(TextUtils.isEmpty(this.reportType))
        {
            NToast.shortToast(mActivity,"请选择举报类型");
            return;
        }
        LoadDialog.show(context);
        atm.request(REPORT,this);
    }
}