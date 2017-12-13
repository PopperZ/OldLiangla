package com.brightcns.liangla.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.StringUtils;
import com.brightcns.liangla.R;
import com.brightcns.liangla.utils.ConstantUtil;
import com.brightcns.liangla.utils.GlideUtil;
import com.brightcns.liangla.utils.PreferenceUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditUserInforActivity extends AppCompatActivity{

    @BindView(R.id.iv_back_to)
    ImageView ivBack;
    @BindView(R.id.ac_set_user_save)
    TextView acSetUserSave;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ac_set_user_avatar)
    ImageView acSetUserAvatar;
    @BindView(R.id.ac_set_user_avatar_layout)
    LinearLayout acSetUserAvatarLayout;
    @BindView(R.id.ac_set_user_name)
    EditText acSetUserName;
    @BindView(R.id.ac_set_user_auto)
    EditText acSetUserAuto;
    @BindView(R.id.ac_set_user_phone)
    TextView acSetUserPhone;
    private String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_infor);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        avatar = PreferenceUtil.getString(ConstantUtil.AVATAR, "");
        GlideUtil.glideCircleAvatar(this, acSetUserAvatar, avatar);

        acSetUserName.setText(PreferenceUtil.getString(ConstantUtil.NICKNAME, ""));
        String userIconPath = PreferenceUtil.getString(ConstantUtil.AVATAR, "");
        if (StringUtils.isEmpty(userIconPath)) {
            GlideUtil.glideCircleDrawable(getApplicationContext(), acSetUserAvatar, R.mipmap.mine_oritoux);
        } else {
            GlideUtil.glideCircleAvatar(getApplicationContext(), acSetUserAvatar, userIconPath);
        }
        acSetUserPhone.setText(PreferenceUtil.getString(ConstantUtil.PHONENUM, "0"));
        acSetUserAuto.setText(PreferenceUtil.getString(ConstantUtil.AUTOGRAPH, ""));
    }

    /**
     * 修改用户头像
     */
    @OnClick(R.id.ac_set_user_avatar_layout)
    public void changeAvatar() {
        modifyAvatar();
    }



    @OnClick(R.id.iv_back_to)
    public void back(View view) {
        EditUserInforActivity.this.finish();
    }

    @OnClick(R.id.ac_set_user_save)
    public void save() {

    }



    /**
     * modify Avatar
     */
    private void modifyAvatar() {

        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(1)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.SINGLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(false)// 是否可预览视频 true or false
                .enablePreviewAudio(false) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(true)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .glideOverride(160, 160)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1,1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(false)// 是否显示gif图片 true or false
//                .compressSavePath()//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
//                .showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
//                .showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
//                .openClickSound()// 是否开启点击声音 true or false
//                .selectionMedia()// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
//                .cropCompressQuality()// 裁剪压缩质量 默认90 int
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
//                .rotateEnabled() // 裁剪是否可旋转图片 true or false
//                .scaleEnabled()// 裁剪是否可放大缩小图片 true or false
//                .videoQuality()// 视频录制质量 0 or 1 int
//                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
//                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
//                .recordVideoSecond()//视频秒数录制 默认60s int
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode) {
                    case PictureConfig.CHOOSE_REQUEST:
                        // 图片选择结果回调
                        String path = PictureSelector.obtainMultipleResult(data).get(0).getCompressPath();
                        Log.e("TAG", "onActivityResult: 工具类设置好的地址"+path);
                        if (EmptyUtils.isNotEmpty(path)) {
                            GlideUtil.glideCircleAvatar(getApplicationContext(), acSetUserAvatar, path);
                        }
                        if (!StringUtils.isEmpty(path)) {
                            Log.e("TAG", "onActivityResult: " );
                            // TODO: 2017/11/25 进行上传操作
//                            uploadPic(path);
                            /**
                             * //包括裁剪和压缩后的缓存，要在上传成功后调用，注意：需要系统sd卡权限
                             PictureFileUtils.deleteCacheDirFile(MainActivity.this);
                             */
                        }
                        break;
                }
                break;
            default:
                break;
        }
    }


}
