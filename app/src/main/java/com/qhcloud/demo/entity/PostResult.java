package com.qhcloud.demo.entity;


import android.graphics.Bitmap;
import android.widget.ImageView;

public interface PostResult {

    void postSuccess(String path, ImageView imageView, Bitmap bitmap, Result result, int width, int height);

    void postFailed(String path, ImageView imageView, Result result);
}
