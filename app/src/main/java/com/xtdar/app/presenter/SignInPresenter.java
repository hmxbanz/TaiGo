package com.xtdar.app.presenter;

import android.content.Context;
import android.widget.TextView;

import com.taigo.calendar.CollapseCalendarView;
import com.taigo.calendar.manager.CalendarManager;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NToast;
import com.xtdar.app.server.HttpException;
import com.xtdar.app.server.async.OnDataListener;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.server.response.ScoreResponse;
import com.xtdar.app.server.response.SignCountResponse;
import com.xtdar.app.view.activity.SignInActivity;
import com.xtdar.app.view.widget.LoadDialog;

import org.joda.time.LocalDate;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class SignInPresenter extends BasePresenter implements OnDataListener {
    private static final int SIGNIN = 1;
    private static final int GETSIGNCOUNT = 2;
    private SignInActivity mActivity;
    private TextView txtSignIn,txtSignAll,txtSignContinue,txtSignToday;
    private CollapseCalendarView calendarView;

    private JSONObject json;
    private SimpleDateFormat sdf;
    private CalendarManager mManager;
    private List<String> signInMonth;

    public SignInPresenter(Context context){
        super(context);
        mActivity = (SignInActivity) context;
    }

    public void init(TextView txtSignIn, TextView txtSignAll, TextView txtSignContinue, TextView txtSignToday, CollapseCalendarView calendarView) {
        this.txtSignIn=txtSignIn;
        this.txtSignAll=txtSignAll;
        this.txtSignContinue=txtSignContinue;
        this.txtSignToday=txtSignToday;
        this.calendarView=calendarView;
        /**
         * 日期选中监听器
         */
//        calendarView.setDateSelectListener(new CollapseCalendarView.OnDateSelect() {
//
//            @Override
//            public void onDateSelected(LocalDate date) {
//                //Toast.makeText(SignInActivity.this, date.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        calendarView.setTitleClickListener(new CollapseCalendarView.OnTitleClickListener() {
//            @Override
//            public void onTitleClick() {
//                //Toast.makeText(SignInActivity.this, "点击标题", Toast.LENGTH_SHORT).show();
//            }
//        });
//        calendarView.showChinaDay(false);

//        //回到今天
//        calendarView.changeDate(LocalDate.now().toString());
//
//        //周月切换
//        mManager.toggleView();
//        calendarView.populateLayout();
//
        //显示或者隐藏农历
        calendarView.showChinaDay(false);


        LoadDialog.show(mActivity);
        atm.request(GETSIGNCOUNT,this);
    }



    @Override
    public Object doInBackground(int requestCode, String parameter) throws HttpException {
        switch (requestCode) {
            case SIGNIN :
                return mUserAction.signIn();
            case GETSIGNCOUNT :
                return mUserAction.getSignCount();
        }
        return null;
    }

    @Override
    public void onSuccess(int requestCode, Object result) {
        LoadDialog.dismiss(context);
        switch (requestCode) {
            case SIGNIN :
                CommonResponse commonResponse=(CommonResponse)result;
                if (commonResponse != null && commonResponse.getCode() == XtdConst.SUCCESS) {
                    this.txtSignIn.setText("已签到");
                }
                NToast.shortToast(mActivity, commonResponse.getMsg());
                break;
            case GETSIGNCOUNT :
                SignCountResponse signCountResponse=(SignCountResponse)result;
                if (signCountResponse != null && signCountResponse.getCode() == XtdConst.SUCCESS) {
                    if("1".equals(signCountResponse.getData().getIs_sign_in()))
                    this.txtSignIn.setText("已签到");
                    this.txtSignAll.setText(signCountResponse.getData().getCard_count_day());
                    this.txtSignContinue.setText(signCountResponse.getData().getSign_in_days());
                    this.txtSignToday.setText("今日签到人数："+signCountResponse.getData().getAll_sign_in_count());

                    signInMonth=signCountResponse.getData().getMonth_sign_in_day();
                    ArrayList<String> localArray=new ArrayList<>();
                    for (String monthStr : signInMonth) {
                        localArray.add(monthStr.substring(8));
                    }

                    //日历数据初始
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    int month = cal.get(Calendar.MONTH);
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.DAY_OF_MONTH, 0);
                    json = new JSONObject();
                    try {
                        for (int i = 0; i <= getCurrentMonthDay(); i++) {
                            JSONObject jsonObject2 = new JSONObject();
                            for (String s:localArray) {
                                if(Integer.parseInt(s)==i)
                                jsonObject2.put("type", "到");
                            }
//                            if (i <= 6) {
//                                jsonObject2.put("type", "到");
//                            } else if (i > 6 && i < 11) {
//                                jsonObject2.put("type", "班");
//                            }
//                            if (i % 3 == 0) {
//                                jsonObject2.put("list", new JSONArray());
//                            }

                            json.put(sdf.format(cal.getTime()), jsonObject2);

                            cal.add(Calendar.DAY_OF_MONTH, 1);
                        }
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    //设置数据显示
                    calendarView.setArrayData(json);
                    mManager = new CalendarManager(LocalDate.now(),
                            CalendarManager.State.MONTH, LocalDate.now().withYear(100),
                            LocalDate.now().plusYears(100));
                    //月份切换监听器
                    mManager.setMonthChangeListener(new CalendarManager.OnMonthChangeListener() {

                        @Override
                        public void monthChange(String month, LocalDate mSelected) {
                            //Toast.makeText(SignInActivity.this, month, Toast.LENGTH_SHORT).show();
                        }

                    });

                    //初始化日历管理器
                    calendarView.init(mManager);

                }
                NToast.shortToast(mActivity, signCountResponse.getMsg());


        }


    }
    /**
     * 获取当月的 天数
     * */
    public static int getCurrentMonthDay() {

        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
    public void signIn() {
        LoadDialog.show(context);
        atm.request(SIGNIN, this);
    }
}