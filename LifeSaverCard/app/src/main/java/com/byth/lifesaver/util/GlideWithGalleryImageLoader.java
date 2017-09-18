package com.byth.lifesaver.util;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.byth.lifesaver.R;

import cn.finalteam.galleryfinal.widget.GFImageView;

/**
 * Created by Administrator on 2017/7/5 0005.
 * galleryFinal图片加载工具类
 */

public class GlideWithGalleryImageLoader implements cn.finalteam.galleryfinal.ImageLoader {
    private static volatile GlideWithGalleryImageLoader loader;

    public static GlideWithGalleryImageLoader getInstance() {
        if (loader == null) {
            synchronized (GlideWithGalleryImageLoader.class) {
                if (loader == null) {
                    loader = new GlideWithGalleryImageLoader();
                }
            }
        }
        return loader;
    }


    @Override
    public void displayImage(Activity activity, String path, final GFImageView imageView, Drawable defaultDrawable, int width, int height) {
        Glide.with(activity)
                .load("file://" + path)
                .placeholder(defaultDrawable)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.NONE)//不缓存到SD卡
                .skipMemoryCache(true)
                .into(new ImageViewTarget<GlideDrawable>(imageView) {
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        imageView.setImageDrawable(resource);
                    }

                    @Override
                    public void setRequest(Request request) {
                        imageView.setTag(R.id.adapter_item_tag_key, request);
                    }

                    @Override
                    public Request getRequest() {
                        return (Request) imageView.getTag(R.id.adapter_item_tag_key);
                    }
                });
    }

    @Override
    public void clearMemoryCache() {

    }
}
