package com.testtask.applicationa.utils;

public final class Consts {
    //for image status
    public static final int DOWNLOADED = 1;
    public static final int ERROR = 2;
    public static final int UNDEFINED = 3;

    //for DB
    public static final String TABLE_IMAGES = "images";
    public static final String ID = "_id";
    public static final String IMAGE_URL = "imageUrl";
    public static final String STATUS = "status";
    public static final String DATE = "date";

    // for Intent to the App B
    public static final String INTENT_KEY = "imageUrl";
    public static final String IMAGE_STATUS = "imageStatus";

    //for saving sorted
    public static final String APP_KEY = "AppA";
    public static final String SORTING_KEY = "sorting_key";
    public static final String OLD_NEW = "old_new";
    public static final String NEW_OLD = "new_old";
    public static final String GREEN_RED_GREY  = "green_red_grey";
    public static final String GREY_RED_GREEN = "grey_red_green";

/*
    content://com.testtask.applicationa.model.data.contentProvider.ImagesProvider/images
*/

}
