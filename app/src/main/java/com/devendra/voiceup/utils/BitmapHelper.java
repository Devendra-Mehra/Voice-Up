package com.devendra.voiceup.utils;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

/**
 * Created by Devendra Mehra on 6/16/2019.
 */
public class BitmapHelper {

    public static int getDominantColor(Bitmap bitmap) {
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap,
                1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return color;
    }


    public static Bitmap getBitmap(String filePath) {
        return ThumbnailUtils.createVideoThumbnail(filePath,
                MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
    }

}
