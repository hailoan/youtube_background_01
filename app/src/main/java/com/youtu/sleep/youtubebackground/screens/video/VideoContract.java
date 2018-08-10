package com.youtu.sleep.youtubebackground.screens.video;

import com.youtu.sleep.youtubebackground.data.model.popularvideo.Video;
import com.youtu.sleep.youtubebackground.screens.BasePresenter;

import java.util.List;

/**
 * Created by DaiPhongPC on 8/6/2018.
 */

public interface VideoContract {
    /**
     * View
     */
    interface View {
        int getCurrentPositionVideo();

        List<Video> getListVideo();

        void setTitle(String title);

        void showListVideo(List<Video> videos);

        void showMessageErrorExtraUrlVideo(String mess);
    }

    /**
     * Presenter
     */
    interface Presenter extends BasePresenter<View> {
        void getListUrlVideo();

        void updateVideoFavourite(Video video);

        void updateSettingLoopVideo();
    }

}
