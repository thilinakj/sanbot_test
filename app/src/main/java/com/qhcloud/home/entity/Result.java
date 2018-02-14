package com.qhcloud.home.entity;


import android.graphics.Bitmap;
import android.widget.ImageView;

public interface Result {

    void onSuccess(String path, ImageView imageView, Bitmap bitmap, int width, int height);

    void onFailed(String path, ImageView imageView);
}
