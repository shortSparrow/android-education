package com.example.cluboplympus.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class ClubOlympusContract {
    private ClubOlympusContract() {
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "olympus"; // database_name - одна для всего приложения, даже если таблиц много
    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "com.example.cluboplympus";
    public static final String PATH_MEMBERS = "members";

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY);


    public static final class MemberEntry implements BaseColumns {
        public static final String TABLE_NAME = "members";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBERS);

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_FIRST_NAME = "firstName";
        public static final String COLUMN_LAST_NAME = "lastName";
        public static final String COLUMN_SEX = "sex";
        public static final String COLUMN_SPORT = "sport";

        public static final int SEX_UNKNOWN = 0;
        public static final int SEX_MALE = 1;
        public static final int SEX_FEMALE = 2;


        public static final String CONTENT_MULTIPLE_ITEMS = "vnd.android.cursor.dir/" + AUTHORITY + "/" + PATH_MEMBERS;
        public static final String CONTENT_SINGLE_ITEM = "vnd.android.cursor.item" + AUTHORITY + "/" + PATH_MEMBERS;
    }
}
