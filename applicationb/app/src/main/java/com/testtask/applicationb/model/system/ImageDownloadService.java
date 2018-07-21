package com.testtask.applicationb.model.system;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;

import com.testtask.applicationb.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.testtask.applicationb.utils.Consts.DIRECTORY_PATH;
import static com.testtask.applicationb.utils.Consts.URL_KEY;

public class ImageDownloadService extends JobIntentService {

    public static final int JOB_ID = 101;

    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, ImageDownloadService.class, JOB_ID, work);
    }

    // Must create a default constructor
    public ImageDownloadService() {
        super();
    }

    // Download the image
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Log.d("DEBUG", "ImageDownloadService triggered");

        // Extract additional values from the bundle
        String imageUrl = intent.getStringExtra(URL_KEY);
        // Saving image
        saveImage(getBitmap(imageUrl));
    }

    private File createFile() {
        File path;
        if(Utils.canWriteOnExternalStorage()) {
            //create directory in external storage, but for some device it will be strangely storage emulated
            path = new File(Environment.getExternalStorageDirectory(), DIRECTORY_PATH);
        } else {
            path = new File("/storage/emulated/0/" + DIRECTORY_PATH);
        }

        if (!path.exists()) {
            path.mkdirs();
        }

        return new File(path, getImageName());
    }


    protected Bitmap getBitmap(String address) {
        // Convert string to URL
        URL url = getUrlFromString(address);
        // Get input stream
        InputStream in = getInputStream(url);
        // Return decoded bitmap result
        return decodeBitmap(in);
    }

    private String getImageName(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
        Date now = new Date();
        return "image_" + formatter.format(now) + ".jpg";
    }

    public void saveImage(Bitmap bitmapImage) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(createFile());
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private URL getUrlFromString(String address) {
        URL url;
        try {
            url = new URL(address);
        } catch (MalformedURLException e1) {
            url = null;
        }
        return url;
    }

    private InputStream getInputStream(URL url) {
        InputStream in;
        // Open connection
        URLConnection conn;
        try {
            conn = url.openConnection();
            conn.connect();
            in = conn.getInputStream();
        } catch (IOException e) {
            in = null;
        }
        return in;
    }

    private Bitmap decodeBitmap(InputStream in) {
        Bitmap bitmap;
        try {
            // Turn response into Bitmap
            bitmap = BitmapFactory.decodeStream(in);
            // Close the input stream
            if(in != null) in.close();
        } catch (IOException e) {
            bitmap = null;
        }
        return bitmap;
    }

}
