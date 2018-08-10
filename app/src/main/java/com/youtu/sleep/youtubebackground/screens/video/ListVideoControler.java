package com.youtu.sleep.youtubebackground.screens.video;

/**
 * Created by DaiPhongPC on 8/10/2018.
 */

public class ListVideoControler {
    /**
     * get Video Next
     */
    public static int getVideoNext(int position, int size) {
        if (position < (size - 1)) {
            return ++position;
        }
        return position;
    }

    /**
     * get Video Previous
     */
    public static int getVideoPrevious(int position) {
        if (position > 1) {
            return --position;
        }
        return position;
    }
}
