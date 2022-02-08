package com.example.cluboplympus.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cluboplympus.data.ClubOlympusContract.*;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


// URI - Unified Resource Identifier
// content://com.android.clubolympus/members

// content://com.android.contacts/contacts - все таблица
// content://com.android.contacts/contacts/34 - строка с id 34 в таблице
// content:// - schema
// com.android.contacts - content authority (указывает какой контент провайдер будет использоваться)
// contacts  - type of data
// 34 - id в таблице

// URL - Unified Resource Locator
// http://google.com

public class OlympusContentProvider extends ContentProvider {
    OlympusDbOpenHelper dbOpenHelper;

    private static final int MEMBERS = 111;
    private static final int MEMBER_ID = 222;

    // Creates a UriMatcher object.
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS, MEMBERS);
        uriMatcher.addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS + "/#", MEMBER_ID);
    }

    @Override
    public boolean onCreate() {
        dbOpenHelper = new OlympusDbOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor;
        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                cursor = db.query(MemberEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            // selection = "_id=?"
            // selectionArgs = 34
            case MEMBER_ID:
                selection = MemberEntry._ID + "=?";
                selectionArgs = new String[]
                        {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(MemberEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Can't query incorrect URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        String firstName = values.getAsString(MemberEntry.COLUMN_FIRST_NAME);
        if (firstName == null) {
            throw new IllegalArgumentException("You have to input first name");
        }

        String lastName = values.getAsString(MemberEntry.COLUMN_LAST_NAME);
        if (lastName == null) {
            throw new IllegalArgumentException("You have to input last name");
        }

        String sport = values.getAsString(MemberEntry.COLUMN_SPORT);
        if (sport == null) {
            throw new IllegalArgumentException("You have to input sport");
        }

        Integer sex = values.getAsInteger(MemberEntry.COLUMN_SEX);
        if (sex == null || !(sex == MemberEntry.SEX_UNKNOWN || sex == MemberEntry.SEX_MALE || sex == MemberEntry.SEX_FEMALE)) {
            throw new IllegalArgumentException("You have to select sex");
        }

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                long id = db.insert(MemberEntry.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e("insertMethod", "Insertion of data in the table failed for " + uri);
                    return null;
                }

                getContext().getContentResolver().notifyChange(uri, null);

                return ContentUris.withAppendedId(uri, id);

            default:
                throw new IllegalArgumentException("Insertion of data in " + "the table failed for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int rowsWasDeleted;

        switch (match) {
            case MEMBERS:
                rowsWasDeleted = db.delete(MemberEntry.TABLE_NAME, selection, selectionArgs);
            break;
            case MEMBER_ID:
                selection = MemberEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsWasDeleted = db.delete(MemberEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Can't delete URI " + uri);
        }

        if (rowsWasDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsWasDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        if (values.containsKey(MemberEntry.COLUMN_FIRST_NAME)) {
            String firstName = values.getAsString(MemberEntry.COLUMN_FIRST_NAME);
            if (firstName == null) {
                throw new IllegalArgumentException("You have to input first name");
            }
        }

        if (values.containsKey(MemberEntry.COLUMN_LAST_NAME)) {
            String lastName = values.getAsString(MemberEntry.COLUMN_LAST_NAME);
            if (lastName == null) {
                throw new IllegalArgumentException("You have to input last name");
            }
        }

        if (values.containsKey(MemberEntry.COLUMN_SPORT)) {
            String sport = values.getAsString(MemberEntry.COLUMN_SPORT);
            if (sport == null) {
                throw new IllegalArgumentException("You have to input sport");
            }
        }

        if (values.containsKey(MemberEntry.COLUMN_SEX)) {
            Integer sex = values.getAsInteger(MemberEntry.COLUMN_SEX);
            if (sex == null || !(sex == MemberEntry.SEX_UNKNOWN || sex == MemberEntry.SEX_MALE || sex == MemberEntry.SEX_FEMALE)) {
                throw new IllegalArgumentException("You have to select sex");
            }
        }

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);

        int rowsWasUpdated;
        switch (match) {
            case MEMBERS:
                rowsWasUpdated = db.update(MemberEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case MEMBER_ID:
                selection = MemberEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsWasUpdated = db.update(MemberEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Can't update URI " + uri);
        }

        if (rowsWasUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsWasUpdated;
    }

    public void deleteTable() {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.delete(MemberEntry.TABLE_NAME, null, null);
        db.close();
    }


    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                return MemberEntry.CONTENT_MULTIPLE_ITEMS;
            case MEMBER_ID:
                return MemberEntry.CONTENT_SINGLE_ITEM;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
    }
}
