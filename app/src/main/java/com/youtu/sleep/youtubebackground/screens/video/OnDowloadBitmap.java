package com.youtu.sleep.youtubebackground.screens.video;

import android.graphics.Bitmap;

/**
 * Created by DaiPhongPC on 8/10/2018.
 */

public interface OnDowloadBitmap {
    void onSuccess(Bitmap bitmap);

    void onFail();
}
