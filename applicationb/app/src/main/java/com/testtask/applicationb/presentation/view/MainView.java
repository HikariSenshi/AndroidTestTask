package com.testtask.applicationb.presentation.view;

import android.graphics.Bitmap;

import com.bumptech.glide.request.RequestListener;

public interface MainView {

    void showImage(String imageURl, RequestListener<Bitmap> requestListener);
    void showErrorImage();
    void startIntentService(String imageUrl);
    void downloadImageWithPerms(String imageUrl);
    void showClosingToast();
    void closeAppWithDelay(int seconds);

}
