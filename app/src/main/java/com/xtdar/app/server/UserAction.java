package com.xtdar.app.server;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.alibaba.fastjson.JSONException;
import com.xtdar.app.XtdConst;
import com.xtdar.app.common.NLog;
import com.xtdar.app.common.json.JsonMananger;
import com.xtdar.app.server.request.LoginRequest;
import com.xtdar.app.server.request.UpdateRequest;
import com.xtdar.app.server.response.AdResponse;
import com.xtdar.app.server.response.BindResponse;
import com.xtdar.app.server.response.CaptchaResponse;
import com.xtdar.app.server.response.ClassListResponse;
import com.xtdar.app.server.response.CommentResponse;
import com.xtdar.app.server.response.CommonResponse;
import com.xtdar.app.server.response.DetailResponse;
import com.xtdar.app.server.response.FavorResponse;
import com.xtdar.app.server.response.GameCheckResponse;
import com.xtdar.app.server.response.GameListResponse;
import com.xtdar.app.server.response.HelpResponse;
import com.xtdar.app.server.response.LoginResponse;
import com.xtdar.app.server.response.MyCommentResponse;
import com.xtdar.app.server.response.MyDevicesResponse;
import com.xtdar.app.server.response.PersonMsgResponse;
import com.xtdar.app.server.response.RecommendResponse;
import com.xtdar.app.server.response.RelateRecommendResponse;
import com.xtdar.app.server.response.ShopMoreResponse;
import com.xtdar.app.server.response.ShowDetailResponse;
import com.xtdar.app.server.response.ShowResponse;
import com.xtdar.app.server.response.SongDetailResponse;
import com.xtdar.app.server.response.SysMsgResponse;
import com.xtdar.app.server.response.TagResponse;
import com.xtdar.app.server.response.TaobaoResponse;
import com.xtdar.app.server.response.UnReadMsgResponse;
import com.xtdar.app.server.response.UserInfoResponse;
import com.xtdar.app.server.response.VersionResponse;
import com.xtdar.app.server.response.WxLoginResponse;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.w3c.dom.Comment;

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
        String uri = getURL("cli-comm-login.php");
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
            NLog.d("login 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginResponse loginResponse = null;
        if (!TextUtils.isEmpty(result)) {
            NLog.d("接收的", result);

            try {
                loginResponse = JsonMananger.jsonToBean(result, LoginResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "LoginResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return loginResponse;

    }

    //微信绑定
    public CommonResponse wxBind(String unionid,String cellPhone, String pwd) throws HttpException
    {
        String uri = getURL("cli-tplogin-bindwx.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("unionid",unionid)
                    .addParams("cell_phone",cellPhone)
                    .addParams("pwd",pwd)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("wxBind 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "CommonResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;

    }

    //微信登录请求
    public WxLoginResponse wxOpenId(String unionid) throws HttpException
    {
        String uri = getURL("cli-tplogin-wxlogin.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("unionid",unionid)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("wxOpenId 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WxLoginResponse wxLoginResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                wxLoginResponse = JsonMananger.jsonToBean(result, WxLoginResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "wxLoginResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return wxLoginResponse;

    }

    //QQ登录请求
    public WxLoginResponse qqOpenId(String unionid) throws HttpException
    {
        String uri = getURL("cli-tplogin-qqlogin.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("open_id",unionid)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("qqOpenId 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WxLoginResponse wxLoginResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                wxLoginResponse = JsonMananger.jsonToBean(result, WxLoginResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "wxLoginResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return wxLoginResponse;

    }
    //QQ绑定
    public CommonResponse qqBind(String unionid,String cellPhone, String pwd) throws HttpException
    {
        String uri = getURL("cli-tplogin-bindqq.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("open_id",unionid)
                    .addParams("cell_phone",cellPhone)
                    .addParams("pwd",pwd)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("qqBind 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "CommonResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;

    }
    //微博登录请求
    public WxLoginResponse wbOpenId(String unionid) throws HttpException
    {
        String uri = getURL("cli-tplogin-wblogin.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("uid",unionid)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("wbOpenId 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        WxLoginResponse wxLoginResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                wxLoginResponse = JsonMananger.jsonToBean(result, WxLoginResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "wbLoginResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return wxLoginResponse;

    }
    //微博绑定
    public CommonResponse wbBind(String unionid,String cellPhone, String pwd) throws HttpException
    {
        String uri = getURL("cli-tplogin-bindwb.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("uid",unionid)
                    .addParams("cell_phone",cellPhone)
                    .addParams("pwd",pwd)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("wbBind 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "CommonResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;

    }

    //上传极光注册ID
    public CommonResponse upLoadRid(String rid) throws HttpException
    {
        String uri = getURL("cli-api-bindregistrationid.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.ACCESS_TOKEN,token)
                    .addParams("registration_id",rid)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("upLoadRid 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "CommonResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;

    }
    //获取验证码
    public CaptchaResponse getCaptcha(String cellPhone) throws HttpException
    {
        String uri = getURL("cli-comm-sendregmsg.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("phone_no",cellPhone)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getCaptcha 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CaptchaResponse captchaResponse = null;
        if (!TextUtils.isEmpty(result)) {
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
        String uri = getURL("cli-comm-sendpwdmsg.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("phone_no",cellPhone)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getCaptchaForget 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
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
        String uri = getURL("cli-comm-register.php");
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
            NLog.d("register 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
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
        String uri = getURL("cli-comm-setpwd.php");
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
            NLog.d("resetPassword 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
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
        String uri = getURL("cli-api-userinfo.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("access_key",token)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getInfo 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UserInfoResponse userInfoResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                userInfoResponse = JsonMananger.jsonToBean(result, UserInfoResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "UserInfoResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return userInfoResponse;
    }
    //取消息未读数
    public UnReadMsgResponse getUnReadMsg() throws HttpException {
        String uri = getURL("cli-api-needreadcount.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("access_key",token)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getUnReadMsg 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UnReadMsgResponse unReadMsgResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                unReadMsgResponse = JsonMananger.jsonToBean(result, UnReadMsgResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "UnReadMsgResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return unReadMsgResponse;
    }

    //版本检查
    public VersionResponse checkVersion() throws HttpException {
        String uri =getURL("app_source/dl/version.txt");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("checkVersion 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        VersionResponse versionResponse = null;
        if (!TextUtils.isEmpty(result)) {
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
        String uri = getURL("cli-comm-classtag.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getTags 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TagResponse tagResponse = null;
        if (!TextUtils.isEmpty(result)) {
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
        String uri = getURL("cli-comm-classslideimg.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("class_id","5")
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getAds 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AdResponse adResponse = null;
        if (!TextUtils.isEmpty(result)) {
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
        String uri = getURL("cli-comm-recommend.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getRecommends 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RecommendResponse recommendResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                recommendResponse = JsonMananger.jsonToBean(result, RecommendResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "RecommendResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return recommendResponse;
    }
    //获取淘设备
    public TaobaoResponse getTaobao()throws HttpException {
        String uri = getURL("cli-dgc-devicemain.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getTaobao 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TaobaoResponse taobaoResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                taobaoResponse = JsonMananger.jsonToBean(result, TaobaoResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "TaobaoResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return taobaoResponse;
    }
    //项详情(图文、视频、音频)
    public DetailResponse getDetail(String itemId) throws HttpException{
        String uri = getURL("cli-comm-itemdetail.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.ITEMID,itemId)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getDetail 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DetailResponse detailResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                detailResponse = JsonMananger.jsonToBean(result, DetailResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "DetailResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return detailResponse;
    }
    //秀场项详情
    public ShowDetailResponse getShowDetail(String itemId) throws HttpException{
        String uri = getURL("cli-comm-showdetail.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("show_id",itemId)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getShowDetail 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ShowDetailResponse showDetailResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                showDetailResponse = JsonMananger.jsonToBean(result, ShowDetailResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "ShowDetailResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return showDetailResponse;
    }
    //收藏
    public CommonResponse addFavor(String itemId) throws HttpException{
        String uri = getURL("cli-api-setcollect.php");
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
            NLog.d("addFavor 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "DetailResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;
    }
    //评论
    public CommonResponse addComment(String itemId,String comment_tag,String comment) throws HttpException{
        String uri = getURL("cli-api-postcomment.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.ITEMID,itemId)
                    .addParams("comment_tag",comment_tag)
                    .addParams("comment",comment)
                    .addParams(XtdConst.ACCESS_TOKEN,token)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("addComment 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
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
        String uri = getURL("cli-comm-classrecommend.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.CLASSID,classId)
                    .addParams("item_count","4")
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getRelateRecommend 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RelateRecommendResponse relateRecommend = null;
        if (!TextUtils.isEmpty(result)) {
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
        String uri = getURL("cli-comm-classlist.php");
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
            NLog.d("getAnimations 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClassListResponse  classListResponse= null;
        if (!TextUtils.isEmpty(result)) {
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
        String uri = getURL("cli-comm-albumdetail.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.ITEMID,itemId)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getSongAlbumDetail 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SongDetailResponse detailResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                detailResponse = JsonMananger.jsonToBean(result, SongDetailResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "DetailResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return detailResponse;
    }

    public ShowResponse getShowList(String last_show_id,String list_count) throws HttpException {
        String uri = getURL("cli-comm-showitemlist.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("list_count",list_count)
                    .addParams("last_show_id",last_show_id)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getShowList 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ShowResponse showResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                showResponse = JsonMananger.jsonToBean(result, ShowResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "ShowResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return showResponse;
    }
    //我的视频
    public ShowResponse getMyVideo() throws HttpException {
        String uri = getURL("cli-api-myshowlist.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("access_key",token)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getMyVideo 接收的", result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        ShowResponse showResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                showResponse = JsonMananger.jsonToBean(result, ShowResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "ShowResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return showResponse;
    }

    public String getProtocol() {
        String uri = "app_source/dl/protocol.html";
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getProtocol 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(result)) {
            return result;
        }
        return null;
    }

    public HelpResponse getHelps() throws HttpException {
        String uri = getURL("cli-dgc-helplist.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getHelps 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();

        }
        HelpResponse helpResponse = null;
        if (!TextUtils.isEmpty(result)) {
            helpResponse = JsonMananger.jsonToBean(result, HelpResponse.class);
        }
        return helpResponse;
    }


    public CommonResponse save(String nickName) throws HttpException {
        String uri = getURL("cli-api-setuserinfo.php");
        String json = JsonMananger.beanToJson(new UpdateRequest(nickName,token));
        Log.w(TAG, "请求的："+json);
        Response response=null;
        try {
            response=OkHttpUtils
                    //.postString()
                    //.mediaType(MediaType.parse("application/json; charset=utf-8"))
                    //.content(json)//.content(new Gson().toJson(new User("zhy", "123")))
                    .get()
                    .url(uri)
                    .addParams("access_key",token)
                    .addParams("nick_name",nickName)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("save 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();

        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
        }
        return commonResponse;

    }

    public BindResponse bindDevice(String mac) throws HttpException{
        String uri = getURL("cli-dg-binddevicebymac.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .url(uri)
                    .addParams("access_key",token)
                    .addParams("mac_address",mac)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("bindDevice 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();

        }
        BindResponse bindResponse = null;
        if (!TextUtils.isEmpty(result)) {
            bindResponse = JsonMananger.jsonToBean(result, BindResponse.class);
        }
        return bindResponse;
    }

    public MyDevicesResponse getDevices() throws HttpException {
        String uri = getURL("cli-dg-mydevicelist.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .url(uri)
                    .addParams("access_key",token)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getDevices 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();

        }
        MyDevicesResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            commonResponse = JsonMananger.jsonToBean(result, MyDevicesResponse.class);
        }
        return commonResponse;
    }

    //获取游戏列表（射击）
    public GameListResponse getShot(String game_type_id, String page_index, String item_count) throws HttpException{

        String uri = getURL("cli-dgc-gamelist.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("game_type_id",game_type_id)
                    .addParams("page_index",page_index)
                    .addParams("item_count",item_count)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getShot 接收的", game_type_id+" "+page_index+" "+item_count);
            NLog.d("getShot 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GameListResponse  gameListResponse= null;
        if (!TextUtils.isEmpty(result)) {
            try {
                gameListResponse = JsonMananger.jsonToBean(result, GameListResponse.class);
                for(GameListResponse.DataBean bean:gameListResponse.getData())
                {
                    GameListResponse.DataBean.GameConfig gameConfig = JsonMananger.jsonToBean(bean.getGame_config(), GameListResponse.DataBean.GameConfig.class);
                    bean.setGameConfig(gameConfig);
                }

            } catch (JSONException e) {
                NLog.d(TAG, "ClassListResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return gameListResponse;
    }

    public CommentResponse getComment(String itemId, String comment_tag,String last_item_id, String item_count) throws HttpException {
        String uri = getURL("cli-comm-commentlist.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("comment_tag",comment_tag)
                    .addParams("item_id",itemId)
                    .addParams("last_com_id",last_item_id)
                    .addParams("list_count",item_count)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getComment 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommentResponse  gameListResponse= null;
        if (!TextUtils.isEmpty(result)) {
            try {
                gameListResponse = JsonMananger.jsonToBean(result, CommentResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "ClassListResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return gameListResponse;

    }

    //上传头像
    public CommonResponse uploadAvatar(File imgFile) throws HttpException {
        String uri = getURL("cli-api-setimg.php");
//        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("content", "Square Logo")
//                .addFormDataPart("image", "logo-square.png",RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
//                .build();
        Response response=null;
        try {
            response=OkHttpUtils
                    .post()
                    .addParams(XtdConst.ACCESS_TOKEN,token)
                    .addFile("file", "imgFile.jpg",imgFile)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("uploadAvatar 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "uploadAvatar occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;
    }

    //发新动态
    public CommonResponse addDynamic(String content, File mp4File)throws HttpException{
        String uri = getURL("cli-api-upshow.php");
//        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("content", "Square Logo")
//                .addFormDataPart("image", "logo-square.png",RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
//                .build();

        Response response=null;
        try {
            response=OkHttpUtils
                    .post()
                    .addParams("access_key",token)
                    .addParams("title", content)
                    .addFile("file", "file.mp4",mp4File)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("addDynamic 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "addDynamic occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;
    }
    //获取收藏
    public FavorResponse getFavorList(String list_count,String last_collect_id) throws HttpException {
        String uri = getURL("cli-api-collectmp4list.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.ACCESS_TOKEN,token)
                    .addParams("last_collect_id",last_collect_id)
                    .addParams("list_count",list_count)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getFavorList 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FavorResponse  favorResponse= null;
        if (!TextUtils.isEmpty(result)) {
            try {
                favorResponse = JsonMananger.jsonToBean(result, FavorResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "FavorResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return favorResponse;
    }
    //删除收藏
    public Object delFavor(String delFavorId) throws HttpException {
        String uri = getURL("cli-api-cancelcollect.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .post()
                    .addParams(XtdConst.ACCESS_TOKEN,token)
                    .addParams("item_id", delFavorId)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("delFavor 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "delFavor occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;
    }

    //删除我的视频
    public Object delMyVideo(String delId) throws HttpException {
        String uri = getURL("cli-api-delmyshow.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .post()
                    .addParams(XtdConst.ACCESS_TOKEN,token)
                    .addParams("show_id", delId)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("delMyVideo 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "delMyVideo occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;
    }
    //获取我的评论
    public MyCommentResponse getCommentList(String list_count, String last_com_id) throws HttpException {
        String uri = getURL("cli-api-mycommentlist.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.ACCESS_TOKEN,token)
                    .addParams("last_com_id",last_com_id)
                    .addParams("list_count",list_count)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getCommentList 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MyCommentResponse  myCommentResponse= null;
        if (!TextUtils.isEmpty(result)) {
            try {
                myCommentResponse = JsonMananger.jsonToBean(result, MyCommentResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "CommentResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return myCommentResponse;
    }
    //获取更多设备
    public ShopMoreResponse getShopMore(String device_type_id, String item_count, String last_device_id) throws HttpException {
        String uri = getURL("cli-dgc-devicelist.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams("device_type_id",device_type_id)
                    .addParams("last_device_id",last_device_id)
                    .addParams("item_count",item_count)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getShopMore 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ShopMoreResponse  shopMoreResponse= null;
        if (!TextUtils.isEmpty(result)) {
            try {
                shopMoreResponse = JsonMananger.jsonToBean(result, ShopMoreResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "CommentResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return shopMoreResponse;
    }

    //获取系统消息
    public SysMsgResponse getSysMsgList(String list_count, String last_msg_id) throws HttpException {
        String uri = getURL("cli-api-sysmsglist.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.ACCESS_TOKEN,token)
                    .addParams("last_msg_id",last_msg_id)
                    .addParams("list_count",list_count)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getSysMsgList 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SysMsgResponse  sysMsgResponse= null;
        if (!TextUtils.isEmpty(result)) {
            try {
                sysMsgResponse = JsonMananger.jsonToBean(result, SysMsgResponse.class);

                for(SysMsgResponse.DataBean bean:sysMsgResponse.getData())
                {

                    if(TextUtils.isEmpty(bean.getLink()))
                    {
                        bean.setLink("{}");
                    }
                    SysMsgResponse.DataBean.LinkBean linkObj = JsonMananger.jsonToBean(bean.getLink(), SysMsgResponse.DataBean.LinkBean.class);
                    bean.setLinkObj(linkObj);
                }
            } catch (JSONException e) {
                NLog.d(TAG, "SysMsgResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return sysMsgResponse;
    }
    //获取个人消息
    public PersonMsgResponse getPersonMsgList(String list_count, String last_msg_id) throws HttpException {
        String uri = getURL("cli-api-mymsglist.php");
        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.ACCESS_TOKEN,token)
                    .addParams("last_msg_id",last_msg_id)
                    .addParams("list_count",list_count)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("getPersonMsgList 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PersonMsgResponse  personMsgResponse= null;
        if (!TextUtils.isEmpty(result)) {
            try {
                personMsgResponse = JsonMananger.jsonToBean(result, PersonMsgResponse.class);

                for(PersonMsgResponse.DataBean bean:personMsgResponse.getData())
                {

                    if(TextUtils.isEmpty(bean.getLink()))
                    {
                        bean.setLink("{}");
                    }
                    PersonMsgResponse.DataBean.LinkBean linkObj = JsonMananger.jsonToBean(bean.getLink(), PersonMsgResponse.DataBean.LinkBean.class);
                    bean.setLinkObj(linkObj);
                }
            } catch (JSONException e) {
                NLog.d(TAG, "PersonMsgResponse occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return personMsgResponse;
    }
    //检查游戏是否可玩
    public GameCheckResponse gameCheck(String gameName) throws HttpException{
        String uri = getURL("cli-dg-checkdevicegame.php");

        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.ACCESS_TOKEN,token)
                    .addParams("game_id",gameName)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("gameCheck 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GameCheckResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                commonResponse = JsonMananger.jsonToBean(result, GameCheckResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "feedback occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;
    }

    //用户反馈
    public CommonResponse feedback(String reportContent, String cellphone) throws HttpException {
        String uri = getURL("cli-api-postfeedback.php");

        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.ACCESS_TOKEN,token)
                    .addParams("feedback",reportContent)
                    .addParams("contact",cellphone)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("feedback 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "feedback occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;
    }

    public CommonResponse unBindDevice(String deviceId) throws HttpException {
        String uri = getURL("cli-dg-unbinddevice.php");

        Response response=null;
        try {
            response=OkHttpUtils
                    .get()
                    .addParams(XtdConst.ACCESS_TOKEN,token)
                    .addParams("bind_device_id",deviceId)
                    .url(uri)
                    .build()
                    .execute();
            result =response.body().string();
            NLog.d("unBindDevice 接收的", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CommonResponse commonResponse = null;
        if (!TextUtils.isEmpty(result)) {
            try {
                commonResponse = JsonMananger.jsonToBean(result, CommonResponse.class);
            } catch (JSONException e) {
                NLog.d(TAG, "unBindDevice occurs JSONException e=" + e.toString());
                return null;
            }
        }
        return commonResponse;
    }
}
