package com.devendra.voiceup.utils;

import android.os.Environment;

/**
 * Created by ADMIN on 21-Mar-17.
 */

public class Constants {
    public final static String DATABASE_NAME = "Voice up";
    public final static String LOGGED_IN = "loggedIn";
    public final static String USER_ID = "userId";
    public final static int PHOTO = 0;
    public final static String TYPE = "type";
    public final static String FILE_NAME = "file_name";
    public final static int VIDEO = 1;
    public static final int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;
    public static final String FILE_LOCATION = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            + "/.voice_up";


}
