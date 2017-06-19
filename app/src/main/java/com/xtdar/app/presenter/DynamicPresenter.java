package com.xtdar.app.presenter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.LocalMediaLoader;
import com.luck.picture.lib.model.PictureConfig;
import com.xtdar.app.common.CommonTools;
import com.xtdar.app.view.activity.DynamicActivity;
import com.xtdar.app.widget.FullyGridLayoutManager;
import com.yalantis.ucrop.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import com.xtdar.app.R;
import com.xtdar.app.adapter.GridImageAdapter;

/**
 * Created by hmxbanz on 2017/4/5.
 */

public class DynamicPresenter {
    private Context mContext;
    private DynamicActivity mActivity;
    private RecyclerView recyclerView;
    private GridImageAdapter adapter;
//选择图片配置
    private int selectMode = FunctionConfig.MODE_MULTIPLE;
    private int maxSelectNum = 3;// 图片最大可选数量
    private EditText et_w, et_h, et_compress_width, et_compress_height;
    private LinearLayout ll_luban_wh;
    private boolean isShow = true;
    private int selectType = LocalMediaLoader.TYPE_IMAGE;
    private int copyMode = FunctionConfig.COPY_MODEL_DEFAULT;//CROP_MODEL_DEFAULT
    private boolean enablePreview = true;
    private boolean isPreviewVideo = true;
    private boolean enableCrop = false;
    private boolean theme = false;
    private boolean selectImageType = false;
    private int cropW = 0;//裁剪宽度
    private int cropH = 0;//裁剪高度
    private int compressW = 0;//加缩宽度
    private int compressH = 0;//加缩高度
    private boolean isCompress = false;
    private boolean isCheckNumMode = false;
    private int compressFlag = 1;// 1 系统自带压缩 2 luban压缩
    private List<LocalMedia> selectMedia = new ArrayList<>();

    public DynamicPresenter(Context context){
        this.mContext=context;
        this.mActivity= (DynamicActivity) context;
    }
    public void init(){
        recyclerView = (RecyclerView) mActivity.findViewById(R.id.recycler);
        FullyGridLayoutManager manager = new FullyGridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        adapter = new GridImageAdapter(mContext, onAddPicClickListener);
        adapter.setSelectMax(3);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                // 这里可预览图片
                PictureConfig.getPictureConfig().externalPicturePreview(mContext, position, selectMedia);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    /**
     * 删除图片回调接口
     */

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int type, int position) {
            switch (type) {
                case 0:
                    // 进入相册
                    /**
                     * type --> 1图片 or 2视频
                     * copyMode -->裁剪比例，默认、1:1、3:4、3:2、16:9
                     * maxSelectNum --> 可选择图片的数量
                     * selectMode         --> 单选 or 多选
                     * isShow       --> 是否显示拍照选项 这里自动根据type 启动拍照或录视频
                     * isPreview    --> 是否打开预览选项
                     * isCrop       --> 是否打开剪切选项
                     * isPreviewVideo -->是否预览视频(播放) mode or 多选有效
                     * ThemeStyle -->主题颜色
                     * CheckedBoxDrawable -->图片勾选样式
                     * cropW-->裁剪宽度 值不能小于100  如果值大于图片原始宽高 将返回原图大小
                     * cropH-->裁剪高度 值不能小于100
                     * isCompress -->是否压缩图片
                     * setEnablePixelCompress 是否启用像素压缩
                     * setEnableQualityCompress 是否启用质量压缩
                     * setRecordVideoSecond 录视频的秒数，默认不限制
                     * setRecordVideoDefinition 视频清晰度  Constants.HIGH 清晰  Constants.ORDINARY 低质量
                     * setImageSpanCount -->每行显示个数
                     * setCheckNumMode 是否显示QQ选择风格(带数字效果)
                     * setPreviewColor 预览文字颜色
                     * setCompleteColor 完成文字颜色
                     * setPreviewBottomBgColor 预览界面底部背景色
                     * setBottomBgColor 选择图片页面底部背景色
                     * setCompressQuality 设置裁剪质量，默认无损裁剪
                     * setSelectMedia 已选择的图片
                     * setCompressFlag 1为系统自带压缩  2为第三方luban压缩
                     * 注意-->type为2时 设置isPreview or isCrop 无效
                     * 注意：Options可以为空，默认标准模式
                     */
                    String ws = null;
                    String hs = null;

                    if (!CommonTools.isNull(ws) && !CommonTools.isNull(hs)) {
                        cropW = Integer.parseInt(ws);
                        cropH = Integer.parseInt(hs);
                    }

                    int selector = R.drawable.select_cb;

                    FunctionConfig config = new FunctionConfig();
                    config.setType(selectType);
                    config.setCopyMode(copyMode);
                    config.setCompress(isCompress);
                    config.setEnablePixelCompress(true);
                    config.setEnableQualityCompress(true);
                    config.setMaxSelectNum(maxSelectNum);
                    config.setSelectMode(selectMode);
                    config.setShowCamera(isShow);
                    config.setEnablePreview(enablePreview);
                    config.setEnableCrop(enableCrop);
                    config.setPreviewVideo(isPreviewVideo);
                    config.setRecordVideoDefinition(FunctionConfig.HIGH);// 视频清晰度
                    config.setRecordVideoSecond(60);// 视频秒数
                    config.setCropW(cropW);
                    config.setCropH(cropH);
                    config.setCheckNumMode(isCheckNumMode);
                    config.setCompressQuality(100);
                    config.setImageSpanCount(4);
                    config.setSelectMedia(selectMedia);
                    config.setCompressFlag(compressFlag);
                    config.setCompressW(compressW);
                    config.setCompressH(compressH);

                    if (theme) {
                        config.setThemeStyle(ContextCompat.getColor(mContext, R.color.blue));
                        // 可以自定义底部 预览 完成 文字的颜色和背景色
                        if (!isCheckNumMode) {
                            // QQ 风格模式下 这里自己搭配颜色，使用蓝色可能会不好看
                            config.setPreviewColor(ContextCompat.getColor(mContext, R.color.white));
                            config.setCompleteColor(ContextCompat.getColor(mContext, R.color.white));
                            config.setPreviewBottomBgColor(ContextCompat.getColor(mContext, R.color.blue));
                            config.setBottomBgColor(ContextCompat.getColor(mContext, R.color.blue));
                        }
                    }
                    if (selectImageType) {
                        config.setCheckedBoxDrawable(selector);
                    }

                    // 先初始化参数配置，在启动相册
                    PictureConfig.init(config);
                    PictureConfig.getPictureConfig().openPhoto(mContext, resultCallback);

                    // 只拍照
                    //PictureConfig.getPictureConfig().startOpenCamera(mContext, resultCallback);
                    break;
                case 1:
                    // 删除图片
                    selectMedia.remove(position);
                    adapter.notifyItemRemoved(position);
                    break;
            }
        }
    };



    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            selectMedia = resultList;
            Log.i("callBack_result", selectMedia.size() + "");
            if (selectMedia != null) {
                adapter.setList(selectMedia);
                adapter.notifyDataSetChanged();
            }
        }
    };

}
