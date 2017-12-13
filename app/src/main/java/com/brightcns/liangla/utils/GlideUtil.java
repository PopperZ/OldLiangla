package com.brightcns.liangla.utils;

import android.content.Context;
import android.widget.ImageView;

import com.brightcns.liangla.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.MediaStoreSignature;


/**
 * Created by luis_gong on 2017/11/22.
 */

public class GlideUtil {

    public static final void glideCircleAvatar(Context context, ImageView imageView, String url) {
        RequestOptions options = new RequestOptions();
        options.sizeMultiplier(0.5f)
                .centerCrop()
                .signature(new MediaStoreSignature("image/jpeg", System.currentTimeMillis(), 0))  // 重点在这行
                .transform(new GlideCircleTransform(context))
                .error(R.mipmap.mine_oritoux);
        Glide.with(context)
                .load(url)
                .apply(options)
//                .placeholder(R.drawable.admin) //加载时显示图片
                .into(imageView);
    }

    public static final void glideCircleDrawable(Context context, ImageView imageView, int drawable) {
        RequestOptions options = new RequestOptions();
        options.sizeMultiplier(0.5f)
                .transform(new GlideCircleTransform(context));
        Glide.with(context)
                .load(drawable)
                .apply(options)
                .into(imageView);
    }
}
