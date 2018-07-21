package com.testtask.applicationb.model.repository;

import android.content.ContentProviderClient;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;

import com.testtask.applicationb.utils.Utils;

import java.util.Date;

import static com.testtask.applicationb.utils.Consts.CONTENT_URI;
import static com.testtask.applicationb.utils.Consts.DATE;
import static com.testtask.applicationb.utils.Consts.IMAGE_URL;
import static com.testtask.applicationb.utils.Consts.STATUS;

public class MainRepository {

    private Context context;

    public MainRepository(Context context) {
        this.context = context;
    }

    public String getDate() {
        return Utils.getTimeDateFormat().format(new Date());
    }

    public void insertImage(String imageUrl, int status, String date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE_URL, imageUrl);
        contentValues.put(STATUS, status);
        contentValues.put(DATE, date);

        try {
            getContentProviderClient().insert(getUri(), contentValues);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void updateImage(String imageUrl, int imageStatus){
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATUS, imageStatus);

        try {
            getContentProviderClient().update(getUri(), contentValues, getSelection(imageUrl), null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void deleteImage(String imageUrl) {
        try {
            getContentProviderClient().delete(getUri(), getSelection(imageUrl), null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private String getSelection(String imageUrl){
        return IMAGE_URL + "=" + quote(imageUrl);
    }

    private String quote(String s) {
        return "'" + s + '\'';
    }

    private ContentProviderClient getContentProviderClient() {
        return context.getContentResolver().acquireContentProviderClient(getUri());
    }

    private Uri getUri() {
        return Uri.parse(CONTENT_URI);
    }


}
