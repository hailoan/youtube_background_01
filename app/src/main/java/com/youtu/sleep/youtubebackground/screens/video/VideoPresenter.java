package com.youtu.sleep.youtubebackground.screens.video;

import com.youtu.sleep.youtubebackground.data.model.popularvideo.Video;
import com.youtu.sleep.youtubebackground.data.repository.UrlVideoRepository;

import java.util.List;

/**
 * Created by DaiPhongPC on 8/6/2018.
 */

public class VideoPresenter implements VideoContract.Presenter {
    private VideoContract.View mView;
    private UrlVideoRepository mUrlVideoRepository;

    public VideoPresenter(UrlVideoRepository mUrlVideoRepository) {
        this.mUrlVideoRepository = mUrlVideoRepository;
    }

    @Override
    public void setView(VideoContract.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {
        getListUrlVideo();
    }

    @Override
    public void onStop() {

    }

    @Override

    public void getListUrlVideo() {
        mUrlVideoRepository.getListUrlVideo(mView.getListVideo(), new OnGetListUrlVideoYoutube() {
            @Override
            public void onSuccess(List<Video> videos) {
                mView.showListVideo(videos);
                mView.setTitle(videos.get(mView.getCurrentPositionVideo()).getTitle());
            }

            @Override
            public void onError(String mess) {
                mView.showMessageErrorExtraUrlVideo(mess);
            }
        });
    }

    @Override
    public void updateVideoFavourite(Video video) {

    }

    @Override
    public void updateSettingLoopVideo() {

    }
}
