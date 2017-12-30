package com.xtdar.app.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.TextView;

import com.xtdar.app.R;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.LotteryDataResponse;
import com.xtdar.app.server.response.LotteryResponse;
import com.xtdar.app.view.activity.HelpDetailActivity;
import com.xtdar.app.view.activity.LotteryActivity;
import com.xtdar.app.view.activity.LotteryPrizeActivity;
import com.xtdar.app.widget.LotteryPopDialog;
import com.xtdar.app.widget.VerticalScrollTextView;
import com.xtdar.app.widget.lottery.LotteryDisk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class LotteryPresenter extends BasePresenter implements OnDataListener {
    private static final int STARTLOTTERY = 1;
    private static final int INITDATA = 2;
    private LotteryActivity mActivity;
    private String prizeName ;
    private LotteryDisk lotteryPlate;
    private int prizePosition;
    private int bg;
    private TextView txtTips;
    private VerticalScrollTextView scrollTextView;

    public LotteryPresenter(Context context){
        super(context);
        mActivity = (LotteryActivity) context;
    }

    public void init(LotteryDisk lotteryPlate,VerticalScrollTextView scrollTextView) {
        this.scrollTextView=scrollTextView;
        this.lotteryPlate=lotteryPlate;
        lotteryPlate.setAnimationEndListener(new LotteryDisk.AnimationEndListener() {
            @Override
            public void endAnimation(int position) {
                loadData(txtTips);
                NToast.longToast(mActivity,prizeName);
                LotteryPopDialog.getInstance().showDialog(mActivity,  bg,null, null, new LotteryPopDialog.DialogCallBack() {
                    @Override
                    public void executeEvent() {
                        mActivity.startActivity(new Intent(mActivity,LotteryPrizeActivity.class));
                    }

                    @Override
                    public void onCancle() {

                    }
                });
            }
        });
    }
    public void loadData(TextView txtTips) {
        this.txtTips=txtTips;
        atm.request(INITDATA,this);
    }

    public void startLottery() {
        atm.request(STARTLOTTERY,this);

    }

    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case STARTLOTTERY:
                return mUserAction.startLottery();
            case INITDATA:
                return mUserAction.loadLotteryData();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        if (result == null) return;
        switch (requestCode) {
            case STARTLOTTERY:
                LotteryResponse lotteryResponse = (LotteryResponse) result;
                if(lotteryResponse.getCode()== XtdConst.SUCCESS)
                {
                    prizePosition =DeGreeTurn(lotteryResponse.getData().getDegree());
                    prizeName=lotteryResponse.getData().getRolls_item_tips();
                    lotteryPlate.startRotate(prizePosition);
                }
                else
                    NToast.longToast(mActivity,lotteryResponse.getMsg());
            break;
            case INITDATA:
                LotteryDataResponse lotteryDataResponsee = (LotteryDataResponse) result;
                if(lotteryDataResponsee.getCode()== XtdConst.SUCCESS)
                {
                    this.txtTips.setText("您今天还有"+lotteryDataResponsee.getData().getLeft_over()+"次机会");
                    List<String> list = new ArrayList<>();
                    for(LotteryDataResponse.DataBean.LuckUserListBean bean:lotteryDataResponsee.getData().getLuck_user_list()){
                        list.add(bean.getUse_name()+" "+bean.getDraw_rolls_tips());

                    }
                    this.scrollTextView.setDataSource(list);
                    this.scrollTextView.startPlay();
                }
                else
                    NToast.longToast(mActivity,lotteryDataResponsee.getMsg());
                break;
        }
    }
    //角度转换
    public int DeGreeTurn(String degrees)
    {
        if(TextUtils.isEmpty(degrees)) return 0;
        int result=0;
        int degree=Integer.valueOf(degrees);
        if(degree == 0){
            result=0;
            bg= R.drawable.lottery_prize_01;
        }
        else if (degree >= 0 && degree <= 36) {
            result=1;
            bg= R.drawable.lottery_prize_02;
        }
        else if(degree >= 36 && degree <= 72)
        {
            result=2;
            bg= R.drawable.lottery_prize_03;
        }
        else if(degree >= 72 && degree <= 108)
        {
            result=3;
            bg= R.drawable.lottery_prize_04;
        }
        else if(degree >= 108 && degree <= 144)
        {
            result=4;
            bg= R.drawable.lottery_prize_01;
        }
        else if(degree >= 144 && degree <= 180)
        {
            result=5;
            bg= R.drawable.lottery_prize_05;
        }
        else if(degree >= 180 && degree <= 216)
        {
            result=6;
            bg= R.drawable.lottery_prize_06;
        }
        else if(degree >= 216 && degree <= 252)
        {
            result=7;
            bg= R.drawable.lottery_prize_07;
        }
        else if(degree >= 252 && degree <= 288)
        {
            result=8;
            bg= R.drawable.lottery_prize_07;
        }
        else if(degree >= 288)
        {
            result=9;
            bg= R.drawable.lottery_prize_07;
        }
            return result;
    }
    public void getLotteryRule() {
        Intent intent = new Intent(mActivity, HelpDetailActivity.class);
        intent.putExtra("title","活动规则");
        intent.putExtra("url", XtdConst.SERVERURI+"cli-dgc-helpdetail.php?article_id=9");
        mActivity.startActivity(intent);
    }
}




//            {
//                    "rolls_item_tips":"iphoneX",        //中奖项
//                    "degree":"0",                                 //角度
//                    "show_img":"a_img/fe69028c52532ed0fee45939ad9c6bb2.png"   //分享图片
//                    },
//                    {
//                    "rolls_item_tips":"谢谢参与",
//                    "degree":"36",
//                    "show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"
//                    },
//                    {
//                    "rolls_item_tips":"精装AR枪",
//                    "degree":"72",
//                    "show_img":"a_img/7fb4f7f86048e3de059858a32e97f1a8.png"
//                    },
//                    {
//                    "rolls_item_tips":"100积分",
//                    "degree":"108",
//                    "show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"
//                    },
//                    {
//                    "rolls_item_tips":"高级AR枪",
//                    "degree":"144",
//                    "show_img":"a_img/7fb4f7f86048e3de059858a32e97f1a8.png"
//                    },
//                    {
//                    "rolls_item_tips":"50元话费",
//                    "degree":"180",
//                    "show_img":"a_img/7fb4f7f86048e3de059858a32e97f1a8.png"
//                    },
//                    {
//                    "rolls_item_tips":"OPPO R11s",
//                    "degree":"216",
//                    "show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"
//                    },
//                    {
//                    "rolls_item_tips":"U盘",
//                    "degree":"252",
//                    "show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"
//                    },
//                    {
//                    "rolls_item_tips":"300积分",
//                    "degree":"288",
//                    "show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"
//                    },
//                    {
//                    "rolls_item_tips":"10元话费",
//                    "degree":"324",
//                    "show_img":"a_img/bd088ca395f6abd2a4f46353e48a43ec.png"
//                    }
//
