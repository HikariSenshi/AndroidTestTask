package com.testtask.applicationb.utils;

import android.os.Environment;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Utils {

    public static boolean canWriteOnExternalStorage() {
        // get the state of your external storage
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static SimpleDateFormat getTimeDateFormat(){
        return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
    }

}
