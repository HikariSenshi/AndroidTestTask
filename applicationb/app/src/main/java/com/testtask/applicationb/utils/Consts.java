package com.testtask.applicationb.utils;

public final class Consts {

    //for getting Intent data
    public static final String IMAGE_URL = "imageUrl";

    //for using ContentProvider
    private static final String CONTENT_PROVIDER_PATH =
            "content://com.testtask.applicationa.model.data.contentProvider.ImagesProvider";
    private static final String TABLE_IMAGES = "images";
    public static final String CONTENT_URI = CONTENT_PROVIDER_PATH + "/" + TABLE_IMAGES;

    public static final String STATUS = "status";
    public static final String DATE = "date";

    public static final String IMAGE_STATUS = "imageStatus";

    // image status
    public static final int IMAGE_STATUS_DEFAULT = 5;
    public static final int DOWNLOADED = 1;
    public static final int ERROR = 2;
    public static final int UNDEFINED = 3;

    public static final String URL_KEY = "url";
    public static final String DIRECTORY_PATH = "BIGDIG/test/B";

    public static final String STORAGE_PATH = "storage";
    public static final String CARD_PATH = "card";


}
