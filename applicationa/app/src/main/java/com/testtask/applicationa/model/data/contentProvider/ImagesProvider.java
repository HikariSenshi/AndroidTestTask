package com.testtask.applicationa.model.data.contentProvider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.testtask.applicationa.model.data.db.DbHelper;

import static com.testtask.applicationa.utils.Consts.ID;
import static com.testtask.applicationa.utils.Consts.TABLE_IMAGES;

public class ImagesProvider extends ContentProvider {

    private static final String AUTHORITY = "com.testtask.applicationa.model.data.contentProvider.ImagesProvider";
    public static final int IMAGES_LIST = 10;
    public static final int IMAGES_ID = 1;
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_IMAGES);

    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE +
            "images";
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
            "images";

    DbHelper dbHelper;

    private static final UriMatcher uriMatcher = new UriMatcher(
            UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, TABLE_IMAGES, IMAGES_LIST);
        uriMatcher.addURI(AUTHORITY, TABLE_IMAGES + "/#", IMAGES_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_IMAGES);

        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case IMAGES_ID:
                queryBuilder.appendWhere(ID + "="
                        + uri.getLastPathSegment());
                break;
            case IMAGES_LIST:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(dbHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        if (uriMatcher.match(uri) != IMAGES_LIST) {
            throw new IllegalArgumentException("Invalid URI: " + uri);
        }

        long id = db.insert(TABLE_IMAGES, null, contentValues);

        if (id > 0) {
            return ContentUris.withAppendedId(uri, id);
        }
        throw new SQLException("Error inserting into table: " + TABLE_IMAGES);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues,
                      @Nullable String selectionClause, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updateCount;
        switch (uriMatcher.match(uri)) {
            case IMAGES_LIST:
                updateCount = db.update(TABLE_IMAGES, contentValues, selectionClause, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return updateCount;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selectionClause, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int delCount;
        switch (uriMatcher.match(uri)) {
            case IMAGES_LIST:
                delCount = db.delete(
                        TABLE_IMAGES,
                        selectionClause,
                        selectionArgs);
                break;
            case IMAGES_ID:
                String idStr = uri.getLastPathSegment();
                String where = ID + " = " + idStr;
                if (!TextUtils.isEmpty(selectionClause)) {
                    where += " AND " + selectionClause;
                }
                delCount = db.delete(
                        TABLE_IMAGES,
                        where,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return delCount;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        String type;
        switch (uriMatcher.match(uri)) {
            case IMAGES_LIST:
                type = CONTENT_TYPE;
                break;
            case IMAGES_ID:
                type = CONTENT_ITEM_TYPE;
                break;
            default:
                throw new IllegalArgumentException("Invalid URI: "+uri);
        }
        return type;
    }

}
