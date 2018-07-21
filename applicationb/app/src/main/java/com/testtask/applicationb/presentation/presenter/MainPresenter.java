package com.testtask.applicationb.presentation.presenter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.testtask.applicationb.model.interactor.MainInteractor;
import com.testtask.applicationb.presentation.view.MainView;
import com.testtask.applicationb.utils.Utils;

import java.util.logging.Handler;

import static com.testtask.applicationb.utils.Consts.CARD_PATH;
import static com.testtask.applicationb.utils.Consts.DOWNLOADED;
import static com.testtask.applicationb.utils.Consts.ERROR;
import static com.testtask.applicationb.utils.Consts.IMAGE_STATUS_DEFAULT;
import static com.testtask.applicationb.utils.Consts.STORAGE_PATH;
import static com.testtask.applicationb.utils.Consts.UNDEFINED;

public class MainPresenter {

    private MainView view;
    private MainInteractor interactor;

    private boolean isSaved = false;
    private boolean isUpdated = false;

    public MainPresenter(MainView view, MainInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    public void checkArgs(String imageUrl, int imageStatus) {
        if (imageUrl != null && imageStatus == IMAGE_STATUS_DEFAULT) {
            view.showImage(imageUrl, getSaveListener(imageUrl));
            tryToSaveUndefined(imageUrl);
        } else if (imageStatus != IMAGE_STATUS_DEFAULT) {
            switch (imageStatus) {
                case DOWNLOADED:
                    deleteImage(imageUrl);
                    break;
                default:
                    //update status, try to load again
                    view.showImage(imageUrl, getUpdateListener(imageUrl));
                    tryToUpdateUndefined(imageUrl);
            }
        } else {
            view.showClosingToast();
        }
    }

    private void insertImage(String imageUrl, int status) {
        interactor.insertImage(imageUrl, status, interactor.getDate());
    }

    private void updateImage(String imageUrl, int status) {
        interactor.updateImage(imageUrl, status);
    }

    private void deleteImage(String imageUrl) {
        view.showImage(imageUrl, null);
        interactor.deleteImage(imageUrl);
        view.downloadImageWithPerms(imageUrl);
        view.closeAppWithDelay(15);
    }

    public void downloadImage(String imageUrl) {
        view.startIntentService(imageUrl);
    }

    private RequestListener<Bitmap> getUpdateListener(final String imageUrl) {
        return new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                if (!isUpdated) {
                    Log.d("Saving", "Saved with error status");
                    updateImage(imageUrl, ERROR);
                }
                isUpdated = true;
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                if (!isUpdated) {
                    Log.d("Saving", "Saved with downloaded status");
                    updateImage(imageUrl, DOWNLOADED);
                }
                isUpdated = true;
                return false;
            }
        };
    }

    private RequestListener<Bitmap> getSaveListener(final String imageUrl) {
        return new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                if (!isSaved) {
                    Log.d("Saving", "Saved with error status" );
                    insertImage(imageUrl, ERROR);
                }
                isSaved = true;
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                if (!isSaved) {
                    Log.d("Saving", "Saved with downloaded status");
                    insertImage(imageUrl, DOWNLOADED);
                }
                isSaved = true;
                return false;
            }
        };
    }

    private void tryToSaveUndefined(final String imageUrl) {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isSaved) {
                    insertImage(imageUrl, UNDEFINED);
                    view.showErrorImage();
                }
                isSaved = true;
            }
        }, 10000);
    }

    private void tryToUpdateUndefined(final String imageUrl){
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isUpdated) {
                    updateImage(imageUrl, UNDEFINED);
                    view.showErrorImage();
                }
                isUpdated = true;
            }
        }, 10000);
    }

}
