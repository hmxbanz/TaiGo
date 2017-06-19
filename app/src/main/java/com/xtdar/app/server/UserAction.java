package com.xtdar.app.server;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NLog;
import com.xtdar.app.common.json.JsonMananger;
import com.xtdar.app.server.request.LoginRequest;
import com.xtdar.app.server.response.AdResponse;
import com.xtdar.app.server.response.CaptchaResponse;
import com.xtdar.app.server.response.ClassListResponse;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.server.response.DetailResponse;
import com.xtdar.app.server.response.LoginResponse;
import com.xtdar.app.server.response.RecommendResponse;
import com.xtdar.app.server.response.RelateRecommendResponse;
import com.xtdar.app.server.response.ShowResponse;
import com.xtdar.app.server.response.SongDetailResponse;
import com.xtdar.app.server.response.TagResponse;
import com.xtdar.app.server.response.UserInfoResponse;
import com.xtdar.app.server.response.VersionResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;


import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by AMing on 16/1/14.
 * Company RongCloud
 */
@SuppressWarnings("deprecation")
public class UserAction extends BaseAction {
    private final String TAG="USERACTION";
    private final String CONTENT_TYPE = "application/json";
    private final String ENCODING = "utf-8";
    private String result;
    public String token;
    public static UserAction instance;
    private Object relateRecommend;

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public UserAction(Context context) {
        super(context);
    }
    public static UserAction getInstance(Context context) {
        if (instance == null) {
            synchronized (UserAction.class) {
                if (instance == null) {
                    instance = new UserAction(context);
                }
            }
        }
        return instance;
    }

//登录
    public LoginResponse login(String userName, String password) throws HttpException
    {
        String uri = getURL("kp_dyz/cli-comm-login.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("phone_no",userName)
                    .addParams("pwd",password)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginResponse loginResponse = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("login", result);

            try {
                loginResponse = JsonMananger.jsonToBean(result, LoginResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "LoginResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return loginResponse;

    }

//获取验证码
    public CaptchaResponse getCaptcha(String cellPhone) throws HttpException
    {
        String uri = getURL("kp_dyz/cli-comm-sendregmsg.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("phone_no",cellPhone)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            Log.w(TAG, "接收的："+ result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CaptchaResponse captchaResponse = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("CaptchaResponse", result);


            try {
                captchaResponse = JsonMananger.jsonToBean(result, CaptchaResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "Get Captcha occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return captchaResponse;

    }
//获取验证码(取回密码)
    public CommonResponse getCaptchaForget(String cellPhone) throws HttpException
    {
        String uri = getURL("kp_dyz/cli-comm-sendpwdmsg.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("phone_no",cellPhone)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("getCaptchaForget", result);


            try {
                commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "getCaptchaForget occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;

    }
//注册
public CommonResponse register(String cellPhone, String password, String captcha) throws HttpException
{
    String uri = getURL("kp_dyz/cli-comm-resetPassword.php");
    Response response=null;
    try {
        response=OkHttpUtils
                .get()
                .addParams("nick_name",cellPhone)
                .addParams("pwd",password)
                .addParams("rand_code",captcha)
                .url(uri)
                .build()
                .execute();
        result =response.body().string();
    } catch (IOException e) {
        e.printStackTrace();
    }
    CommonResponse commonResponse = null;
    if (!TextUtils.isEmpty(result)) {
        NLog.e("resetPassword", result);

        try {
            commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
        } catch (JSONException e) {
            NLog.d(TAG, "CommonResponse occurs JSONException e=" + e.toString());
            return null;
        }
    }
    return commonResponse;

}
//重置密码
    public CommonResponse resetPassword(String cellPhone, String password, String captcha) throws HttpException
    {
        String uri = getURL("kp_dyz/cli-comm-setpwd.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("nick_name",cellPhone)
                    .addParams("new_pwd",password)
                    .addParams("rand_code",captcha)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("resetPassword", result);

            try {
                commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "CommonResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;

    }
//取个人资料
    public UserInfoResponse getInfo() throws HttpException {
        String uri = getURL("kp_dyz/cli-api-userinfo.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("access_key",token)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserInfoResponse userInfoResponse = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("getInfo", result);

            try {
                userInfoResponse = JsonMananger.jsonToBean(result, UserInfoResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "UserInfoResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return userInfoResponse;
    }

//版本检查
    public VersionResponse checkVersion() throws HttpException {
        String uri = getURL("version.txt");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VersionResponse versionResponse = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("checkVersion", result);


            try {
                versionResponse = JsonMananger.jsonToBean(result, VersionResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "Get VersionResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return versionResponse;
    }

    public TagResponse getTags() throws HttpException {
        String uri = getURL("kp_dyz/cli-comm-classtag.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TagResponse tagResponse = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("getTags", result);

            try {
                tagResponse = JsonMananger.jsonToBean(result, TagResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "TagResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return tagResponse;

    }

    public AdResponse getAds() throws HttpException{
        String uri = getURL("kp_dyz/cli-comm-classslideimg.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("class_id","5")
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdResponse adResponse = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("getAds", result);

            try {
                adResponse = JsonMananger.jsonToBean(result, AdResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "AdResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return adResponse;

    }
//获取推荐
    public RecommendResponse getRecommends()throws HttpException {
        String uri = getURL("kp_dyz/cli-comm-recommend.php");
        //String uri = "http://test.nannvyou.cn/a.txt";
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RecommendResponse recommendResponse = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("getRecommends", result);

            try {
                recommendResponse = JsonMananger.jsonToBean(result, RecommendResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "RecommendResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return recommendResponse;
    }
//项详情(图文、视频、音频)
    public DetailResponse getDetail(String itemId) throws HttpException{
        String uri = getURL("kp_dyz/cli-comm-itemdetail.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.ITEMID,itemId)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DetailResponse detailResponse = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("getDetail", result);

            try {
                detailResponse = JsonMananger.jsonToBean(result, DetailResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "DetailResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return detailResponse;
    }
//收藏
        public CommonResponse addFavor(String itemId) throws HttpException{
            String uri = getURL("kp_dyz/cli-api-setcollect.php");
            Response response=null;
            try {
                response=OkHttpUtils
                        .get()
                        .addParams(XtdConst.ITEMID,itemId)
                        .addParams(XtdConst.ACCESS_TOKEN,token)
                        .url(uri)
                        .build()
                        .execute();
                result =response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            CommonResponse commonResponse = null;
            if (!TextUtils.isEmpty(result)) {
                NLog.e("getDetail", result);

                try {
                    commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
                } catch (JSONException e) {
                    NLog.d(TAG, "DetailResponse occurs JSONException e=" + e.toString());
                    return null;
                }
            }
            return commonResponse;
    }

    public RelateRecommendResponse getRelateRecommend(String classId) throws HttpException{
        String uri = getURL("kp_dyz/cli-comm-classrecommend.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.CLASSID,classId)
                    .addParams("item_count","6")
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        RelateRecommendResponse relateRecommend = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("getRelateRecommend", result);

            try {
                relateRecommend = JsonMananger.jsonToBean(result, RelateRecommendResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "RelateRecommendResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return relateRecommend;
    }
    //获取分类项列表（动画）
    public ClassListResponse getAnimations(String class_id,String last_item_id,String item_count) throws HttpException{
        String uri = getURL("kp_dyz/cli-comm-classlist.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("class_id",class_id)
                    .addParams("last_item_id",last_item_id)
                    .addParams("item_count",item_count)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClassListResponse  classListResponse= null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("getAnimations", result);

            try {
                classListResponse = JsonMananger.jsonToBean(result, ClassListResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "ClassListResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return classListResponse;
    }

    public SongDetailResponse getSongAlbumDetail(String itemId) throws HttpException {
        String uri = getURL("kp_dyz/cli-comm-albumdetail.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.ITEMID,itemId)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SongDetailResponse detailResponse = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("getSongAlbumDetail", result);

            try {
                detailResponse = JsonMananger.jsonToBean(result, SongDetailResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "DetailResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return detailResponse;
    }

    public ShowResponse getShowList() throws HttpException {
        String uri = getURL("kp_dyz/cli-comm-showitemlist.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("list_count","12")
                    .addParams("last_show_id","0")
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ShowResponse showResponse = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.e("getShowList", result);

            try {
                showResponse = JsonMananger.jsonToBean(result, ShowResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "ShowResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return showResponse;
    }
}
