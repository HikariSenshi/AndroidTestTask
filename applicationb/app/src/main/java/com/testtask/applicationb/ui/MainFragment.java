package com.testtask.applicationb.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.testtask.applicationb.R;
import com.testtask.applicationb.model.interactor.MainInteractor;
import com.testtask.applicationb.model.repository.MainRepository;
import com.testtask.applicationb.model.system.ImageDownloadService;
import com.testtask.applicationb.presentation.presenter.MainPresenter;
import com.testtask.applicationb.presentation.view.MainView;

import static com.testtask.applicationb.utils.Consts.IMAGE_STATUS;
import static com.testtask.applicationb.utils.Consts.IMAGE_URL;
import static com.testtask.applicationb.utils.Consts.URL_KEY;

public class MainFragment extends Fragment implements MainView {

    View view;
    ImageView imageView;

    MainPresenter presenter;

    public String TAG = "tag";

    public static MainFragment getInstance(String imageUrl, int status) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(IMAGE_URL, imageUrl);
        args.putInt(IMAGE_STATUS, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        if (presenter == null){
            presenter = new MainPresenter(this, new MainInteractor(new MainRepository(getActivity())));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageView = view.findViewById(R.id.main_fragment_iv);

        presenter.checkArgs(getArguments().getString(IMAGE_URL), getArguments().getInt(IMAGE_STATUS));
    }

    @Override
    public void showImage(String imageURl, RequestListener<Bitmap> requestListener) {
        RequestBuilder<Bitmap> requestBuilder = Glide.with(this)
                .asBitmap()
                .load(imageURl)
                .thumbnail(0.5f)
                .apply(new RequestOptions()
                        .error(R.drawable.ic_broken_image_black_24dp)
                        .diskCacheStrategy(DiskCacheStrategy.NONE));

        if (requestListener != null) {
            requestBuilder.listener(requestListener).into(imageView);
        } else {
            requestBuilder.into(imageView);
        }
    }

    @Override
    public void showErrorImage(){
        if (imageView != null) {
            imageView.setImageDrawable(getActivity()
                    .getResources().getDrawable(R.drawable.ic_broken_image_black_24dp));
        }
    }

    @Override
    public void startIntentService(String imageUrl) {
        Intent intent = new Intent(getActivity(), ImageDownloadService.class);
        intent.putExtra(URL_KEY, imageUrl);
        ImageDownloadService.enqueueWork(getActivity(), intent);
    }

    @Override
    public void showClosingToast() {
        Toast.makeText(getActivity(), getString(R.string.not_separate_messsge), Toast.LENGTH_LONG).show();
        closeAppWithDelay(10);
    }

    @Override
    public void downloadImageWithPerms(String imageUrl) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                presenter.downloadImage(imageUrl);
            } else {
                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            presenter.downloadImage(imageUrl);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            presenter.downloadImage(getArguments().getString(IMAGE_URL));
        }
    }

    @Override
    public void closeAppWithDelay(int seconds) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (getActivity() != null) getActivity().finish();
                System.exit(0);
            }
        }, seconds * 1000);
    }

}
