package com.testtask.applicationa.model.repository;

import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.RemoteException;

import com.testtask.applicationa.entity.ImageModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.testtask.applicationa.model.data.contentProvider.ImagesProvider.CONTENT_URI;
import static com.testtask.applicationa.utils.Consts.APP_KEY;
import static com.testtask.applicationa.utils.Consts.DATE;
import static com.testtask.applicationa.utils.Consts.GREEN_RED_GREY;
import static com.testtask.applicationa.utils.Consts.GREY_RED_GREEN;
import static com.testtask.applicationa.utils.Consts.IMAGE_URL;
import static com.testtask.applicationa.utils.Consts.NEW_OLD;
import static com.testtask.applicationa.utils.Consts.OLD_NEW;
import static com.testtask.applicationa.utils.Consts.SORTING_KEY;
import static com.testtask.applicationa.utils.Consts.STATUS;

public class HistoryRepository {

    private Context context;

    public HistoryRepository(Context context) {
        this.context = context;
    }

    //sortByOldDate
    public List<ImageModel> getImagesFromDb() {
        Cursor cursor = getContentProviderCursor();
        List<ImageModel> imageModels = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                imageModels.add(new ImageModel(cursor.getString(cursor.getColumnIndex(IMAGE_URL)),
                        cursor.getInt(cursor.getColumnIndex(STATUS)), cursor.getString(cursor.getColumnIndex(DATE))));
            }
        }
        cursor.close();

        return imageModels;
    }

    public List<ImageModel> getSortedList() {
        switch (getSortingMode()) {
            case NEW_OLD:
                return sortByNewDate();
            case GREEN_RED_GREY:
                return sortByGreenRedGrey();
            case GREY_RED_GREEN:
                return sortByGreyRedGreen();
            default:
               return getImagesFromDb();
        }
    }

    public List<ImageModel> sortByNewDate() {
        List<ImageModel> imageModels = getImagesFromDb();
        Collections.reverse(imageModels);
        return imageModels;
    }

    public List<ImageModel> sortByGreenRedGrey() {
        List<ImageModel> imageModels = getImagesFromDb();
        Comparator<ImageModel> comp = (im1, im2) -> im1.getStatus() - im2.getStatus();
        Collections.sort(imageModels, comp);
        return imageModels;
    }

    public List<ImageModel> sortByGreyRedGreen() {
        List<ImageModel> imageModels = sortByGreenRedGrey();
        Collections.reverse(imageModels);
        return imageModels;
    }

    private Cursor getContentProviderCursor() {
        ContentProviderClient providerClient = context.getContentResolver().acquireContentProviderClient(CONTENT_URI);
        Cursor cursor = null;
        try {
            cursor = providerClient.query(CONTENT_URI, null, null, null, null, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return cursor;
    }

    public void setSortingMode(String mode) {
        getPrefs().edit().putString(SORTING_KEY, mode).apply();
    }

    private String getSortingMode() {
        return getPrefs().getString(SORTING_KEY, OLD_NEW);
    }

    private SharedPreferences getPrefs() {
        return context.getSharedPreferences(APP_KEY, Context.MODE_PRIVATE);
    }

}
